package simulator.Exceptions;

public class InvalidPageLengthSizeException extends Exception {
    public InvalidPageLengthSizeException () {
        super("Invalid page length size. Provide a number between 0 and 31.");
    }
}
