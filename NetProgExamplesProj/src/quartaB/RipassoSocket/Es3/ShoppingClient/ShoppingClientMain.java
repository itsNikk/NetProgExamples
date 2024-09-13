package quartaB.RipassoSocket.Es3.ShoppingClient;

import quartaB.RipassoSocket.Es3.ShoppingServer.Item;
import quartaB.RipassoSocket.Es3.ShoppingServer.ShoppingList;
import quartaB.RipassoSocket.Es3.ShoppingServer.ShoppingServerMain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ShoppingClientMain {
    public static void main(String[] args) {
        try (Socket s = new Socket("localhost", ShoppingServerMain.PORT)) {

            ObjectOutputStream toServer = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(s.getInputStream());

            ShoppingList shoppingList = new ShoppingList();
            shoppingList.addItem(new Item("Mela", 5, 3.25));
            shoppingList.addItem(new Item("Matita", 15, 1.64));
            shoppingList.addItem(new Item("Coca-Cola", 3, 4));


            toServer.writeObject(shoppingList);
            toServer.flush();

            double totalSpent = fromServer.readDouble();
            System.out.println("Totale spesa: " + totalSpent + "€");

            Item cheapest = (Item) fromServer.readObject();
            Item costliest = (Item) fromServer.readObject();
            System.out.println("Item nella lista più costoso:  " + costliest);
            System.out.println("Item nella lista meno costoso:  " + cheapest);

        } catch (ConnectException connectException) {
            System.out.println("Server is down...");
        } catch (UnknownHostException e) {
            System.out.println("Host sconosciuto... ");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Occhio alla serializzazione");
        }
    }
}
