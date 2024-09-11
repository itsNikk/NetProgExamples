package quartaB.RipassoSocket.Es1SerializedVersion.Server;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class SerializedServerMain {

    public static final int PORT = 1324;
    public static final String ADDRESS = "127.0.0.1";

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server (serializzato) aperto su porta: " + PORT);

            while (true) {
                //è arrivato un nuovo client...
                Socket newClient = serverSocket.accept();
                System.out.println("Arrivato nuovo client... " + newClient.getInetAddress());

                BufferedReader fromCLient = new BufferedReader(new InputStreamReader(newClient.getInputStream()));
                ObjectOutputStream toCLient = new ObjectOutputStream(newClient.getOutputStream());

                // Leggere la stringa del client
                String valueFromClient = fromCLient.readLine();
                System.out.println(valueFromClient);

                // Calcolo totale caratteri
                int totalChars = valueFromClient.length();

                // Calcolo vocali e consonanti
                int vowelsCount = 0, consonantsCount = 0;
                for (char c : valueFromClient.toCharArray()) {
                    if (!Character.isLetter(c)) continue;

                    if (isVowel(c)) vowelsCount++;
                    else consonantsCount++;
                }

                // Spedire a client i risultati tramite oggetto custom
                toCLient.writeObject(new StringAnalysisResults(totalChars, vowelsCount, consonantsCount));
                toCLient.flush();

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
        //IndexOf restituisce il numero intero (l'indice) corrispondente alla posizione di c nella stringa di partenza
        // se non esiste allora indexOf restituirà -1;
        return "aeiouAEIOU".indexOf(c) >= 0;
    }
}
