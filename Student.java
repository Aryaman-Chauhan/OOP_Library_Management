import java.sql.SQLException;
import java.util.*;
import java.time.LocalDate;

public class Student extends User {
    private String name;
    private TreeMap<Book, LocalDate> currBooks;
//    private double dues;
    Student(String name, String id, String password) {
        this.name = name;
        this.id = id;
        super.setPassword(password);
        currBooks = new TreeMap<>();
    }

//    public double getDues() {
//        return dues;
//    }

    public String getName() {
        return name;
    }

    public TreeMap<Book, LocalDate> getCurrBooks() {
        return currBooks;
    }

    public void setCurrBooks(TreeMap<Book, LocalDate> currBooks) {
        this.currBooks = currBooks;
    }

//    public void setDues(double dues) {
//        this.dues = dues;
//    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Name: " + name;
    }

    public void borrowBook(String name) throws SQLException, ClassNotFoundException {
        Database_DAO dao = new Database_DAO();
        int idno = dao.getUserId(this.getID());
        int a = dao.issueBookDB(idno, name);
        if(a!=0) System.out.println("Book Issued");
        else System.out.println("Issue failed");
    }

    public void returnBook(String name) throws SQLException, ClassNotFoundException {
        int idno = new Database_DAO().getUserId(this.getID());
        if(idno!=0) {
            HashMap<Double,Double> hs = new Database_DAO().returnBookDB(idno, name);
            for(Double d: hs.keySet()){
                if(d!=0){
                    System.out.println("Dues: "+hs.get(d));
                }
                else
                    System.out.println("Cannot return book");
            }
        }
    }

    public void reissueBook(String name) throws SQLException, ClassNotFoundException {
        Database_DAO dao = new Database_DAO();
        int a = dao.reissueBookDB(name);
        if(a!=0) System.out.println("Book re-issued successfully");
        else System.out.println("Can't reissue this book");
    }
}
