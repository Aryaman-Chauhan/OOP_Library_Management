public class BookNotFoundException extends Exception{
    public BookNotFoundException(String name) {
        super(name + " not found");
    }
}
