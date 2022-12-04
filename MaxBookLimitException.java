public class MaxBookLimitException extends Exception{ //Exception definition if a user has borrowed 3 books and tries to borrow more
    public MaxBookLimitException(){
        super("Max book checkout limit reached! Should not exceed 3");
    }
}
