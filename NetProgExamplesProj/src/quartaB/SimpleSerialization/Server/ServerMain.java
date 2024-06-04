package quartaB.SimpleSerialization.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(80)) {
            while (true) {
                Socket s = serverSocket.accept();
                //client mi manda oggetto, glielo restiuisco modificato
                ObjectInputStream fromClient = new ObjectInputStream(s.getInputStream());
                ObjectOutputStream toClient = new ObjectOutputStream(s.getOutputStream());

                User nativeUser = (User) fromClient.readObject();
                System.out.println(nativeUser);
                nativeUser.setAge(66);
                nativeUser.setSurname(nativeUser.getSurname().toUpperCase());

                toClient.writeObject(nativeUser);
                toClient.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
