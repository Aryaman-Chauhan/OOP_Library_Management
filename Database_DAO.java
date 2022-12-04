import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.HashSet;

class BookIssuer implements Runnable{
    static String bookname;
    static int idNo;

    public int ret;

    public BookIssuer(int id, String name){
        bookname = name;
        idNo = id;
        Thread t = new Thread(this);
        t.start();
    }
    @Override
    public void run() {
        ret = issueBook();
    }
    synchronized static int issueBook(){
        try{
            Database_DAO.connect();
            String q = "SELECT COUNT(book.bname) FROM book WHERE bookissue=?";
            PreparedStatement qst = Database_DAO.con.prepareStatement(q);
            qst.setInt(1, idNo);
            ResultSet rs1 = qst.executeQuery();
            rs1.next();
            int t = rs1.getInt(1);
            if(t >= 3) return -1;
            String query = "UPDATE book SET bookissue=?,duedate=adddate(current_date(),15) WHERE bname=? and bookissue=1";

            Database_DAO.pst = Database_DAO.con.prepareStatement(query);
            Database_DAO.pst.setInt(1,idNo);
            Database_DAO.pst.setString(2,bookname);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
        try {
            return Database_DAO.pst.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println(e.getMessage());
            return 0;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

}
public class Database_DAO{


    static Connection con = null;
    static PreparedStatement pst = null;

    static public void connect() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/bits_library";
        String uname = "root";
        String pwd = "12345678";
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url,uname,pwd);
    }

    static public void resetDBToLib() throws SQLException, ClassNotFoundException {
        connect();
        String query = "UPDATE book SET bookissue=1,duedate=NULL";
        pst = con.prepareStatement(query);
        pst.executeUpdate();//To reset Books at the initial stage of implementation
    }
    static public int addStudentToDB(Student s) throws SQLException, ClassNotFoundException {
        connect();
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
        String query = "INSERT INTO student(sid,sname,pwd,regdate) VALUES(?,?,?,?)";
        PreparedStatement pst;
        pst = con.prepareStatement(query);
        pst.setString(1, s.id);
        pst.setString(2, s.getName());
        pst.setString(3, s.password);
        pst.setDate(4,sqlDate);
        try {
            return pst.executeUpdate();//If this i is 0, no updates made, else update successful
        }catch (SQLIntegrityConstraintViolationException e){
            return 0;
        }
    }

    static public int deleteStudentFromDB(String sid) throws SQLException, ClassNotFoundException {
        connect();
        String query = "DELETE FROM student WHERE sid=?";
        pst = con.prepareStatement(query);
        pst.setString(1,sid);
        try{
            return pst.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            return 0;
        }
    }

    static public Student signIn(String id, String password) throws SQLException, ClassNotFoundException {
        connect();
        String sid = id;
        String pass = password;
        String query = "SELECT sname,sid,pwd FROM student WHERE sid=? and pwd=?";
        pst = con.prepareStatement(query);
        pst.setString(1,sid);
        pst.setString(2,pass);
        ResultSet rs = pst.executeQuery();// This ResultSet can be used to extract data like idno, pwd, etc.
        // If null, then wrong info, null can be checked using boolean rs.next()
        if(rs.next()) return new Student(rs.getString(1),rs.getString(2),rs.getString(3));
        return null;
    }

    static public ResultSet getUserBooks(int idno) throws SQLException, ClassNotFoundException {
        connect();
        String query = "SELECT book.bname,book.duedate FROM student INNER JOIN book ON student.idno=book.bookissue WHERE idno=?";
        pst = con.prepareStatement(query);
        pst.setInt(1, idno);
        return pst.executeQuery();// This ResultSet can be used to extract data like bookname, duedate.
        // If null, then user has no books due, null can be checked using boolean rs.next()
    }

    static public int addBookToDB(Book b) throws SQLException, ClassNotFoundException {
        connect();
        String query = "INSERT INTO book(bname,bauth,isbn,bgenre,publish) VALUES(?,?,?,?,?)";
        String bname = b.getName();
        String isbn = b.getIsbn();
        String bauth = b.getAuthor();
        String bgenre = b.getGenre();
        String publish = b.getPublisher();
        pst = con.prepareStatement(query);
        pst.setString(1,bname);
        pst.setString(2,bauth);
        pst.setString(3,isbn);
        pst.setString(4,bgenre);
        pst.setString(5, publish);
        try{
            return pst.executeUpdate();//If this is 0, no updates made, else update successful
        }catch (SQLIntegrityConstraintViolationException e){
            return 0;
        }
    }

    static public int deleteBookFromDB(String isbn) throws SQLException, ClassNotFoundException {
        connect();
        String query = "DELETE FROM book WHERE isbn=?";
        pst = con.prepareStatement(query);
        pst.setString(1,isbn);
        try{
            return pst.executeUpdate();//If this is 0, no updates made, else book delete successful
        }catch (SQLIntegrityConstraintViolationException e){
            return 0;
        }
    }

    static public int issueBookDB(int idno, String bookname) {
        BookIssuer r = new BookIssuer(idno,bookname);
        return r.ret;
    }

    static public HashMap<Double,Double> returnBookDB(int idno, String bookname) throws SQLException, ClassNotFoundException {
        connect();
        HashMap<Double,Double> hs = new HashMap<>();
        double dues = dueBookDB(idno, bookname);
        String query = "UPDATE book set bookissue=1,duedate=NULL where bname=? AND bookissue=?";
        pst = con.prepareStatement(query);
        pst.setString(1,bookname);
        pst.setInt(2,idno);
        try {
            hs.put((double) pst.executeUpdate(),dues);
            return hs;
        }catch (SQLIntegrityConstraintViolationException e){
            return null;
        }
    }

    static public HashSet<Book> bookDetailsByName(String bookname) throws SQLException, ClassNotFoundException {
        ResultSet rs = getAvailableBooks();
        HashSet<Book> hbs = new HashSet<>();
        while (rs.next()) {
            Book b = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            String str = b.getName().toUpperCase();
            if (str.contains(bookname.toUpperCase())) {
                hbs.add(b);
            }
        }
        return hbs;
    }

    static public ResultSet bookDetailsByISBN(String isbn) throws SQLException, ClassNotFoundException {
        connect();
        String query = "SELECT bname,bauth,isbn,bgenre,publish FROM book WHERE isbn=?";
        pst = con.prepareStatement(query);
        pst.setString(1,isbn);
        try {
            ResultSet rs =  pst.executeQuery();
            rs.next();
            return rs;
        }catch (Exception ignored){ return null;}
    }

    static public HashSet<Book> bookDetailsByAuth(String authorname) throws SQLException, ClassNotFoundException {
        ResultSet rs = getAvailableBooks();
        HashSet<Book> hbs = new HashSet<>();
        while (rs.next()) {
            Book b = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            String str = b.getAuthor().toUpperCase();
            if (str.contains(authorname.toUpperCase())) {
                hbs.add(b);
            }
        }
        return hbs;
    }

    static public ResultSet getAvailableBooks() throws SQLException, ClassNotFoundException {
        connect();
        String query = "SELECT bname,bauth,isbn,bgenre,publish FROM book WHERE bookissue=1";
        pst = con.prepareStatement(query);
        return pst.executeQuery();
    }

    static public ResultSet getAllBooks() throws SQLException, ClassNotFoundException {
        connect();
        String query = "SELECT bname,bauth,isbn,bgenre,publish FROM book";
        pst = con.prepareStatement(query);
        return pst.executeQuery();
    }

    static public double dueTotal(Student std) throws SQLException, ClassNotFoundException {
        connect();
        int idno = getUserId(std.getID());
        double dues = getUserDues(idno);
        ResultSet rs = getUserBooks(idno);
        if(rs.isBeforeFirst()) {
            while (rs.next()){
                dues += dueBookDB(idno, rs.getString(1));
            }

        }

        return dues;
    }

    private static double getUserDues(int idno) throws SQLException, ClassNotFoundException {
        connect();
        String query = "SELECT dues FROM student WHERE idno=?";
        pst = con.prepareStatement(query);
        pst.setInt(1,idno);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getDouble(1);
    }

    static public double dueBookDB(int idno, String bookname) throws SQLException, ClassNotFoundException {
        connect();
        String query = "SELECT duedate FROM book WHERE bname=? AND bookissue=?";
        double dues = 0.;
        pst = con.prepareStatement(query);
        pst.setString(1, bookname);
        pst.setInt(2, idno);
        ResultSet rs = pst.executeQuery();
        rs.next();
        java.sql.Date sqlDate = rs.getDate(1);
        LocalDate ld = sqlDate.toLocalDate();
        LocalDate cd = LocalDate.now();
        int d = cd.compareTo(ld);
        if(d>0) {
            Period period = Period.between(ld, cd);
            d = period.getDays();
            dues += d * User.fine;
        }
        return dues;
    }
    public void closeCon() throws SQLException {
        con.close();
        try {
            pst.close();
        }catch (NullPointerException ignored){  }
    }

    static public int getUserId(String id) throws SQLException, ClassNotFoundException {
        connect();
        String query = "SELECT idno FROM student WHERE sid=?";
        pst = con.prepareStatement(query);
        pst.setString(1, id);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            return rs.getInt(1);
        }
        else return 0;
    }

