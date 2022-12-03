public class User {
    protected String id;
    protected String password;

    public String getID(){
        return this.id;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
