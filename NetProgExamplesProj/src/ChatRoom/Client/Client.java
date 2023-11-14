package ChatRoom.Client;

import ChatRoom.Server.MultiServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = MultiServer.address;
    private static final int SERVER_PORT = MultiServer.PORT;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.print("Inserisci il tuo nome: ");
            String username = reader.readLine();
            writer.println(username);

            System.out.println("Benvenuto/a in chat! Scrivi 'exit' per uscire.");

            Thread readerThread = new Thread(() -> {
                try {
                    BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String serverMessage;
                    while ((serverMessage = serverReader.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readerThread.start();

            String message;
            while (true) {
                message = reader.readLine();
                writer.println(message);

                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
