package quartaA.RipassoSocket.Es4.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class PalindromeClient {
    public static void main(String[] args) {
        try (Socket s = new Socket("localhost", 12345)) {

            BufferedReader fromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter toServer = new PrintWriter(s.getOutputStream(), true);

            //Leggi stringa da console
            Scanner scanner = new Scanner(System.in);
            System.out.print("Inserisci una stringa: ");
            String valueToSend = scanner.nextLine();

            toServer.println(valueToSend);

            String responseFromServer = fromServer.readLine();
            System.out.println(responseFromServer);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (UnknownHostException e) {
            System.out.println("Host sconosciuto");
        } catch (ConnectException c) {
            System.out.println("SERVER DOWN");
        } catch (IOException e) {
            System.out.println("Generico errore di rete");
        }
    }
}