    static public void reviewstudentDB() throws SQLException, ClassNotFoundException {
        connect();
        String query ="SELECT sname,sid,regdate FROM student";
        pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        int i = 0;
        while(rs.next()) System.out.println((++i)+":Name: "+rs.getString(1)+"\nID: "+rs.getString(2)+
                "\nRegistration Date: "+rs.getDate(3)+"\n");
    }

    static public void reviewbookDB() throws SQLException, ClassNotFoundException{
        connect();
        String query ="SELECT bname,bauth,isbn,bgenre,publish FROM book";
        pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        int i = 0;
        while(rs.next()) System.out.println((++i)+":Title: "+rs.getString(1)+"\nAuthor: "+rs.getString(2)+
                "\nISBN: "+rs.getString(3)+"\nGenre: "+rs.getString(4)+"\nPublisher"+rs.getString(5)+"\n");
    }

}
//class Test{
//    public static void main(String[] args) throws Exception {
//        Database_DAO dao = new Database_DAO();
//        dao.connect();
////        Student std = new Student("aryaman","FYYY","12345");//Add student completed
////                                                            // with all possible case checks
////        System.out.println(dao.addStudentToDB(std));
////        System.out.println(dao.deleteStudentFromDB("FYYY"));//Deletion working passed
////        System.out.println(dao.issueBook(3,"Mein Kamf"));//Issue working perfectly
////        ResultSet rs = dao.getUserBooks(3);
////        int i = 1;
////        while(rs.next()){
////            System.out.println(i+":"+rs.getString(1));
////            System.out.println("Due Date:"+rs.getDate(2));
////            i++;
////        }
////        System.out.println(rs.isAfterLast());//If this is true, then we had a successful list,
////                                            // else no books due
////        Book book = new Book("Hey","test","aloo","","");//Book addition test passed
////        System.out.println(dao.addBook(book));
////        System.out.println(dao.deleteBook("aloo"));//Book deletion test passed
////        System.out.println(dao.returnBook(3,"Mein Kampf"));//Return book class passed
////        ResultSet rs = dao.bookDetails("Mein Kampf");//Get details working passed
////        System.out.println(rs.isBeforeFirst());//Important to differentiate b/w empty and full ResulSet
////        rs.next();
////        System.out.println("Name: "+rs.getString(1)+"\nAuthor: "+rs.getString(2)+"\nISBN: "+rs.getString(3)+"\nGenre: "+
////        rs.getString(4)+"\nPublisher: "+rs.getString(5)+" ");
////        System.out.println(dao.dueTotal(new Student("Aryaman Chauhan","F2020B5A72006P","12345")));//Due calculation passed
////        dao.returnBookDB(3,"Digital Design");
//        dao.closeCon();
//    }
//}