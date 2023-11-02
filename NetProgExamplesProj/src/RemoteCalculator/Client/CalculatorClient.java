package RemoteCalculator.Client;

import RemoteCalculator.Server.CalculatorServer;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CalculatorClient {
    public static void main(String[] args) {
        final String serverAddress = "localhost";
        final int serverPort = CalculatorServer.getPort();
        try (Socket socket = new Socket(serverAddress, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {
            System.out.print("Inserisci un'espressione matematica (es. 2 + 3): ");
            String expression = scanner.nextLine();
            out.println(expression);
            double result = Double.parseDouble(in.readLine());
            System.out.println("Risultato: " + result);
        } catch (IOException e) {
            System.out.println("[CalculatorClient]: IOExcpetion");
        }
    }
}
