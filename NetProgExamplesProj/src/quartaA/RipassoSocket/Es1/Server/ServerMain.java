package quartaA.RipassoSocket.Es1.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public static final int PORT = 1324;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server aperto su porta: " + PORT);

            while (true) {
                //è arrivato un nuovo client...
                Socket newClient = serverSocket.accept();
                System.out.println("Arrivato nuovo client... " + newClient.getInetAddress());

                BufferedReader fromCLient = new BufferedReader(new InputStreamReader(newClient.getInputStream()));
                PrintWriter toCLient = new PrintWriter(newClient.getOutputStream(), true);

                //leggere la stringa del client
                String valueFromClient = fromCLient.readLine();
                System.out.println(valueFromClient);

                int totalChars = valueFromClient.length();
                toCLient.println("La stringa inviata ha:" + totalChars + " caratteri");

                newClient.close();
            }

        } catch (IllegalArgumentException e) {
            System.out.println("La porta è illegale. Range consentito [0,65535]");
        } catch (BindException b) {
            System.out.println("Indirizzo già in uso :(");
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
}
