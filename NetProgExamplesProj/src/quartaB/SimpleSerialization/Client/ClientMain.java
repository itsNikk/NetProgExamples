package quartaB.SimpleSerialization.Client;

import quartaB.SimpleSerialization.Server.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {
    public static void main(String[] args) {
        try (Socket s = new Socket("localhost", 80)) {
            ObjectOutputStream toServer = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(s.getInputStream());

            toServer.writeObject(new User("Nik", "Rossi", 22));
            toServer.flush();
            User modifiedUser = (User) fromServer.readObject();
            System.out.println(modifiedUser);

        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
