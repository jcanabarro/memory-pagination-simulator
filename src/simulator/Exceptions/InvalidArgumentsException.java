package simulator.Exceptions;

public class InvalidArgumentsException extends Exception {
    public InvalidArgumentsException () {
        super("Invalid arguments.\nUsage:\njava -jar program.jar [file] [method|all] [size]\nWhere:\nfile: the trace file location\nmethod: fifo or optimal page substitution policy (or all to run both)\nsize: a number between 0~31 bits of displacement (will determine the page size).");
    }
}
