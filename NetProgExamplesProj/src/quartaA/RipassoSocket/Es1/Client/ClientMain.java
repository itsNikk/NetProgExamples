package quartaA.RipassoSocket.Es1.Client;

import quartaB.RipassoSocket.Es1.Server.ServerMain;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {

        try {
            Socket s = new Socket("localhost", ServerMain.PORT);

            PrintWriter toServer = new PrintWriter(s.getOutputStream(), true);
            BufferedReader fromServer =
                    new BufferedReader(new InputStreamReader(s.getInputStream()));

            toServer.println("ciao");

            System.out.println(fromServer.readLine());

            s.close();


        } catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}
