import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class Librarian extends User {

    Librarian() {
        this.id = "FXXXXXXXXXXXX";
        this.password = "admin";
    }
    public void setFine(double fine){
        this.fine = fine;
    }
    public void addBook(Book b) throws SQLException, ClassNotFoundException {
        Database_DAO dao = new Database_DAO();
        int a = dao.addBookToDB(b);
        if(a!=0) System.out.println("Book Added Successfully");
        else System.out.println("Book not added");
    }
    public void removeBook(String isbn) throws SQLException, ClassNotFoundException {
        Database_DAO dao = new Database_DAO();
        int a = dao.deleteBookFromDB(isbn);
        if(a!=0) System.out.println("Book Deleted!");
        else System.out.println("Deletion failed");
    }
    public void reviewUser() throws SQLException, ClassNotFoundException {
        Database_DAO dao = new Database_DAO();
        dao.reviewstudentDB();
    }
    public void reviewBook() throws SQLException, ClassNotFoundException {
        Database_DAO dao = new Database_DAO();
        dao.reviewbookDB();
    }
//    public void changeBookName(String bookName, String newName) throws ClassNotFoundException {
//        try{
//            Database.changeBookName(this, bookName, newName);
//        }
//        catch (BookNotFoundException e){
//            System.out.println(e.getMessage());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public void changeBookAuthor(String bookName, String newName) throws ClassNotFoundException {
//        try{
//            Database.changeBookAuthor(this, bookName, newName);
//        }
//        catch (BookNotFoundException | SQLException e){
//            System.out.println(e.getMessage());
//        }
//    }
}
