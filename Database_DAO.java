import java.sql.*;
import java.time.LocalDate;
import java.time.Period;

public class Database_DAO {
    Connection con = null;
    PreparedStatement pst = null;

    public void connect() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/bits_library";
        String uname = "root";
        String pwd = "12345678";
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url,uname,pwd);
    }

    public int addStudentToDB(Student s) throws SQLException {
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

    public int deleteStudentFromDB(String sid) throws SQLException {
        String query = "DELETE FROM student WHERE sid=?";
        pst = con.prepareStatement(query);
        pst.setString(1,sid);
        try{
            return pst.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            return 0;
        }
    }

    public ResultSet signIn(User u) throws SQLException {
        String sid = u.id;
        String pass = u.password;
        String query = "SELECT idno,sname FROM student WHERE sid=? and pwd=?";
        pst = con.prepareStatement(query);
        pst.setString(1,sid);
        pst.setString(2,pass);
        return pst.executeQuery();// This ResultSet can be used to extract data like idno, pwd, etc.
        // If null, then wrong info, null can be checked using boolean rs.next()
    }

    public ResultSet getUserBooks(int idno) throws SQLException {
        String query = "SELECT book.bname,book.duedate FROM student INNER JOIN book ON student.idno=book.bookissue WHERE idno=?";
        pst = con.prepareStatement(query);
        pst.setInt(1, idno);
        return pst.executeQuery();// This ResultSet can be used to extract data like bookname, duedate.
        // If null, then user has no books due, null can be checked using boolean rs.next()
    }

    public int addBook(Book b) throws SQLException {
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

    public int deleteBook(String isbn) throws SQLException {
        String query = "DELETE FROM book WHERE isbn=?";
        pst = con.prepareStatement(query);
        pst.setString(1,isbn);
        try{
            return pst.executeUpdate();//If this is 0, no updates made, else book delete successful
        }catch (SQLIntegrityConstraintViolationException e){
            return 0;
        }
    }

    public int issueBookDB(int idno, String bookname) throws SQLException {
        String query = "UPDATE book SET bookissue=?,duedate=adddate(current_date(),15) WHERE bname=?";
        pst = con.prepareStatement(query);
        pst.setInt(1,idno);
        pst.setString(2,bookname);
        try {
            return pst.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int returnBookDB(int idno, String bookname) throws SQLException {
        String query = "UPDATE book set bookissue=1,duedate=NULL where bname=? AND bookissue=?";
        pst = con.prepareStatement(query);
        pst.setString(1,bookname);
        pst.setInt(2,idno);
        try {
            return pst.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            return 0;
        }
    }

    public ResultSet bookDetails(String bookname) throws SQLException {
        String query = "SELECT bname,bauth,isbn,bgenre,publish FROM book WHERE bname=?";
        pst = con.prepareStatement(query);
        pst.setString(1,bookname);
        try {
            return pst.executeQuery();
        }catch (Exception ignored){ return null;}
    }

    public ResultSet getAvailableBooks() throws SQLException {
        ResultSet rs = this.getUserBooks(1);
        if(rs.isBeforeFirst()) return rs;
        else return this.getAllBooks();
    }

    public ResultSet getAllBooks() throws SQLException {
        String query = "SELECT bname,bauth,isbn,bgenre,publish FROM book";
        pst = con.prepareStatement(query);
        return pst.executeQuery();
    }

    public double dueTotal(Student std)throws SQLException{
        ResultSet rs = this.signIn(std);
        double dues = 0.;
        int idno;
        if(rs.isBeforeFirst()) {
            rs.next();
            idno = rs.getInt(1);
            ResultSet bookset = this.getUserBooks(idno);
            while(bookset.next()){
                java.sql.Date sqlDate = bookset.getDate(2);
                LocalDate ld = sqlDate.toLocalDate();
                LocalDate cd = LocalDate.now();
                int d = cd.compareTo(ld);
                if(d>0) {
                    Period period = Period.between(ld,cd);
                    d = period.getDays();
                    dues += d * std.fine;
                }
            }
        }
        return dues;
    }
    public void closeCon() throws SQLException {
        con.close();
        try {
            pst.close();
        }catch (NullPointerException ignored){  }
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