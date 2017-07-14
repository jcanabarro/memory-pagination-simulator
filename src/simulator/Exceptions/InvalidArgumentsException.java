package simulator.Exceptions;

public class InvalidArgumentsException extends Exception {
    public InvalidArgumentsException () {
        super("Invalid arguments.\nUsage:\njava -jar program.jar [file] [method|all] [frames] [size]\nWhere:\nfile: the trace file location\nmethod: fifo or optimal page substitution policy (or all to run both)\nframes: number of physical memory frames\nsize (optional): a number between 0~31 bits for offset (will determine the page size).");
    }
}
