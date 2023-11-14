package ChatRoom.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;

        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String username = reader.readLine();
            System.out.println(username + " si è unito alla chat.");

            MultiServer.broadcastMessage(username + " si è unito alla chat.", this);

            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                if ("exit".equalsIgnoreCase(clientMessage)) {
                    break;
                }

                MultiServer.broadcastMessage(username + ": " + clientMessage, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Il client ha lasciato la chat.");
            MultiServer.removeClient(this);
            MultiServer.broadcastMessage("Un utente ha lasciato la chat.", this);
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }
}
