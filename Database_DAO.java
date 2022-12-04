import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.HashSet;

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
        String query = "UPDATE book SET bookissue=1,duedate=NULL,issueno=0";
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

    static public User signIn(String id, String password) throws SQLException, ClassNotFoundException {
        connect();
        String query = "SELECT sname,sid,pwd FROM student WHERE sid=? and pwd=?";
        pst = con.prepareStatement(query);
        pst.setString(1, id.toUpperCase());
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();// This ResultSet can be used to extract data like idno, pwd, etc.
        // If null, then wrong info, null can be checked using boolean rs.next()
        if(!(id.equalsIgnoreCase("ADMIN"))) {
            if (rs.next()) return new Student(rs.getString(1), rs.getString(2), rs.getString(3));
            return null;
        }else if(rs.next()) return new Librarian();
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

    static public int issueBookDB(int idno, String bookname) throws InterruptedException {
        BookIssuer r = new BookIssuer(idno,bookname);
        Thread.sleep(100);
        return r.ret;
    }

    static public int reissueBookDB(String name) throws InterruptedException {
        ReissuerBook r = new ReissuerBook(name);
        Thread.sleep(100);
        return r.ret;
    }

    static public Date getBookDueDate(String name) throws SQLException, ClassNotFoundException {
        connect();
        String query = "SELECT duedate FROM book WHERE bname=?";
        pst = con.prepareStatement(query);
        pst.setString(1,name);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getDate(1);
    }
    static public HashMap<Double,Double> returnBookDB(int idno, String bookname) throws SQLException, ClassNotFoundException {
        connect();
        HashMap<Double,Double> hs = new HashMap<>();
        double dues = dueBookDB(idno, bookname);
        double odue = getOldDues(idno);
        odue += dues;
        addNewDues(odue, idno);
        String query = "UPDATE book set bookissue=1,duedate=NULL,issueno=0 where bname=? AND bookissue=?";
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

    private static void addNewDues(double odue, int idno) throws SQLException, ClassNotFoundException {
        connect();
        String query = "UPDATE student SET dues=? WHERE idno=?";
        pst = con.prepareStatement(query);
        pst.setDouble(1,odue);
        pst.setInt(2,idno);
        pst.executeUpdate();
    }

    private static double getOldDues(int idno) throws SQLException, ClassNotFoundException {
        connect();
        String query = "SELECT dues FROM student where idno=?";
        pst = con.prepareStatement(query);
        pst.setInt(1,idno);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getDouble(1);
    }

    public static Student getStudentFromId(int idno) throws SQLException, ClassNotFoundException {
        connect();
        String query = "SELECT sname,sid,pwd FROM student where idno=?";
        pst = con.prepareStatement(query);
        pst.setInt(1,idno);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return new Student(rs.getString(1), rs.getString(2), rs.getString(3));
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
        double dues = getOldDues(idno);
        ResultSet rs = getUserBooks(idno);
        if(rs.isBeforeFirst()) {
            while (rs.next()){
                dues += dueBookDB(idno, rs.getString(1));
            }

        }
        addNewDues(dues,idno);
        return dues;
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
        try {
            con.close();
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
        rs.next();
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