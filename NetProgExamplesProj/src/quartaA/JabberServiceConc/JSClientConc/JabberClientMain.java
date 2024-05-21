package quartaA.JabberServiceConc.JSClientConc;

import quartaA.JabberService.JabberServer.JabberServerMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class JabberClientMain {
    public static void main(String[] args) throws InterruptedException {
        final String serverAddress = "localhost";
        final int serverPort = JabberServerMain.PORT;

        final int NUM_THREADS = 50;

        for (int i = 0; i < NUM_THREADS; i++) {
            new JabberClientConc("Client" + (i + 1), serverAddress, serverPort).start();
            Thread.sleep(200);
        }


    }
}
