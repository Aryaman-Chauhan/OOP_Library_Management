//import java.sql.SQLException;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//import java.time.LocalDate;
//
//class dueChecker implements Runnable {
//
//    public dueChecker() {
//        Thread t = new Thread(this, "Due Checker");
//        t.start();
//    }
//
//    @Override
//    public void run() {
//        while(true){
//            Date d = new Date();
//            String day = d.toString().substring(0,10);
//
//            for (;;) {
//                try{
//                    Thread.sleep(600000);
//                } catch(Exception e){
//                    System.out.println(e.getMessage());
//                }
//                Date date = new Date();
//                String currentDay = date.toString().substring(0,10);
//                if(!day.equals(currentDay)){
//                    break;
//                }
//            }
//            HashSet<Student> hs = Database.getStudentDatabase();
//            LocalDate currDate = LocalDate.now();
//
//            for (Student s : hs) {
//                TreeMap<Book, LocalDate> tm = s.getCurrBooks();
//
//                for (Book b : tm.keySet()) {
//                    LocalDate ch = tm.get(b);
//                    if (currDate.isAfter(ch)) {
//                        System.out.println("Student " + s.getName() + "'s borrowed book " + b.getName() + "is currently due! Book was due on " + tm.get(b).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "!");
//                        s.setDues(s.getDues() + 1);
//                    }
//                }
//            }
//        }
//    }
//}
//
//public class Database implements searchByName{
//    static Database_DAO dao = new Database_DAO();
//
//    private static HashSet<Student> studentDatabase = new HashSet<>();
//    private static HashSet<Book> bookList = new HashSet<>();
//    private static HashSet<Librarian> adminList = new HashSet<>();
//
//    public static HashSet<Student> getStudentDatabase() {
//        return studentDatabase;
//    }
//
//    public static HashSet<Book> getBookList() {
//        return bookList;
//    }
//
//    public static void addStudent(Student s) {
//        studentDatabase.add(s);
//        System.out.println("Student Added!");
//        System.out.println(s.getName());
//    }
//
//    public static void addBook(Book b) {
//        for (Book s : bookList) {
//            if (b.equals(s)) {
//                System.out.println(b.getName() + " already present in Library!");
//                return;
//            }
//        }
//        bookList.add(b);
//        System.out.println("Book Added!");
//        System.out.println(b);
//    }
//
//    public static void removeBook(String name) throws SQLException, ClassNotFoundException {
//        Book r = searchByName.search(name,dao);
//        dao.closeCon();
//        if (r == null) {
//            System.out.println("Book not present in Library!");
//        }
//        else {
//            bookList.remove(r);
//            System.out.println("Book Removed!");
//            System.out.println(r);
//        }
//    }
//
//    public static void issueBook(Student s, String name) throws MaxBookLimitException, BookNotFoundException, BookNotAvailableException, SQLException, ClassNotFoundException {
//        Book retBook = null;
//        Book b = searchByName.search(name,dao);
//        dao.closeCon();
//        if (b == null) {
//            throw new BookNotFoundException(name);
//        }
//
//        else if (b.isAvailable()) {
//            retBook = b;
//            b.setAvailable(false);
//        }
//        else if (!b.isAvailable()){
//            throw new BookNotAvailableException(name);
//        }
//
//        if (retBook != null) {
//            TreeMap<Book, LocalDate> tm = s.getCurrBooks();
//            if (tm == null) {
//                tm = new TreeMap<>();
//            }
//
//            if (tm.size() >= 3) {
//                throw new MaxBookLimitException();
//            }
//
//            else {
//                tm.put(retBook, LocalDate.now().plusDays(15));
//                s.setCurrBooks(tm);
//            }
//        }
//    }
//
//    public static void changeBookName(Librarian admin, String bookName, String newName) throws BookNotFoundException, SQLException, ClassNotFoundException {
//        Book retBook = searchByName.search(bookName,dao);
//        dao.closeCon();
//        if(retBook==null){
//            throw new BookNotFoundException(bookName);
//        }
//
//        else{
//            retBook.setName(newName);
//        }
//    }
//
//    public static void changeBookAuthor(Librarian admin, String bookName, String newName) throws BookNotFoundException, SQLException, ClassNotFoundException {
//        Book retBook = searchByName.search(bookName,dao);
//        dao.closeCon();
//        if(retBook==null){
//            throw new BookNotFoundException(bookName);
//        }
//
//        else {
//            retBook.setAuthor(newName);
//        }
//    }
//
//    public static int returnBook(Student s, String name) {
//        TreeMap<Book, LocalDate> tm = s.getCurrBooks();
//
//        for (Book b : tm.keySet()) {
//            if (b.getName().equalsIgnoreCase(name)) {
//                tm.remove(b);
//                b.setAvailable(true);
//                return 1;
//            }
//        }
//
//        return 0;
//    }
//}
