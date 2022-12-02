public class Librarian extends User {

    Librarian() {
        this.id = "admin";
        this.password = "admin";
    }

    public void changeBookName(Book b, String newName){
        b.setName(newName);
    }

    public void changeBookAuthor(Book b, String newName){
        b.setAuthor(newName);
    }
}
