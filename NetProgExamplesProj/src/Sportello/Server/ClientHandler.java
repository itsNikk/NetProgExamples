package Sportello.Server;

import javax.print.MultiDocPrintService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket clientToManage;
    private PrintWriter outToClient;
    private BufferedReader inFromClient;

    public ClientHandler(Socket clientToManage) {
        this.clientToManage = clientToManage;
        //prendere gli streams del client
        getClientStreams();
    }

    private void getClientStreams() {
        try {
            outToClient = new PrintWriter(clientToManage.getOutputStream(), true);
            inFromClient = new BufferedReader(new InputStreamReader(clientToManage.getInputStream()));
        } catch (IOException ioe) {
            System.out.println("[CLIENT_HANDLER]: non posso prendere gli streams del client.");
            try {
                clientToManage.close();
            } catch (IOException e) {
                System.out.println("[CLIENT_HANDLER]: errore nella chiusura della socket client");
            }
        }
    }

    @Override
    public void run() {
        try {
            String clientName = inFromClient.readLine();
            System.out.println("[CLIENT_HANDLER]: new client " + clientName);

            // client chieded la risorsa = auto chiede parcheggio
            SharedServer.resource.enterResource(outToClient);
            //se riesce a entrare, la usa
            String message;
            while ((message = inFromClient.readLine()) != null) {
                // se ne va dopo un po'
                SharedServer.resource.handleResource(clientName, message);
            }


        } catch (IOException ioException) {
            System.out.println("[CLIENT_HANDLER]: non posso comunicare con il client");
            SharedServer.resource.releaseResource();
        } finally {
            //Il client rilascia la risorsa
            System.out.println("[CLIENT_HANDLER]: Rilascio la risorsa");
            SharedServer.resource.releaseResource();
            try {
                clientToManage.close();
            } catch (IOException ioException) {
                System.out.println("[CLIENT_HANDLER]: errore nella chiusura della socket del client");
            }
        }
    }
}
