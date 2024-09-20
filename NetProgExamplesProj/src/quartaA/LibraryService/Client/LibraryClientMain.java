package quartaA.LibraryService.Client;

import quartaA.LibraryService.Server.LibraryServerMain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LibraryClientMain {
    public static void main(String[] args) {
        // new LibraryClientThread().start()

        try (Socket s = new Socket("localhost", LibraryServerMain.PORT)) {

            ObjectOutputStream toServer = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(s.getInputStream());

            Library library = new Library();
            library.addBook(new Book("libro1", 54, 123));
            library.addBook(new Book("libro2", 12, 55));
            library.addBook(new Book("libro3", 66, 654));

            toServer.writeObject(library);
            toServer.flush();

            //leggo dal server
            System.out.println(fromServer.readUTF());

        } catch (IOException e) {
            System.out.println("Generico errore di socket");
        }
    }
}
