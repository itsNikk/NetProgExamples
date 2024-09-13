package quartaB.RipassoSocket.Es3.ShoppingServer;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingList implements Serializable {

    @Serial
    private static final long serialVersionUID = -1564774715387753270L;
    private final List<Item> shoppingCart;

    public ShoppingList(List<Item> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public ShoppingList() {
        this.shoppingCart = new ArrayList<>();
    }

    public void addItem(Item item) {
        shoppingCart.add(item);
    }

    public void removeItem(Item itemToRemove){
        shoppingCart.remove(itemToRemove);
    }

    public List<Item> getList() {
        return shoppingCart;
    }
}
