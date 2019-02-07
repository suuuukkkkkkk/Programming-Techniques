package operations.exceptions;

public class OnLoanException extends Exception {
    public OnLoanException() {
        super("*=>Error: The current holding is on loan!");
    }
}
