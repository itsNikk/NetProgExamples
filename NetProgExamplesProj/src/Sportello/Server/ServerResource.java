package Sportello.Server;

import java.io.PrintWriter;

public class ServerResource {
    private boolean isUse = false;

    private PrintWriter out;

    public synchronized void enterResource(PrintWriter outToClient) {
        try {
            while (isUse) wait();
        } catch (InterruptedException interruptedException) {
            System.out.println("Thread interrotto.");
        }

        isUse = true;
        out = outToClient;
    }

    public synchronized void handleResource(String name, String message) {
        if (isUse) System.out.println(name + " ha utilizzato la risorsa: " + message);
        else out.println("Accesso negato. Richiedi l'accesso");
    }

    public synchronized void releaseResource() {
        isUse = false;
        notifyAll();
    }


}
