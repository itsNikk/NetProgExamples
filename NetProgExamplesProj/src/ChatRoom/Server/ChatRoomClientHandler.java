package ChatRoom.Server;

import ChatRoom.Server.ChatRoomServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatRoomClientHandler implements Runnable {
    private Socket clientToManage;
    private BufferedReader inFromClient;
    PrintWriter outToClient;

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
