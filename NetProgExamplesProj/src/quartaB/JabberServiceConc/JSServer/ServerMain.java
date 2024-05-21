package quartaB.JabberServiceConc.JSServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static final int PORT = 80;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server aperto su porta: " + PORT);

            while (true) {
                Socket newClient = serverSocket.accept();
                System.out.println("Nuova connessione da: " + newClient.getInetAddress());
                new JabberServiceThread(newClient).start();

                //La logica di gestione di un client sta sul Thread
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
