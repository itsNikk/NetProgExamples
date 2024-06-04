package quartaA.JabberServiceConc.JSServerConc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class JabberServiceThread extends Thread {

    private final Socket clientToServe;

    public JabberServiceThread(Socket clientToServe) {
        this.clientToServe = clientToServe;
    }

    @Override
    public void run() {
        //try with resources
        try (clientToServe; BufferedReader fromClient = new BufferedReader(
                new InputStreamReader(clientToServe.getInputStream()));
             PrintWriter toClient = new PrintWriter(clientToServe.getOutputStream(), true)
        ) {
            //LOGICA SERVER / protocollo applicativo
            while (true) {
                String strFromClient = fromClient.readLine();
                if (strFromClient.equals("END")) break;
                System.out.println("Echoing -> " + strFromClient + "!");
                toClient.println(strFromClient + "!");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
