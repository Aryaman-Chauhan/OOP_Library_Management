public abstract class User {
    protected String id;
    protected String password;
    protected static double fine = 1.0;

    public String getID(){ //Gets the id of the user
        return this.id;
    }

    public String getPassword(){ //Gets the password of the user
        return this.password;
    }

    public void setPassword(String password) { //Sets the password of the user to the passed argument
        this.password = password;
    }
}
