public class ReissuerBook implements Runnable{
    static String name;


    public static int ret;

    public ReissuerBook(String tname){
        name = tname;
        Thread t = new Thread(this);
        t.start();
    }
    @Override
    public void run() {
        reissueBook();
    }
    synchronized static void reissueBook(){
        try{
            Database_DAO.connect();
            String query = "UPDATE book SET issueno=2,duedate=adddate(current_date(),15) WHERE bname=? AND issueno=1";
            Database_DAO.pst = Database_DAO.con.prepareStatement(query);
            Database_DAO.pst.setString(1,name);
            ret = Database_DAO.pst.executeUpdate();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            ret = 0;
        }
    }

}