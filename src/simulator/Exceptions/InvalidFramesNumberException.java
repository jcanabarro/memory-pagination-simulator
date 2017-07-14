package simulator.Exceptions;

public class InvalidFramesNumberException extends Exception {
    public InvalidFramesNumberException () {
        super("Invalid frames number. Provide a number higher than zero.");
    }
}
