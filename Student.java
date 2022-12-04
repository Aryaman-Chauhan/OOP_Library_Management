import java.sql.SQLException;
import java.util.*;

public class Student extends User {
    private String name;
Student(String name, String id, String password) { //Constructor for Student which takes 3 arguments and initializes the parameters
        this.name = name;
        this.id = id;
        super.setPassword(password);
    }

    public String getName() { //Gets the name of the student
        return name;
    }

    public void setName(String name) { //Sets the name of the student
        this.name = name;
    }

    @Override
    public String toString() { //Returns the name instance field of the Student class
            return "Name: " + this.getName()+"\nId: "+this.getID()+"\n";
    }

    public void borrowBook(String name) throws SQLException, ClassNotFoundException, InterruptedException { //Issues a book to the student
        Database_DAO dao = new Database_DAO();
        int idno = dao.getUserId(this.getID());
        int a = dao.issueBookDB(idno, name);
        if(a==-1) System.out.println("Max Limit Reached");
        else if(a==1) System.out.println("Book Issued");
        else System.out.println("Issue failed");
    }

    public void returnBook(String name) throws SQLException, ClassNotFoundException { //A book is returned by the student
        int idno = new Database_DAO().getUserId(this.getID());
        if (idno != 0) {
            HashMap<Double, Double> hs = new Database_DAO().returnBookDB(idno, name);
            for (Double d : hs.keySet()) {
                if (d != 0) {
                    System.out.println("Dues: " + hs.get(d));
                } else
                    System.out.println("Cannot return book");
            }
        }
    }
    public void reissueBook(String name) throws SQLException, ClassNotFoundException, InterruptedException {
            Database_DAO dao = new Database_DAO();
            int a = dao.reissueBookDB(name);
            if(a!=0) System.out.println("Book re-issued successfully");
            else System.out.println("Can't reissue this book");

    }

}
