package JabberService.JabberServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;

public class ServerMain {
    public static final int PORT = 80;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server aperto su porta: " + PORT);

            while (true) {
                Socket newClient = serverSocket.accept();
                System.out.println("Nuova connessione da: " + newClient.getInetAddress());

                BufferedReader fromClient = new BufferedReader(
                        new InputStreamReader(newClient.getInputStream()));

                PrintWriter toClient = new PrintWriter(newClient.getOutputStream(),
                        true);
            
                //Logica del server
                while (true) {
                    String strFromClient = fromClient.readLine();
                    if (strFromClient.equals("END")) break;
                    System.out.println("SERVER: echoing to client-> " + strFromClient);
                    toClient.println(strFromClient + "!");
                }
                //TODO: chiudere connessione
                newClient.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
