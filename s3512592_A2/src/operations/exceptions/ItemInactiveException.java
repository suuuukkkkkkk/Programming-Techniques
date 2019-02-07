package operations.exceptions;

public class ItemInactiveException extends Exception {
    public ItemInactiveException() {
        super("*=>Error: The holding is currently inactive!");
    }
}
