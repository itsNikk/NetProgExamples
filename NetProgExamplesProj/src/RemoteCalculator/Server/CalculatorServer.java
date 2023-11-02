package RemoteCalculator.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorServer {


    private static int port;

    public CalculatorServer(int port) {
        CalculatorServer.port = port;
    }

    public CalculatorServer() {
        this(80);
    }

    public void start() throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server in ascolto su porta: " + port);
            while (true) {
                Socket client = serverSocket.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter outToClient = new PrintWriter(client.getOutputStream(), true);

                String expression = inFromClient.readLine();
                float val = evaluateExpression(expression);
                outToClient.println(val);

                client.close();
            }
        }

    }

    private float evaluateExpression(String expression) {
        String[] split = expression.strip().split(" ");
        return switch (split[1]) {
            case Operations.plus -> Integer.parseInt(split[0]) + Integer.parseInt(split[2]);
            case Operations.minus -> Integer.parseInt(split[0]) - Integer.parseInt(split[2]);
            case Operations.mult -> Integer.parseInt(split[0]) * Integer.parseInt(split[2]);
            case Operations.div -> (float) Integer.parseInt(split[0]) / Integer.parseInt(split[2]);
            default -> 0;
        };
    }

    public static int getPort() {
        return getPort();
    }

    private static final class Operations {
        public static final String plus = "+";
        public static final String minus = "-";
        public static final String mult = "*";
        public static final String div = "/";
    }

}
