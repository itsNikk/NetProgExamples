package ChatRoom.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomServer {

    public static final int PORT = 8888;
    public static final String ADDRESS = "localhost";
    private static final List<ClientHandler> clients = new ArrayList<>();


    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            manageConnections(serverSocket);
        } catch (IOException e) {
            System.out.println("Can't start server on port: " + PORT);
        }
    }

    private void manageConnections(ServerSocket serverSocket){
        while (true) {
           try {
               Socket clientSocket = serverSocket.accept();
               System.out.println("New connection from: " + clientSocket.getInetAddress());

               ClientHandler clientHandler = new ClientHandler(clientSocket);
               addClient(clientHandler);
               new Thread(clientHandler).start();
           } catch (IOException exception){
               System.out.println("[CHATSERVER]: Error on accepting a new client.");
           }
        }
    }

    public static void broadcastMessage(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                // Sender doesn't need to receive it's own message in our implementation.
                if (client != sender) client.sendMessage(message);
            }
        }
    }

    public static void removeClient(ClientHandler clientHandler) {
        synchronized (clients) {
            clients.remove(clientHandler);
        }
    }

    public void addClient(ClientHandler clientHandler) {
        synchronized (clients) {
            clients.add(clientHandler);
        }
    }
}
