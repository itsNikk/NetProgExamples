package quartaB.RipassoSocket.Es1SerializedVersion.Client;

import quartaB.RipassoSocket.Es1SerializedVersion.Server.SerializedServerMain;
import quartaB.RipassoSocket.Es1SerializedVersion.Server.StringAnalysisResults;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SerializedClientMain {
    public static void main(String[] args) {

        //ConnectExp => non riesco a connettermi (server is down)
        //UnknownHostExp => ho sbagliato a scrivere l'IP...
        // localhost = 127.0.0.1

        try {
            Socket s = new Socket(SerializedServerMain.ADDRESS, SerializedServerMain.PORT);

            ObjectInputStream fromServer = new ObjectInputStream(s.getInputStream());
            PrintWriter toServer = new PrintWriter(s.getOutputStream(), true);

            //Creare e mandare a server
            Scanner scanner = new Scanner(System.in);
            System.out.print("We, dammi una stringa: ");
            String toSend = scanner.nextLine();
            toServer.println(toSend);

            //Leggere dal server la risposta
            StringAnalysisResults serverResponse = (StringAnalysisResults) fromServer.readObject();
            System.out.println("La string inviata al server ha: ");
            System.out.println("- " + serverResponse.getTotalChars() + " caratteri totali");
            System.out.println("- " + serverResponse.getVowelsCount() + " vocali");
            System.out.println("- " + serverResponse.getConsonantsCount() + " consonanti");

            //TODO: Cosa stampa la riga qui sotto e perchè? :)
            //System.out.println(serverResponse);

            s.close();


        } catch (ConnectException c) {
            System.out.println("Server non attivo...");
        } catch (UnknownHostException unknownHostException) {
            System.out.println("Indirizzo sconosciuto");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Si è cercato di leggere un oggetto dal server non castato/deserializzato come ci si aspettava.");
        }


    }
}
