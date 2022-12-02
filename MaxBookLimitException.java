public class MaxBookLimitException extends Exception{
    public MaxBookLimitException(){
        super("Max book checkout should not exceed 3");
    }
}
