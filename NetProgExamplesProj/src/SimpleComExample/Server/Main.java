package SimpleComExample.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        Server s = new Server(ServerInfo.serverPort);
        try {
            s.start();
        } catch (IOException e) {
            System.out.println("Can't start server.");
        }
    }


}