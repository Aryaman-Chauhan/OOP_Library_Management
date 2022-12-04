public class MaxBookLimitException extends Exception{
    public MaxBookLimitException(){
        super("Max book checkout limit reached! Should not exceed 3");
    }
}
