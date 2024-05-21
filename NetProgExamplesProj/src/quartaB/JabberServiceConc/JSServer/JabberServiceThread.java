package quartaB.JabberServiceConc.JSServer;

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
        // try with resources
        try (clientToServe; BufferedReader fromClient = new BufferedReader(
                new InputStreamReader(clientToServe.getInputStream()));
             PrintWriter toClient = new PrintWriter(clientToServe.getOutputStream(),
                     true)) {


            //Logica del server
            while (true) {
                String strFromClient = fromClient.readLine();
                if (strFromClient.equals("END")) break;
                System.out.println("SERVER: echoing to client-> " + strFromClient);
                toClient.println(strFromClient + "!");
            }
        } catch (IOException er) {
            System.out.println(er.getMessage());
        }
    }
}
