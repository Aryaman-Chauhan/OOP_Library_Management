import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public interface searchByName {
    static HashSet<Book> search(String name, Database_DAO dao) throws SQLException, ClassNotFoundException {
        return dao.bookDetailsByName(name);
    }
}
