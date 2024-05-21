package quartaA.JabberService.JabberClient;

import quartaA.JabberService.JabberServer.JabberServerMain;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;

public class JabberClientMain {
    public static void main(String[] args) {
        final String serverAddress = "localhost";
        final int serverPort = JabberServerMain.PORT;
        try {
            Socket newClient = new Socket(serverAddress, serverPort);
            System.out.println("Client connessso col server!");

            // prendere i  canali di comunicazione da e verso il server
            BufferedReader fromServer = new BufferedReader(
                    new InputStreamReader(newClient.getInputStream()));
            PrintWriter toServer = new PrintWriter(newClient.getOutputStream(), true);

            //Logica client - AKA protocollo applicativo
            for (int i = 0; i < 10; i++) {
                toServer.println("Hello " + (i + 1));
                String serverResponse = fromServer.readLine();
                System.out.println(serverResponse);
            }

            toServer.println("END");

            newClient.close();

        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Porta illegale, cambiala. Porta inserita: " + serverPort);
        } catch (UnknownHostException unknownHostException) {
            System.out.println("Indirizzo server errato/sconosciuto. " + serverAddress);
        } catch (ConnectException connectException) {
            //Sollevata quando il client si connette al server ma il server è spento (non listening)
            System.out.println("Server is down... :(");
        } catch (IOException e) {
            System.out.println("Errore di rete generico");
        }


    }
}
