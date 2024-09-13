package quartaB.RipassoSocket.Es3.ShoppingServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ShoppingServerMain {
    public static final int PORT = 5421;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server started...");
            while (true) {
                Socket s = serverSocket.accept();
                System.out.println("New client arrived:> " + s.getInetAddress());

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());

                ShoppingList shoppingList = (ShoppingList) objectInputStream.readObject();

                double total = 0;
                System.out.println("--- Computing total ---");
                Item cheapest = shoppingList.getList().get(0);
                Item costliest = shoppingList.getList().get(0);

                for (Item item : shoppingList.getList()) {
                    System.out.println(item);
                    total += item.getPrice();
                    if (item.getPrice() > costliest.getPrice()) costliest = item;
                    if (item.getPrice() < cheapest.getPrice()) cheapest = item;
                }

                objectOutputStream.writeDouble(total);
                objectOutputStream.writeObject(cheapest);
                objectOutputStream.writeObject(costliest);

                objectOutputStream.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Occhio alla serializzaione");
        }
    }
}
