package quartaB.RipassoSocket.Es3.ShoppingServer;

import java.io.Serial;
import java.io.Serializable;

public class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = -7415412463281847270L;
    private String name;
    private int quantity;
    private double price;


    public Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + ": (" + quantity + ":" + price + "€)";
    }
}
