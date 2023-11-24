package ChatRoom.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatRoomServer {

    public static final String address = "localhost";
    public static final int port = 80;

    //Chat Room
    private static ArrayList<ChatRoomClientHandler> clientsInRoom = new ArrayList();

    public void startServer() {
        try (ServerSocket s = new ServerSocket(port)) {
            System.out.println("Server up and running on port:" + port);
            manageConnections(s);
        } catch (IOException ioe) {
            System.out.println("[SERVER]: Can't bund server on port" + port + ". Closing...");
            System.exit(0);
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

    public static void broadcast(String message, ChatRoomClientHandler sender) {
        synchronized (clientsInRoom) {
            for (ChatRoomClientHandler handler : clientsInRoom) {
                if (handler != sender) handler.outToClient.println(message);
            }
        }
    }

    private class ChatRoomClientHandler implements Runnable {
        private Socket clientToManage;
        private BufferedReader inFromClient;
        private PrintWriter outToClient;

        public ChatRoomClientHandler(Socket client) {
            clientToManage = client;
            getClientStreams();
        }

        private void getClientStreams() {
            try {
                inFromClient = new BufferedReader(new InputStreamReader(clientToManage.getInputStream()));
                outToClient = new PrintWriter(clientToManage.getOutputStream(), true);
            } catch (IOException ioe) {
                System.out.println("[SERVERHANDLER]: can't get client streams");
                try {
                    clientToManage.close();
                } catch (IOException e) {
                    System.out.println("[SERVERHANDLER]: error while closing client socket.");
                }
            }
        }

        @Override
        public void run() {
            try {
                String username = inFromClient.readLine();
                String welcomeMessage = username + " entered the chat.";
                ChatRoomServer.broadcast(welcomeMessage, this);
                while (true) {
                    String messageFromConsole = inFromClient.readLine();
                    String messageToSend = username + ": " + messageFromConsole;
                    System.out.println(messageToSend);
                    ChatRoomServer.broadcast(messageToSend, this);
                }
            } catch (IOException ioe) {
                System.out.println("[SERVERHANDLER]: Can't communicate with client");
            }
        }
    }


}
