import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
    	Database_DAO dao = new Database_DAO();
    	dao.resetDBToLib();
//    	dao.addStudentToDB(new Student("Radhey Kanade", "F20212534", "12345"));
    }
}
