package simulator;

import simulator.Exceptions.InvalidArgumentsException;
import simulator.Exceptions.InvalidFramesNumberException;
import simulator.Exceptions.InvalidPageLengthSizeException;
import simulator.Replacer.FirstInFirstOut;
import simulator.Replacer.Optimal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    private static String fileLocation;

    private static final int OPTIMAL_METHOD = 1;

    private static final int FIFO_METHOD = 2;

    private static final int ALL_METHODS = 3;

    private static int frames;

    private static int pageSize = 12;

    private static int selectedMethod;

    private static ArrayList<String> accessList;

    public static void main (String[] args) {
        try {
            readArgs(args);
            readFile(fileLocation);
        } catch (InvalidArgumentsException | InvalidPageLengthSizeException | IOException | InvalidFramesNumberException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        StrategyReplacer replacer;
        switch (selectedMethod) {
            case ALL_METHODS:
                replacer = new StrategyReplacer(null);
                break;
            case FIFO_METHOD:
                replacer = new StrategyReplacer(new FirstInFirstOut(accessList, pageSize, frames));
                break;
            case OPTIMAL_METHOD:
                replacer = new StrategyReplacer(new Optimal(accessList, pageSize, frames));
                break;
            default:
                replacer = new StrategyReplacer(null);
                break;
        }

        replacer.run();
    }

    private static void readArgs (String[] arguments) throws InvalidArgumentsException, InvalidPageLengthSizeException, InvalidFramesNumberException {
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
                pageSize = Integer.parseInt(arguments[3]);
            } catch (NumberFormatException e) {
                throw new InvalidPageLengthSizeException();
            }
            if (pageSize < 0 || pageSize > 31) {
                throw new InvalidPageLengthSizeException();
            }
        }
    }

    private static void readFile (String location) throws IOException {
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
