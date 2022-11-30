import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;

class dueChecker implements Runnable {

    public dueChecker() {
        Thread t = new Thread(this, "Due Checker");
        t.start();
    }

    @Override
    public void run() {
        while(true){
            Date d = new Date();
            String day = d.toString().substring(0,10);
            for (;;) {
                try{
                    Thread.sleep(600000);
                    Date date=new Date();
                    String currentDay=date.toString().substring(0,10);
                    if(!day.equals(currentDay)){
                        break;
                    }
                } catch(Exception e){
                    System.out.println(e.getMessage());
                }
                d = new Date();
            }
            HashSet<Student> hs= Database.getStudentDatabase();
            LocalDate currDate = LocalDate.now();

            for (Student s : hs) {
                TreeMap<Book, LocalDate> tm = s.getCurrBooks();

                for (Book b : tm.keySet()) {
                    LocalDate ch = tm.get(b);
                    if (currDate.isAfter(ch)) {
                        System.out.println("Student " + s.getName() + "'s borrowed book " + b.getName() + "is currently due! Book was due on " + tm.get(b).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "!");
                        s.setDues(s.getDues() + 10);
                    }
                }
            }
        }
    }
}

public class Database {
    private static HashSet<Student> studentDatabase = new HashSet<>();
    private static HashSet<Book> bookList = new HashSet<>();
    private static HashSet<Librarian> adminList = new HashSet<>();

    dueChecker dc = new dueChecker();

    public static HashSet<Student> getStudentDatabase() {
        return studentDatabase;
    }

    public static HashSet<Book> getBookList() {
        return bookList;
    }

    public static void addStudent(Student s) {
        studentDatabase.add(s);
        System.out.println("Student Added!");
        System.out.println(s.getName());
    }

    public static void addBook(Book b) {
        for (Book s : bookList) {
            if (b.equals(s)) {
                System.out.println(b.getName() + " already present in Library!");
                return;
            }
        }
        bookList.add(b);
        System.out.println("Book Added!");
        System.out.println(b);
    }

    public static void removeBook(String name) {
        Book r = null;

        for (Book b : bookList) {
            if (b.getName().equals(name)) {
                r = b;
            }
        }

        if (r == null) {
            System.out.println("Book not present in Library!");
        }
        else {
            bookList.remove(r);
            System.out.println("Book Removed!");
            System.out.println(r);
        }
    }

    public static int issueBook(Student s, String name) {
        Book retBook = null;
        int a = 0;

        for (Book b : bookList) {
            if (b.getName().equalsIgnoreCase(name) && b.isAvailable()) {
                retBook = b;
                b.setAvailable(false);
            }
            else if (b.getName().equalsIgnoreCase(name) && !b.isAvailable()) {
                a = 2;
            }
        }

        if (retBook != null) {
            TreeMap<Book, LocalDate> tm = s.getCurrBooks();
            if (tm == null) {
                tm = new TreeMap<>();
            }

            if (tm.size() > 3) {
                return 3;
            }

            tm.put(retBook, LocalDate.now().plusDays(15));
            s.setCurrBooks(tm);
            a = 1;
        }
        return a;
    }

    public static int returnBook(Student s, String name) {
        TreeMap<Book, LocalDate> tm = s.getCurrBooks();
        boolean found = false;

        for (Book b : tm.keySet()) {
            if (b.getName().equalsIgnoreCase(name)) {
                found = true;
            }
        }

        if (!found) return 0;

        for (Book b : tm.keySet()) {
            if (b.getName().equalsIgnoreCase(name)) {
                tm.remove(b);
                b.setAvailable(true);
                break;
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        //Testing

        Student s = new Student("rad", "rad", "rad");
        addStudent(s);
        System.out.println();

        addBook(new Book("Atomic Habits", "James Clear"));
        System.out.println();
        addBook(new Book("Ikigai", "Hector Garcia"));
        System.out.println();
        addBook(new Book("War of Lanka", "Amish Tripathi"));
        System.out.println();

        System.out.println(bookList);

        s.borrowBook("war of lanka");
        s.borrowBook("atomic habits");
        System.out.println();
        System.out.println(s.getCurrBooks());
        System.out.println(bookList);
        s.returnBook("atomic habits");
        System.out.println(s.getCurrBooks());
        System.out.println(bookList);
    }
}
