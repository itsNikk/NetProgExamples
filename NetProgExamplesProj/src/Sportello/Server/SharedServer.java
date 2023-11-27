package Sportello.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SharedServer {

    public static final int PORT = 80;

    public static final ServerResource resource = new ServerResource();

    public void startServer() {
        //perchè posso mettere una classe dentro () in un try => implements Closable
        try (ServerSocket s = new ServerSocket(80)) {
            System.out.println("Open server on port: " + PORT);
            // Fa spegnere il server dopo N millisecondi
            s.setSoTimeout(600000);
            manageConnections(s);
        } catch (IOException ioe) {
            System.err.println("[SERVER]: non si può aprire server sulla porte:" + PORT);
            System.exit(0);
        }
    }

    private void manageConnections(ServerSocket s) {
        while (true) {
            try {
                Socket client = s.accept();

                // Server Multithread
                /*
                1) Creare un thread per ogni client ricevuto (on-demand)
                2) Creare e gestire un POOL di thread sul server.
                 */
                //ALTERNATIVA new Thread(new ClientHandler(client)).start()
                new ClientHandler(client).start();
            } catch (SocketTimeoutException ste) {
                System.out.println("[SERVER]: il server è in timeout");
                System.exit(0);
            } catch (IOException ioe) {
                System.out.println("[SERVER]: Errore mentre aspetto una connessione");
            }
        }
    }

}
