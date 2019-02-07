package operations.exceptions;

public class InsufficientCreditException extends Exception {
    public InsufficientCreditException() {
        super("*=>Error: Insufficient credit of the account!");
    }
}
