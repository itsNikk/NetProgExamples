package SimpleComExample.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public final class Server {


    private final int port;

    public Server(int port) {
        this.port = port;
    }


    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server in ascolto sulla porta " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket));
            }
        }
    }

    private class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                System.out.println("nuovo client connesso:" + clientSocket);
                out.println("Benvenuto al server!" + clientSocket.getInetAddress());
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("[ServerThread]: IOException");
            }
        }
    }
}
