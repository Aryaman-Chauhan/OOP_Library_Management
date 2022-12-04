import java.util.HashSet;

public interface searchByISBN {
    static Book search(String isbn) {
        Book retBook = null;
        HashSet<Book> bookList = Database.getBookList();
        for (Book b : bookList) {
            if (b.getName().equalsIgnoreCase(isbn)) {
                retBook = b;
                break;
            }
        }

        return retBook;
    }
}
