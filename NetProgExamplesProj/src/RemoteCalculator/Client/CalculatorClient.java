package RemoteCalculator.Client;

import RemoteCalculator.Server.CalculatorServer;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Version one of the remote calculator app.
 * Client just sends the whole expression to the server and the server will parse it and compute it accordingly.
 */
public class CalculatorClient {
    public static void main(String[] args) {
        final int serverPort = CalculatorServer.getPort();
        final String serverAddress = CalculatorServer.getAddress();

        try (Socket socket = new Socket(serverAddress, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {
            String expression;
            do {
                System.out.print("Insert a mathematical expression (e.g. 2 + 3): ");
                expression = scanner.nextLine();
                out.println(expression);
                if(expression.equals("END")) break;
                double result = Double.parseDouble(in.readLine());
                System.out.println("Risultato: " + result);
            } while (true);

        } catch (UnknownHostException unknownHostException) {
            System.out.println("[CalculatorClient]: " + serverAddress + " is unknown");
        } catch (ConnectException connectException){
            System.out.println("Server not active.");
            System.exit(0);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("[CalculatorClient]: IOException while crating the socket.");
        }
    }
}
