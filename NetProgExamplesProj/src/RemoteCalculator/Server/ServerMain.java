package RemoteCalculator.Server;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) {
        CalculatorServer server = new CalculatorServer();
        try {
            server.start();
        } catch (IOException e) {
            System.out.println("[CalculatorServer]: IOException");
        }
    }
}
