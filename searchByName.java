import java.sql.SQLException;
import java.util.HashSet;

public interface searchByName { //Searches for a book by it's name
    static HashSet<Book> search(String name, Database_DAO dao) throws SQLException, ClassNotFoundException {
        return dao.bookDetailsByName(name);
    }
}
