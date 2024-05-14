package JabberService.JabberClient;

import JabberService.JabberServer.ServerMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;

public class JabberClient {
    public static void main(String[] args) {
        int port = ServerMain.PORT;
        String serverAddress = "localhost";
        try {
            Socket serverConnection = new Socket(serverAddress, port);
            System.out.println("Client connesso a server");

            BufferedReader fromServer = new BufferedReader(
                    new InputStreamReader(serverConnection.getInputStream()));
            PrintWriter toServer = new PrintWriter(
                    serverConnection.getOutputStream(), true);

            //Logica client
            for (int i = 0; i < 10; i++) {
                toServer.println("Hello " + (i + 1));
                String serverResponse = fromServer.readLine();
                System.out.println(serverResponse);
            }

            //END message
            toServer.println("END");
            //TODO: chiudere connessione
            serverConnection.close();

        } catch (IllegalArgumentException e) {
            System.out.println("Porta non consentita: " + port);
        } catch (UnknownHostException unknownHostException) {
            System.out.println("Indirizzo server sconosciuto");
        } catch (ConnectException e) {
            System.out.println("Client: errore di connessione. server down");
        } catch (IOException e) {
            System.out.println("Errore di rete generico");
        }
    }
}
