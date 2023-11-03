package ChatRoom.Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        final String serverAddress = "localhost";
        final int serverPort = 12345;
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Scanner scanner = new Scanner(System.in);
            System.out.print("Inserisci il tuo nome: ");
            String clientName = scanner.nextLine();

            Thread inputThread = new Thread(() -> {
                while (true) {
                    System.out.print("Messaggio: ");
                    String message = scanner.nextLine();
                    out.println(clientName + ": " + message);
                }
            });
            inputThread.start();

            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println(serverMessage);
            }
        } catch (ConnectException c) {
            System.out.println("Server is down");
            try {
                Reconnect();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted.");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void Reconnect() throws InterruptedException {
        for (int i = 3; i > 0; i--) {
            System.out.print("Reconnecting in " + i + " seconds");
            Thread.sleep(1000);
        }

    }
}
