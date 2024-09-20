package quartaA.LibraryService.Client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Library implements Serializable {

    public static final long serialVersionUID = 1;

    private List<Book> library;

    public Library() {
        this.library = new ArrayList<>();
    }

    public void addBook(Book book) {
        library.add(book);
    }

    public List<Book> getLibrary() {
        return library;
    }
}
