public class BookNotAvailableException extends Exception{
    public BookNotAvailableException(String name) {
        super(name + " already borrowed by some user!");
    }
}
