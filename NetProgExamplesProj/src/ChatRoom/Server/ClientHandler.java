package ChatRoom.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private BufferedReader inFromClient;
    private PrintWriter outToClient;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        getClientStreams(clientSocket);
    }

    private void getClientStreams(Socket clientSocket) {
        try {
            inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("[ClientHandler]: Can't get client streams");
        }
    }

    @Override
    public void run() {
        String username = null;
        try (clientSocket) {
            username = inFromClient.readLine();
            String welcomeMessage = username + " entered the chat.";
            System.out.println(welcomeMessage);

            ChatRoomServer.broadcastMessage(welcomeMessage, this);

            String clientMessage;
            while (true) {
                clientMessage = inFromClient.readLine();
                //https://www.w3schools.com/java/ref_string_equalsignorecase.asp
                if ("exit".equalsIgnoreCase(clientMessage)) break;
                ChatRoomServer.broadcastMessage(username + ": " + clientMessage, this);
            }
        } catch (IOException e) {
            System.out.println("[ClientHandler]: Can't communicate with client");
        } finally {
            try {
                inFromClient.close();
                outToClient.close();
            } catch (IOException e) {
                System.out.println("[ClientHandler]: Can't ");
            }

            String goodbyeMessage = username + " left the chat.";
            System.out.println(goodbyeMessage);
            ChatRoomServer.removeClient(this);
            ChatRoomServer.broadcastMessage(goodbyeMessage, this);
        }
    }

    public void sendMessage(String message) {
        outToClient.println(message);
    }
}
