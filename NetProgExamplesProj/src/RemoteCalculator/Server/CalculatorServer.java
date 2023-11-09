package RemoteCalculator.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.Date;

public class CalculatorServer {

    private static final String serverAddress = "localhost";
    private static final int port = 80;

    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server in ascolto su porta: " + port);
            serverSocket.setSoTimeout(6000);
            manageConnections(serverSocket);
        } catch (IOException ioe) {
            System.out.println("[CalculatorServer]: IOException");
        }

    }

    private void manageConnections(ServerSocket serverSocket) {
        while (true) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("[CalculatorServer] New Client " + client.getInetAddress() + " " + Date.from(Instant.now()));
                new Thread(new CalculatorThread(client)).start();
            } catch (IOException e) {
                System.out.println("[CalculatorServer]: Error while waiting for a connection");
                break;
            }
        }
    }

    /**
     * This method uses the enhanced switch keyword.
     * This new switch can be used from JDK 13.
     * @param expression the expression to evaluate e.g.:2 + 4
     * @return the result associated with that expression
     * @see <a href="https://www.vojtechruzicka.com/java-enhanced-switch/">Enhanced switch keyword</a>
     */
    private float evaluateExpression(String expression) {
        String[] split = expression.strip().split(" ");

        return switch (split[1]) {
            case OperationType.plus -> Integer.parseInt(split[0]) + Integer.parseInt(split[2]);
            case OperationType.minus -> Integer.parseInt(split[0]) - Integer.parseInt(split[2]);
            case OperationType.mult -> Integer.parseInt(split[0]) * Integer.parseInt(split[2]);
            case OperationType.div -> (float) Integer.parseInt(split[0]) / Integer.parseInt(split[2]);
            default -> 0;
        };
    }

    public static int getPort() {
        return port;
    }

    public static String getAddress() {
        return serverAddress;
    }


    /**
     * This class is just a utility class that helps me to store constant operation types.
     * I could have used enum, it's just a metter of style a conciseness
     */
    private static final class OperationType {
        public static final String plus = "+";
        public static final String minus = "-";
        public static final String mult = "*";
        public static final String div = "/";
    }

    private class CalculatorThread implements Runnable {
        private final Socket client;
        private BufferedReader inFromClient;
        private PrintWriter outToClient;

        public CalculatorThread(Socket client) {
            this.client = client;
            getClientStreams();
        }

        @Override
        public void run() {

            try (client) {
                while (true) {
                    String expression = inFromClient.readLine();
                    if (expression.equals("END")) {
                        System.out.println("[CalculatorServerThread]: Ending communication with client" + client.getInetAddress() + " as requested.");
                        return;
                    }
                    float val = evaluateExpression(expression);
                    outToClient.println(val);
                }
            } catch (IOException e) {
                System.out.println("[CalculatorServerThread]: Error while communicating with client");
            }

        }

        private void getClientStreams() {
            try {
                inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
                outToClient = new PrintWriter(client.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("[CalculatorServerThread]: Can't get client streams, closing client connection");
                try {
                    client.close();
                } catch (IOException ex) {
                    System.out.println("[CalculatorServerThread]: Error while closing client socket.");
                }
            }
        }
    }
}
