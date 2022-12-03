public abstract class User {
    protected String id;
    protected String password;
    protected double fine = 1.0;

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
