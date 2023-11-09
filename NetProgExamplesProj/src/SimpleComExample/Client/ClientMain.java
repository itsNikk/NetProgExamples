package SimpleComExample.Client;

import SimpleComExample.Server.AppServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {

    private static final double NUM_CLIENTS = 10;

    public static void main(String[] args) {
        String serverAddress = AppServer.getServerAddress();
        int serverPort = AppServer.getServerPort();

        for (int i = 0; i < NUM_CLIENTS; i++) {
            new Thread(new ClientConnector("Client-" + (i + 1),
                    serverAddress, serverPort)).start();
        }
    }

    /**
     * This specific class (only with JDK version from 16 and on) can be converted into a Record type.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/21/language/records.html#GUID-6699E26F-4A9B-4393-A08B-1E47D4B2D263">Record class Oracle doc</a>
     */
    private static class ClientConnector implements Runnable {

        private final String clientName;
        private final String serverAddress;
        private final int port;

        public ClientConnector(String clientName, String serverAddress, int port) {
            this.clientName = clientName;
            this.serverAddress = serverAddress;
            this.port = port;
        }

        @Override
        public void run() {
            try (Socket c = new Socket(serverAddress, port)) {
                BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream()));
                String fromServer = r.readLine();
                System.out.println(clientName + ":> " + fromServer);
            } catch (UnknownHostException unknownHostException) {
                System.err.println("Host is unknown");
                System.exit(0);
            } catch (IOException e) {
                System.err.println("Can't connect to server");
            }
        }
    }
}
