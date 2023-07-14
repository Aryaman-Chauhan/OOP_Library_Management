public class BookNotFoundException extends Exception{ //Exception definiton if a user tries a access a book which does not exist
    public BookNotFoundException(String name) {
        super(name + " not found");
    }
}
