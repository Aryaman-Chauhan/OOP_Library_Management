import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookIssuer implements Runnable{
    static String bookname;
    static int idNo;

    public static int ret;

    public BookIssuer(int id, String name){
        bookname = name;
        idNo = id;
        Thread t = new Thread(this);
        t.start();
    }
    @Override
    public void run() {
        issueBook();
    }
    synchronized static void issueBook(){
        try{
            Database_DAO.connect();
            String q = "SELECT COUNT(book.bname) FROM book WHERE bookissue=? AND issueno=0";
            PreparedStatement qst = Database_DAO.con.prepareStatement(q);
            qst.setInt(1, idNo);
            ResultSet rs1 = qst.executeQuery();
            rs1.next();
            int t = rs1.getInt(1);
            if(t >= 3) ret = -1;
            String query = "UPDATE book SET bookissue=?,duedate=adddate(current_date(),15),issueno=1 WHERE bname=? and bookissue=1";

            Database_DAO.pst = Database_DAO.con.prepareStatement(query);
            Database_DAO.pst.setInt(1,idNo);
            Database_DAO.pst.setString(2,bookname);
            ret = Database_DAO.pst.executeUpdate();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            ret = 0;
        }
    }

}