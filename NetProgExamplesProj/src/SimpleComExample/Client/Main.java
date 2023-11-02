package SimpleComExample.Client;

import SimpleComExample.Server.ServerInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {

        final String serverAddress = ServerInfo.serverAddress;
        final int serverPort = ServerInfo.serverPort;

        try (Socket socket = new Socket(serverAddress, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String serverMessage = in.readLine();
            System.out.println("Messaggio dal server: " + serverMessage);
        } catch (UnknownHostException e) {
            System.out.println(serverAddress + " is an unknown address.");
        } catch (IOException e) {
            System.out.println("Generic IO error...");
        } catch (IllegalArgumentException iae) {
            System.out.println(serverPort + " is illegal.");
        }
    }
}
