package SimpleComExample.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.Date;

public class Server {

    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket s = new ServerSocket(port)) {
            System.out.println("Server started on port: " + port);
            manageServer(s);
        } catch (BindException e) {
            System.out.println("port " + port + " already in use.");
        } catch (IOException e) {
            System.err.println("IOException");
        }
    }

    private void manageServer(ServerSocket s) {
        while (true) {
            try {
                Socket client = s.accept();
                new Thread(new ClientHandler(client)).start();
            } catch (IOException e) {
                System.out.println("Can't get connection to client");
            }
        }
    }

    private class ClientHandler implements Runnable {
        private final Socket client;
        private PrintWriter outToClient;

        public ClientHandler(Socket client) throws IOException {
            this.client = client;
            getClientStreams();
        }

        private void getClientStreams() throws IOException {
            outToClient = new PrintWriter(client.getOutputStream(), true);
        }

        @Override
        public void run() {
            try (client) {
                System.out.println("[ServerThread] New Client:> " + client.getInetAddress() + Date.from(Instant.now()));
                outToClient.println("Benvenuto nel server!");
            } catch (IOException e) {
                System.err.println("Error while communicating with client.");
            }
        }
    }
}
