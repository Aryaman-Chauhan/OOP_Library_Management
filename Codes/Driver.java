import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Driver {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        Database_DAO dao = new Database_DAO();
        while (true) {
            System.out.println("\n\nEnter 1 to sign in as user, 2 to sign in as admin," +
                    "\n 3 to register, else to log off");
            int a;
            try {
                a = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Incorrect option entered");
                return;
            }
            if (a != 1 && a != 2 && a != 3) {
                System.out.println("Logging off");
                dao.closeCon();
                return;
            }

            if (a == 3) {
                String name, id, pass;
                System.out.print("Enter name: ");
                name = sc.nextLine();
                System.out.print("Enter ID: ");
                id = sc.nextLine();
                System.out.print("Enter password: ");
                pass = sc.nextLine();
                Database_DAO.addStudentToDB(new Student(name, id, pass));
                Student s = (Student) Database_DAO.signIn(id, pass);
                int y = 1;
                while (y != 0)
                    y = displayUserOptions(s);
                if (y == 0) continue;
            }

            System.out.print("Enter user id: ");
            String user_id = sc.nextLine();
            System.out.print("Enter password: ");
            String user_pass = sc.nextLine();
            if (a == 1) {

                Student s = (Student) dao.signIn(user_id, user_pass);
                if (s == null) {
                    System.out.println("Incorrect User id or password");
                    return;
                } else {
                    int y = 1;
                    while (y != 0)
                        y = displayUserOptions(s);
                }
            } else if (a == 2){

                Librarian l = (Librarian) Database_DAO.signIn(user_id, user_pass);
                if (l == null) {
                    System.out.println("Incorrect admin details");
                    return;
                } else {
                    int y = 1;
                    while (y != 0)
                        y = displayAdminOptions(l);
                }
            }
        }
    }

    private static int displayAdminOptions(Librarian l) {
        Database_DAO dao = new Database_DAO();
        Scanner sc = new Scanner(System.in);
        System.out.println("\n1 to add book");
        System.out.println("2 to view all books in library");
        System.out.println("3 to view books not borrowed");
        System.out.println("4 to delete book");
        System.out.println("5 to view all students");
        System.out.println("6 to change fine");
        System.out.println("7 to sign out");
        int a;
        try{
            a = Integer.parseInt(sc.nextLine());
        }catch(Exception e) {
            a = 0;
        }
        int y = 1;

        switch(a) {
            case 1:
                String title, author, isbn, genre, publish;
                System.out.print("Enter title: ");
                title = sc.nextLine();
                System.out.print("Enter author: ");
                author = sc.nextLine();
                System.out.print("Enter isbn: ");
                isbn = sc.nextLine();
                System.out.print("Enter genre: ");
                genre = sc.nextLine();
                System.out.print("Enter publisher: ");
                publish = sc.nextLine();
                try {
                    l.addBook(new Book(title, author, isbn, genre, publish));
                } catch(Exception e) {

                }
                break;

            case 2:
                try {
                    l.reviewBook();
                } catch (Exception e) {
                    System.out.println("Couldn't complete operation");
                }
                break;

            case 3:
                try {
                    ResultSet rs = dao.getAvailableBooks();
                    int i = 0;
                    while(rs.next()) System.out.println((++i)+":Title: "+rs.getString(1)+"\nAuthor: "+rs.getString(2)+
                            "\nISBN: "+rs.getString(3)+"\nGenre: "+rs.getString(4)+"\nPublisher"+rs.getString(5)+"\n");
                } catch(Exception e) {
                    System.out.println("Couldn't complete operation");
                }
                break;

            case 4:
                try {
                    System.out.println("Enter ISBN to delete: ");
                    isbn = sc.nextLine();
                    l.removeBook(isbn);
                }catch (Exception e) {
                    System.out.println("Couldn't complete operation");
                }
                break;

            case 5:
                try{
                    l.reviewUser();
                }catch(Exception e) {
                    System.out.println("Couldn't complete operation");
                }
                break;

            case 6:
                System.out.print("Enter new fine: ");
                l.setFine(Double.parseDouble(sc.nextLine()));
                break;

            case 7:
                y = 0;
                break;

            default:
                System.out.println("Incorrect operation entered");
                y = 0;
                break;
        }
        return y;
    }

    private static int displayUserOptions(Student s) {
        Database_DAO dao = new Database_DAO();
        Scanner sc = new Scanner(System.in);
        System.out.println("\n1 to search book");
        System.out.println("2 to borrow book");
        System.out.println("3 to view current books");
        System.out.println("4 to return book");
        System.out.println("5 to re-issue book");
        System.out.println("6 to view available books");
        System.out.println("7 to get dues");
        System.out.println("8 to sign out");
        int a;
        try{
            a = Integer.parseInt(sc.nextLine());
        }catch(Exception e) {
            a = 0;
        }
        int y = 1;

        switch (a) {
            case 1:
                System.out.println("1 to search by name\n2 to search by ISBN\n3 to search by author");
                int t = Integer.parseInt(sc.nextLine());
                if (t == 1) {
                    System.out.print("Enter name: ");
                    try{
                        System.out.println(searchByName.search(sc.nextLine(), dao));
                    }catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                else if (t == 2) {
                    System.out.print("Enter ISBN: ");
                    try {
                        searchByISBN.search(sc.nextLine(), dao);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                else if (t == 3) {
                    System.out.print("Enter author: ");
                    try {
                        System.out.println(searchByAuthor.search(sc.nextLine(), dao));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                else {
                    System.out.println("Incorrect option entered");
                }
                break;

            case 2:
                System.out.print("Enter book name: ");
                try {
                    s.borrowBook(sc.nextLine());
                }catch(Exception e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 3:
                try {
                    int id = dao.getUserId(s.getID());
                    ResultSet rs = dao.getUserBooks(id);
                    if(rs.isBeforeFirst()){
                        while(rs.next()) {
                            System.out.println("\nName: " + rs.getString(1) + "\nDue Date: " + rs.getDate(2));
                        }}
                    else System.out.println("No books borrowed");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 4:
                System.out.print("Enter book name to return: ");
                try {
                    s.returnBook(sc.nextLine());
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 5:
                System.out.print("Enter book name to reissue: ");
                try {
                    s.reissueBook(sc.nextLine());
                }catch (Exception e) {

                }
                break;

            case 6:
                try {
                    ResultSet rs = dao.getAvailableBooks();
                    int i = 0;
                    while(rs.next()) System.out.println((++i)+":Title: "+rs.getString(1)+"\nAuthor: "+rs.getString(2)+
                            "\nISBN: "+rs.getString(3)+"\nGenre: "+rs.getString(4)+"\nPublisher"+rs.getString(5)+"\n");
                } catch(Exception e) {
                    System.out.println("Couldn't complete operation");
                }
                break;

            case 7:
                try{
                    System.out.println("Dues: " + dao.dueTotal(s));
                }catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case 8:
                y = 0;
                break;

            default:
                System.out.println("Incorrect option entered");
                y = 0;
        }
        return y;
    }
}
