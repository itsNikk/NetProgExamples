package quartaB.RipassoSocket.Es1.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public static final int PORT = 1324;
    public static final String ADDRESS = "127.0.0.1";

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server aperto su porta: " + PORT);

            while (true) {
                //è arrivato un nuovo client...
                Socket newClient = serverSocket.accept();
                System.out.println("Arrivato nuovo client... " + newClient.getInetAddress());

                BufferedReader fromCLient = new BufferedReader(new InputStreamReader(newClient.getInputStream()));
                PrintWriter toCLient = new PrintWriter(newClient.getOutputStream(), true);

                //leggere la stringa del client
                String valueFromClient = fromCLient.readLine();
                System.out.println(valueFromClient);

                //Calcolo totale caratteri
                int totalChars = valueFromClient.length();
                int vowelsCount = 0, consonantsCount = 0;
                //Calcolo vocali e consonanti
                for (char c : valueFromClient.toCharArray()) {
                    if (!Character.isLetter(c)) continue;

                    if (isVowel(c)) vowelsCount++;
                    else consonantsCount++;
                }

                //Spedire a client i risultati
                /*
                TODO: Secondo voi, perchè questa soluzione non funziona? ;) (Continua..)
                toCLient.println("La stringa inviata ha: \n" +
                        "-" + totalChars + " caratteri \n" +
                        "-" + vowelsCount + " vocali \n" +
                        "-" + consonantsCount + " consonanti");*/

                //E invece questa funziona?
                toCLient.println("Vocali: " + vowelsCount + ", Consonanti: " + consonantsCount + ", Caratteri totali: " + totalChars);
                newClient.close();
            }

        } catch (IllegalArgumentException e) {
            System.out.println("La porta è illegale. Range consentito [0,65535]");
        } catch (BindException b) {
            System.out.println("Indirizzo già in uso :(");
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    private static boolean isVowel(char c) {
        //IndexOf restituisce il numero intero corrispondente alla posizione di c nella stringa di partenza
        // se non esiste allora indexOf restituirà -1;
        return "aeiouAEIOU".indexOf(c) >= 0;
    }
}
