import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public interface searchByISBN {
    static Book search(String isbn, Database_DAO dao) throws SQLException, ClassNotFoundException {
        Book retBook = null;
        ResultSet rs = dao.bookDetailsByISBN(isbn);
        if(rs.isFirst())
            retBook = new Book(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));

        return retBook;
    }
}
