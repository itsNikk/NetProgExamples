package quartaA.RipassoSocket.Es4.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class PalindromeThreadClient extends Thread {
    private final String hostName;
    private final int hostPort;
    private final String valueToSend;
    private BufferedReader fromServer;
    private PrintWriter toServer;
    private Socket socket;

    public PalindromeThreadClient(String name, String hostName, int hostPort, String valueToSend) {
        setName(name);
        this.hostName = hostName;
        this.hostPort = hostPort;
        this.valueToSend = valueToSend;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(hostName, hostPort)) {
            this.socket = socket;
            this.toServer = new PrintWriter(socket.getOutputStream(), true);
            this.fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            toServer.println(valueToSend);

            try {
                String responseFromServer = fromServer.readLine();
                System.out.println(getName() + ": " + responseFromServer);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Problema in lettura sul client " + getName());
            }
        } catch (UnknownHostException e) {
            System.out.println("Host " + hostName + " sconosciuto");
        } catch (IllegalArgumentException e) {
            System.out.println("Porta " + hostPort + " illegale.");
        } catch (ConnectException connectException) {
            System.out.println("Server is not available");
        } catch (IOException e) {
            System.out.println("Generico problema di rete");
        }


    }
}
