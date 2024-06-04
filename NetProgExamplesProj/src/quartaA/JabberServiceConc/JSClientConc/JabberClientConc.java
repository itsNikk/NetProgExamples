package quartaA.JabberServiceConc.JSClientConc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class JabberClientConc extends Thread {

    private String serverAddress;
    private int serverPort;

    public JabberClientConc(String name, String serverAddress, int serverPort) {
        super(name);
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
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
                Thread.sleep((int) (Math.random() * (800 - 200) + 200));
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
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrotto");
        }
    }
}
