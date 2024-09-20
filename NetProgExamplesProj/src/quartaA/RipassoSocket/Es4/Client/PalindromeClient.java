package quartaA.RipassoSocket.Es4.Client;

import quartaA.RipassoSocket.Es4.Server.PalindromeServer;

import java.util.Random;

public class PalindromeClient {
    private static final int NUM_PALINDROME_THREADS = 10;
    private static final int NUM_NON_PALINDROME_THREADS = 10;
    private static Random r;

    public static void main(String[] args) {

        r = new Random();

        String hostName = "localhost";
        int hostPort = PalindromeServer.PORT;

        for (int i = 0; i < NUM_PALINDROME_THREADS; i++)
            new PalindromeThreadClient("PaliThread" + (i + 1), hostName, hostPort, generateRandomString(9, true)).start();

        for (int i = 0; i < NUM_NON_PALINDROME_THREADS; i++)
            new PalindromeThreadClient("NonPaliThread" + (i + 1), hostName, hostPort, generateRandomString(10, false)).start();

    }

    private static String generateRandomString(int desiredLength, boolean isPalindrome) {
        //Possono essere anche meno ovviamente...
        String admittedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String result = "";

        /*
         * OPERATORE TERNARIO -> condizione ? valueIfTrue : valueIfFalse
         * int lengthToConsider = isPalindrome ? (desiredLength + 1) / 2 : desiredLength;
         *
         * La riga sopra si traduce come segue
         * if(isPalindrome) {
         *   lengthToConsider = (desiredLength + 1) / 2
         * } else {
         *   lengthToConsider = desiredLength;
         * }
         * */
        int lengthToConsider = isPalindrome ? (desiredLength + 1) / 2 : desiredLength;
        for (int i = 0; i < lengthToConsider; i++) {
            result += admittedCharacters.charAt(r.nextInt(admittedCharacters.length()));
        }

        if (isPalindrome) {
            //Parto dalla stringa che ho fin'ora (lunga metà del necessario)
            //E costruisco la metà rimanente come specchio della prima.
            for (int i = (desiredLength / 2) - 1; i >= 0; i--) result += result.charAt(i);
        }

        return result;
    }
}
