package simulator.Exceptions;

public class InvalidFrameSizeException extends Exception {
    public InvalidFrameSizeException () {
        super("Invalid frame size. Provide a number between 0 and 31.");
    }
}
