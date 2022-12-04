public class BookNotAvailableException extends Exception{ //Exception definition if a book is unavailable and user tries to borrow it
    public BookNotAvailableException(String name) {
        super(name + " already borrowed by some user!");
    }
}
