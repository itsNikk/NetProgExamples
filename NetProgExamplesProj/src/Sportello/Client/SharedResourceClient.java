package Sportello.Client;// SharedResourceClient.java

import java.io.*;
import java.net.*;

public class SharedResourceClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 80;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);

        BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        // Inizializza e avvia il gestore del client
        ClientManager clientManager = new ClientManager(writer, serverReader, userInputReader);
        new Thread(clientManager).start();

        // Gestisci l'input dell'utente e invia i messaggi al server
        String userInput;
        while ((userInput = userInputReader.readLine()) != null) {
            if (userInput.contains("exit")) break;
            writer.println(userInput);
        }

        socket.close();
    }
}

class ClientManager implements Runnable {
    private final PrintWriter writer;
    private final BufferedReader serverReader;
    private final BufferedReader userInputReader;

    public ClientManager(PrintWriter writer, BufferedReader serverReader, BufferedReader userInputReader) {
        this.writer = writer;
        this.serverReader = serverReader;
        this.userInputReader = userInputReader;
    }

    @Override
    public void run() {
        try {
            // Leggi messaggi dal server e stampali sulla console
            String serverMessage;
            while ((serverMessage = serverReader.readLine()) != null) {
                System.out.println(serverMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
