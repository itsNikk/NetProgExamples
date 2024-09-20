package quartaA.LibraryService.Server;

import quartaA.LibraryService.Client.Book;
import quartaA.LibraryService.Client.Library;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LibraryServerThread extends Thread {

    private Socket clientToServe;

    public LibraryServerThread(Socket clientToServe) {
        this.clientToServe = clientToServe;
    }

    @Override
    public void run() {
        //prendo gli stream
        try (ObjectOutputStream toCLient = new ObjectOutputStream(clientToServe.getOutputStream());
             ObjectInputStream fromClient = new ObjectInputStream(clientToServe.getInputStream())) {

            Library library = (Library) fromClient.readObject();

            double valoreTotale = 0;
            //calcolo valore totale
            for (Book book : library.getLibrary()) {
                valoreTotale += book.getPrice();
            }

            //writeUTF = println
            toCLient.writeUTF("Il valore totale della libreria è: " + valoreTotale);
            toCLient.flush();

        } catch (IOException e) {
            System.out.println("Problema di rete in comunicazione");
        } catch (ClassNotFoundException c) {
            System.out.println("Problema di serializzazione");
        }


        //2)
    }
}
