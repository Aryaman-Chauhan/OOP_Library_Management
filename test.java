import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.DuplicateFormatFlagsException;

public class test {
    public static void main(String[] args) {
        dueChecker dc = new dueChecker();
        Student s = new Student("rad", "rad", "rad");
        Database.addStudent(s);
        System.out.println();

        Database.addBook(new Book("Atomic Habits", "James Clear"));
        System.out.println();
        Database.addBook(new Book("Ikigai", "Hector Garcia"));
        System.out.println();
        Database.addBook(new Book("War of Lanka", "Amish Tripathi"));
        System.out.println();

        s.borrowBook("Atomic habits");
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(s.getDues());

//        System.out.println(Database.getBookList());
//
//        s.borrowBook("war of lanka");
//        System.out.println();
//        s.borrowBook("atomic habits");
//        System.out.println();
//        System.out.println();
//        System.out.println(s.getCurrBooks());
//        System.out.println(Database.getBookList());
//        s.returnBook("atomic habits");
//        System.out.println(s.getCurrBooks());
//        System.out.println(Database.getBookList());
    }
}
