package SimpleComExample.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.Date;

public class AppServer {

    private static final int serverPort = 8080;

    private static final String serverAddress = "localhost";

    public void start() {
        try (ServerSocket server = new ServerSocket(serverPort)) {
            System.out.println("Server up and running on port: " + serverPort);
            manageConnections(server);
        } catch (IOException e) {
            System.out.println("Can't start server on port: " + serverPort + ". Closing...");
            System.exit(0);
        }
    }

    private void manageConnections(ServerSocket server) {
        while (true) {
            try {
                Socket client = server.accept();
                System.out.println("[AppServer]: New client:> " + client.getInetAddress() + " " + Date.from(Instant.now()));
                new Thread(new ClientHandler(client)).start();
            } catch (IOException e) {
                System.out.println("[AppServer]: Error while getting client connection");
            }
        }
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static String getServerAddress() {
        return serverAddress;
    }

    private class ClientHandler implements Runnable {
        private final Socket clientToManage;
        private PrintWriter outToClient;

        public ClientHandler(Socket clientToManage) {
            this.clientToManage = clientToManage;
            getClientStreams();
        }

        private void getClientStreams() {
            try {
                outToClient = new PrintWriter(clientToManage.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("Error while getting client " + clientToManage.getInetAddress() + " streams." +
                        " Closing connection");
            }
        }

        @Override
        public void run() {
            try (clientToManage) {
                outToClient.println("Benvenuto al server!");
            } catch (IOException ioe) {
                System.out.println("[AppServerThread]: Error while communicating with client. Closing connection.");
            }
        }
    }

    private static int getFirstFreePort() {
        int port = 8080;
        while (port <= 65353) {
            //Try with resources
            // "ignored" is used to say that
            // we don't want to use that resource inside the try block
            try (ServerSocket ignored = new ServerSocket(port)) {
                return port;
            } catch (IOException e) {
                port++;
            }
        }
        return 65353;
    }
}
