package quartaA.RipassoSocket.Es4.Client;

import quartaA.RipassoSocket.Es4.Server.PalindromeServer;

public class PalindromeClient {
    private static final int NUM_PALINDROME_THREADS = 10;
    private static final int NUM_NON_PALINDROME_THREADS = 10;

    public static void main(String[] args) {

        String hostName = "localhost";
        int hostPort = PalindromeServer.PORT;

        for (int i = 0; i < NUM_PALINDROME_THREADS; i++)
            new PalindromeThreadClient("PaliThread" + (i + 1), hostName, hostPort, "Annnnna").start();

        for (int i = 0; i < NUM_NON_PALINDROME_THREADS; i++)
            new PalindromeThreadClient("NonPaliThread" + (i + 1), hostName, hostPort, "JustAString" + (i + 1)).start();


    }
}
