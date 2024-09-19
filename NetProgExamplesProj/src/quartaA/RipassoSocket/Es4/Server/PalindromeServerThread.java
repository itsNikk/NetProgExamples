package quartaA.RipassoSocket.Es4.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PalindromeServerThread extends Thread {

    private Socket clientToServe;
    private BufferedReader fromClient;
    private PrintWriter toClient;

    public PalindromeServerThread(Socket socket) {
        this.clientToServe = socket;
        bindClientStreams();
    }


    private void bindClientStreams() {
        try {
            this.toClient = new PrintWriter(clientToServe.getOutputStream(), true);
            this.fromClient = new BufferedReader(new InputStreamReader(clientToServe.getInputStream()));
        } catch (IOException ioException) {
            System.out.println("Non sono riuscito a collegare gli stream del client");
            try {
                clientToServe.close();
            } catch (IOException e) {
                System.out.println("Problema a chiudere la socketdel client");
            }
        }
    }

    @Override
    public void run() {
        try {
            String valueFromClient = fromClient.readLine();

            //Validazione
            if (valueFromClient == null || valueFromClient.isEmpty()) {
                toClient.println("ERROR");
            } else {
                if (isPalindrome(valueFromClient)) {
                    toClient.println("La stringa è palindroma");
                } else {
                    toClient.println("La stringa non è palindroma");
                }
            }

            clientToServe.close();

        } catch (IOException ioException) {
            System.out.println("Problemi sugli stream di comunicazione");
        }
    }

    private boolean isPalindrome(String stringToCheck) {
        //Rimuovo eventuali spazi,farebbero fallire il controllo
        //Considero tutta la stringa in minuscolo perchè, ai fini del controllo della palindromia,
        //non è importante distinguere le maiuscole da minuscole
        stringToCheck = stringToCheck.toLowerCase().replace(" ", "");

        int left = 0;
        int right = stringToCheck.length() - 1;
        while (left < right) {
            if ((stringToCheck.charAt(left) != stringToCheck.charAt(right))) return false;
            left++;
            right--;
        }
        return true;
    }
}
