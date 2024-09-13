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

        /*
         * 1) crea la socket
         * 2) fai la accept (dentro un while(true))
         * 3) prendi gli stream di Out e di IN
         * 4) dipende dall'applikcaizone
         * */

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            System.out.println("Server up and running");

            while (true) {
                Socket newClient = serverSocket.accept();
                System.out.println("Arrivato nuovo client: " + newClient.getInetAddress());

                PrintWriter toCLient = new PrintWriter(newClient.getOutputStream(), true);
                BufferedReader fromClient =
                        new BufferedReader(new InputStreamReader(newClient.getInputStream()));

                String valueFromClient = fromClient.readLine();

                int totalChars = valueFromClient.length();

                toCLient.println(totalChars);

                newClient.close();
            }

        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Porta fuori range. range consentito [0, 65535]");
        } catch (BindException b) {
            System.out.println("Porta già in uso");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
