import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

public class ReissuerBook implements Runnable{
    static String name;


    public int ret;

    public ReissuerBook(String tname){
        name = tname;
        Thread t = new Thread(this);
        t.start();
    }
    @Override
    public void run() {
        ret = reissueBook();
    }
    synchronized static int reissueBook(){
        try{
            Database_DAO.connect();
            String query = "UPDATE book SET issueno=2,duedate=adddate(current_date(),15) WHERE bname=? AND issueno=1";
            Database_DAO.pst = Database_DAO.con.prepareStatement(query);
            Database_DAO.pst.setString(1,name);
            return Database_DAO.pst.executeUpdate();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
    }

}