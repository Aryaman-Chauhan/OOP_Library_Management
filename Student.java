import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;

public class Student extends User {
    private String name;
    private TreeMap<Book, LocalDate> currBooks;
    private double dues;

    Student(String name, String id, String password) {
        this.name = name;
        this.id = id;
        super.setPassword(password);
        currBooks = new TreeMap<>();
        dues = 0;
    }

    public double getDues() {
        return dues;
    }

    public String getName() {
        return name;
    }

    public TreeMap<Book, LocalDate> getCurrBooks() {
        return currBooks;
    }

    public void setCurrBooks(TreeMap<Book, LocalDate> currBooks) {
        this.currBooks = currBooks;
    }

    public void setDues(double dues) {
        this.dues = dues;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Name: " + name;
    }

    public void borrowBook(String name){
        int a = currBooks.size();

        try{
            Database.issueBook(this, name);
        }
        catch(/*MaxBookLimitException | BookNotFoundException | BookNotAvailableException |*/ Exception e) {
            System.out.println(e.getMessage());
        }
        if (a != currBooks.size()) {
            System.out.println(name + " borrowed! Due on " + LocalDate.now().plusDays(15).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }

    public void returnBook(String name) {
        int a = Database.returnBook(this, name);

        if (a == 0) {
            System.out.println(name.formatted() + " not currently borrowed by you!");
        }
        else if (a == 1) {

            System.out.println(name + " successfully returned!");
        }
    }
}
