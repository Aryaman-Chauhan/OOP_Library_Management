import java.sql.SQLException;
import java.util.HashSet;

public interface searchByAuthor { //Searches for a book by it's author
    static HashSet<Book> search(String authorname, Database_DAO dao) throws SQLException, ClassNotFoundException {
        return dao.bookDetailsByAuth(authorname);
    }
}
