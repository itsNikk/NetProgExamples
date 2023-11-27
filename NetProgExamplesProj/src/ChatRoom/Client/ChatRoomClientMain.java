package ChatRoom.Client;

import ChatRoom.Server.ChatRoomServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatRoomClientMain {
    private static final String SERVER_ADDRESS = ChatRoomServer.ADDRESS;
    private static final int SERVER_PORT = ChatRoomServer.PORT;

    public static void main(String[] args) {
        try (Socket client = new Socket(SERVER_ADDRESS, SERVER_PORT);
             Scanner scanner = new Scanner(System.in);
             PrintWriter writer = new PrintWriter(client.getOutputStream(), true)
        ) {

            System.out.print("Inserisci il tuo nome utente> ");
            String username = scanner.nextLine();
            writer.println(username);

            //TODO: Gestire lettura da server asincrona rispetto alla scrittura
            new ClientThread(client).start();


            String message;
            while (true) {
                message = scanner.nextLine();
                writer.println(message);

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }


        } catch (IOException ioe) {
            System.out.println("[CLIENT]: Errore comunicazione");
        }
    }

    private static class ClientThread extends Thread {

        private final Socket connection;
        private BufferedReader inFromServer;

        public ClientThread(Socket connection) throws IOException {
            this.connection = connection;
            inFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }

        @Override
        public void run() {
            String serverMessage;
            try {
                while ((serverMessage = inFromServer.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (IOException ioe) {
                //ioe.printStackTrace(); serve per il debug, mai metterlo in codice vero
                System.out.println("[CLIENTTHREAD]: Errore");
            }
        }
    }
}
