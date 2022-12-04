import java.sql.SQLException;

public class Librarian extends User {

    Librarian() { //Constructor for Librarian initializing parameters if no argument is passed
        this.id = "ADMIN";
        this.password = "admin";
    }
    public void setFine(double fine){ //Sets the fine value
        User.fine = fine;
    }
    public void addBook(Book b) throws SQLException, ClassNotFoundException { //Adds a book B to the SQL database 
        Database_DAO dao = new Database_DAO();
        int a = dao.addBookToDB(b);
        if(a!=0) System.out.println("Book Added Successfully");
        else System.out.println("Book not added");
    }
    public void removeBook(String isbn) throws SQLException, ClassNotFoundException { //Removes a book with passed isbn code from the SQL database
        Database_DAO dao = new Database_DAO();
        int a = dao.deleteBookFromDB(isbn);
        if(a!=0) System.out.println("Book Deleted!");
        else System.out.println("Deletion failed");
    }
    public void reviewUser() throws SQLException, ClassNotFoundException { //Reviews the student database
        Database_DAO dao = new Database_DAO();
        dao.reviewstudentDB();
    }
    public void reviewBook() throws SQLException, ClassNotFoundException { //Reviews the book database
        Database_DAO dao = new Database_DAO();
        dao.reviewbookDB();
    }
//    public void changeBookName(String bookName, String newName) throws ClassNotFoundException { //Changes the name of a book
//        try{
//            Database.changeBookName(this, bookName, newName);
//        }
//        catch (BookNotFoundException e){
//            System.out.println(e.getMessage());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public void changeBookAuthor(String bookName, String newName) throws ClassNotFoundException { //Changes the author of a book
//        try{
//            Database.changeBookAuthor(this, bookName, newName);
//        }
//        catch (BookNotFoundException | SQLException e){
//            System.out.println(e.getMessage());
//        }
//    }
}
