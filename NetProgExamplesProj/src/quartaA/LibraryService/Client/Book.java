package quartaA.LibraryService.Client;

import java.io.Serializable;

public class Book implements Serializable {

    public static final long serialVersionUID = 2;
    private String nome;
    private double price;
    private int numberOfPages;

    public Book(String nome, double price, int numberOfPages) {
        this.nome = nome;
        this.price = price;
        this.numberOfPages = numberOfPages;
    }

    public String getNome() {
        return nome;
    }

    public double getPrice() {
        return price;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }
}
