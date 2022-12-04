import java.util.HashSet;

public interface searchByName {
    static Book search(String name) {
        Book retBook = null;
        HashSet<Book> bookList = Database.getBookList();
        for (Book b : bookList) {
            if (b.getName().equalsIgnoreCase(name)) {
                retBook = b;
                break;
            }
        }

        return retBook;
    }
}
