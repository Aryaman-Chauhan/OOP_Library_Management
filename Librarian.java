public class Librarian extends User {

    Librarian() {
        this.id = "admin";
        this.password = "admin";
    }

    public void changeBookName(String bookName, String newName){
        try{
            Database.changeBookName(this, bookName, newName);
        }
        catch (BookNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void changeBookAuthor(String bookName, String newName){
        try{
            Database.changeBookAuthor(this, bookName, newName);
        }
        catch (BookNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
