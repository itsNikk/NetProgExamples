package quartaA.RipassoSocket.Es1.Client;

import quartaB.RipassoSocket.Es1.Server.ServerMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {

        //ConnectExp => non riesco a connettermi (server is down)
        //UnknownHostExp => ho sbagliato a scrivere l'IP...
        // localhost = 127.0.0.1
        try {
            Socket s = new Socket("localhost", ServerMain.PORT);

            BufferedReader fromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter toServer = new PrintWriter(s.getOutputStream(), true);

            //Creare e mandare a server
            Scanner scanner = new Scanner(System.in);
            System.out.print("We, dammi una stringa: ");
            String toSend = scanner.nextLine();
            toServer.println(toSend);

            //Leggere dal server la risposta
            String serverResponse = fromServer.readLine();
            System.out.println(serverResponse);

            s.close();


        } catch (ConnectException c) {
            System.out.println("Server non attivo...");
        } catch (UnknownHostException unknownHostException) {
            System.out.println("Indirizzo sconosciuto");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
