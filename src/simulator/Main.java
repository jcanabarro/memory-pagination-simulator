package simulator;

import simulator.Exceptions.InvalidArgumentsException;
import simulator.Exceptions.InvalidFramesNumberException;
import simulator.Exceptions.InvalidFrameSizeException;
import simulator.Replacer.FirstInFirstOut;
import simulator.Replacer.Optimal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    /**
     * Physical localization of input traces.
     */
    private static String fileLocation;

    /**
     * Optimal method identifier.
     */
    private static final int OPTIMAL_METHOD = 1;

    /**
     * First In, First Out method identifier.
     */
    private static final int FIFO_METHOD = 2;

    /**
     * All methods identifier.
     */
    private static final int ALL_METHODS = 3;

    /**
     * Total number of frames.
     */
    private static int frames;

    /**
     * Bits that address each offset inside frame (2^frameSize = frame positions, 2^(32-frameSize) = number of addressable pages).
     */
    private static int frameSize = 12;

    /**
     * The selected simulation method.
     */
    private static int selectedMethod;

    /**
     * The input access list.
     */
    private static ArrayList<String> accessList;

    /**
     * The simulator entry point.
     * @param args The command line arguments.
     */
    public static void main (String[] args) {
        try {
            readArgs(args);
            readFile(fileLocation);
        } catch (InvalidArgumentsException | InvalidFrameSizeException | IOException | InvalidFramesNumberException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        MemoryDispatcher replacer;
        switch (selectedMethod) {
            case ALL_METHODS:
                replacer = new MemoryDispatcher(accessList, null, frameSize);
                break;
            case FIFO_METHOD:
                replacer = new MemoryDispatcher(accessList, new FirstInFirstOut(frames), frameSize);
                break;
            case OPTIMAL_METHOD:
                replacer = new MemoryDispatcher(accessList, new Optimal(frames), frameSize);
                break;
            default:
                replacer = new MemoryDispatcher(accessList, null, frameSize);
                break;
        }

        replacer.run();
    }

    /**
     * The arguments reader.
     * @param arguments Array of command line arguments.
     * @throws InvalidArgumentsException If arguments are invalid (in size or options).
     * @throws InvalidFrameSizeException If frame size isn't a number between 0 and 31.
     * @throws InvalidFramesNumberException If negative or invalid number of frames.
     */
    private static void readArgs (String[] arguments) throws InvalidArgumentsException, InvalidFrameSizeException, InvalidFramesNumberException {
        if (arguments.length < 3) {
            throw new InvalidArgumentsException();
        }

        fileLocation = arguments[0];

        if (Objects.equals(arguments[1], "optimal")) {
            selectedMethod = 1;
        } else if (Objects.equals(arguments[1], "fifo")) {
            selectedMethod = 2;
        } else if (Objects.equals(arguments[1], "all")) {
            selectedMethod = 3;
        } else {
            throw new InvalidArgumentsException();
        }

        try {
            frames = Integer.parseInt(arguments[2]);
        } catch (NumberFormatException e) {
            throw new InvalidFramesNumberException();
        }
        if (frames <= 0) {
            throw new InvalidFramesNumberException();
        }

        if (arguments.length > 3) {
            try {
                frameSize = Integer.parseInt(arguments[3]);
            } catch (NumberFormatException e) {
                throw new InvalidFrameSizeException();
            }
            if (frameSize < 0 || frameSize > 31) {
                throw new InvalidFrameSizeException();
            }
        }
    }

    /**
     * Reads the input file and allocate it's contents to dispatch to the memory manager.
     * @param location Physical file location.
     * @throws IOException If cannot open the file.
     * @throws NumberFormatException If input lines isn't base16 strings.
     */
    private static void readFile (String location) throws IOException, NumberFormatException {
        accessList = new ArrayList<>();
        String raw;
        FileReader fr = new FileReader(location);
        BufferedReader bf = new BufferedReader(fr);
        while ((raw = bf.readLine()) != null) {
            long value = Long.parseLong(raw, 16);
            String binVal = Long.toBinaryString(value);
            while (binVal.length() < 32) {
                binVal = "0" + binVal;
            }
            accessList.add(binVal);
        }
        bf.close();
        fr.close();
    }
}
