package SimpleComExample.Server;

public class ServerMain {
    public static void main(String[] args) {
        final int port = 8080;
        Server s = new Server(port);
        s.start();
    }
}
