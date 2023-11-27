package ChatRoom.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatRoomServer {

    public static final String ADDRESS = "localhost";
    public static final int PORT = 80;

    //Chat Room
    private static final ArrayList<ChatRoomClientHandler> clientsInRoom = new ArrayList<>();

    public void startServer() {
        try (ServerSocket s = new ServerSocket(PORT)) {
            System.out.println("Server up and running on port:" + PORT);
            manageConnections(s);
        } catch (IOException ioe) {
            System.out.println("[SERVER]: Can't bund server on port" + PORT + ". Closing...");
            System.exit(0);
            // Trova la prima porta libera e binda il server su quella porta
        }
    }

    public void manageConnections(ServerSocket s) {
        while (true) {
            try {
                Socket client = s.accept();
                ChatRoomClientHandler c = new ChatRoomClientHandler(client);
                clientsInRoom.add(c);
                new Thread(c).start();
            } catch (IOException ioe) {
                System.out.println("[SERVER]: Can't accept clients");
            }
        }
    }

    public static void removeClient(ChatRoomClientHandler clientHandler) {
        synchronized (clientsInRoom) {
            clientsInRoom.remove(clientHandler);
        }
    }

    public static void broadcast(String message, ChatRoomClientHandler sender) {
        synchronized (clientsInRoom) {
            for (ChatRoomClientHandler handler : clientsInRoom) {
                if (handler != sender) handler.outToClient.println(message);
            }
        }
    }


}












