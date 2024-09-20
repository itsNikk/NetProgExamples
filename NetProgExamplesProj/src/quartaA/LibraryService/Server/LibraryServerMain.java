package quartaA.LibraryService.Server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class LibraryServerMain {
    public static final int PORT = 5544;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server up...");
            while (true) {
                Socket newClient = server.accept();

                new LibraryServerThread(newClient).start();
            }

        } catch (IllegalArgumentException a) {
            System.out.println("Porta illegale");
        } catch (BindException bindException) {
            System.out.println("Porta già usata");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
