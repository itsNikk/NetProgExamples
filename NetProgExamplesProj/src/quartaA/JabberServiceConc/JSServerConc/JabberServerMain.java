package quartaA.JabberServiceConc.JSServerConc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class JabberServerMain {
    public static final int PORT = 80;

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server aperto sulla porta: " + PORT);

            while (true) {
                Socket newClient = serverSocket.accept();
                System.out.println("Nuova connessione da:> " + newClient.getInetAddress());

                new JabberServiceThread(newClient).start();

            }

        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("numero di porta illegale: " + PORT);
        } catch (IOException e) {
            System.out.println("Porta già in uso: " + PORT);
        }

    }
}
