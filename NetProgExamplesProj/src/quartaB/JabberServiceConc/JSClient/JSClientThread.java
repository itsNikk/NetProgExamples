package quartaB.JabberServiceConc.JSClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class JSClientThread extends Thread {

    private int serverPort;
    private String serverAddress;

    private PrintWriter toServer;
    private BufferedReader fromServer;

    public JSClientThread(String name, String serverAddress, int serverPort) {
        super(name);
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {

        try (Socket connectionToServer = new Socket(serverAddress, serverPort);
             BufferedReader fromServer = new BufferedReader(
                     new InputStreamReader(connectionToServer.getInputStream()));
             PrintWriter toServer = new PrintWriter(
                     connectionToServer.getOutputStream(), true)) {

            //Logica client
            for (int i = 0; i < 25; i++) {
                toServer.println(getName() + "- Hello " + (i + 1));
                Thread.sleep((int) (Math.random() * (800 - 200)) + 200);
                System.out.println(fromServer.readLine());
            }

        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Errore di rete generico o di I/O");
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrotto");
        }

    }
}
