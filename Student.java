import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;

public class Student extends User {
    private String name;
    private TreeMap<Book, LocalDate> currBooks;
    private double dues;
    Student(String name, String id, String password) { //Constructor for Student which takes 3 arguments and initializes the parameters
        this.name = name;
        this.id = id;
        super.setPassword(password);
        currBooks = new TreeMap<>();
        dues = 0;
    }

    public double getDues() { //Gets the dues owed by the student
        return dues;
    }

    public String getName() { //Gets the name of the student
        return name;
    }

    public TreeMap<Book, LocalDate> getCurrBooks() { //Gets a treemap having books borrowed by the student
        return currBooks;
    }

    public void setCurrBooks(TreeMap<Book, LocalDate> currBooks) { //Sets the treemap having books borrowed by the student
        this.currBooks = currBooks;
    }

    public void setDues(double dues) { //Sets the dues owed by the student
        this.dues = dues;
    }

    public void setName(String name) { //Sets the name of the student
        this.name = name;
    }

    @Override
    public String toString() { //Returns the name instance field of the Student class
            return "Name: " + name;
    }

    public void borrowBook(String name) throws SQLException, ClassNotFoundException { //Issues a book to the student
        Database_DAO dao = new Database_DAO();
        int idno = dao.getUserId(this.getID());
        int a = dao.issueBookDB(idno, name);
        if(a!=0) System.out.println("Book Issued");
        else System.out.println("Issue failed");
    }

    public void returnBook(String name) throws SQLException, ClassNotFoundException { //A book is returned by the student
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
}
