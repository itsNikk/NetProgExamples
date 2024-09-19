package quartaA.RipassoSocket.Es4.Server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class PalindromeServer {
    public static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server up...");

            while (true) {
                Socket newClient = serverSocket.accept();
                // mandare newClient a un thread...
                new PalindromeServerThread(newClient).start();
            }

        } catch (BindException bindException) {
            System.out.println("Porta già in uso");
        } catch (IllegalArgumentException e) {
            System.out.println("Porta non in range");
        } catch (IOException ioException) {
            System.out.println("Generico errore di rete lato server");
        }

    }
}
