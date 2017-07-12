package simulator;

import simulator.Exceptions.InvalidArgumentsException;
import simulator.Exceptions.InvalidPageLengthSizeException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    private static String fileLocation;

    private static final String OPTIMAL_METHOD = "optimal";

    private static final String FIFO_METHOD = "fifo";

    private static final String ALL_METHODS = "all";

    private static int pageSizeInBytes = 4096;

    private static int selectedMethod; // 1 => OPTIMAL, 2 => FIFO, 2 => ALL

    public static void main (String[] args) {
        try {
            readArgs(args);
            readFile(fileLocation);
        } catch (InvalidArgumentsException | InvalidPageLengthSizeException | IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void readArgs (String[] arguments) throws InvalidArgumentsException, InvalidPageLengthSizeException {
        if (arguments.length < 2) {
            throw new InvalidArgumentsException();
        }

        fileLocation = arguments[0];

        if (Objects.equals(arguments[1], OPTIMAL_METHOD)) {
            selectedMethod = 1;
        } else if (Objects.equals(arguments[1], FIFO_METHOD)) {
            selectedMethod = 2;
        } else if (Objects.equals(arguments[1], ALL_METHODS)) {
            selectedMethod = 3;
        } else {
            throw new InvalidArgumentsException();
        }

        if (arguments.length > 2) {
            int bits;
            try {
                bits = Integer.parseInt(arguments[2]);
            } catch (NumberFormatException e) {
                throw new InvalidPageLengthSizeException();
            }
            if (bits < 0 || bits > 31) {
                throw new InvalidPageLengthSizeException();
            } else {
                pageSizeInBytes = (int) Math.pow((double) bits, (double) 2);
            }
        }
    }

    private static void readFile (String location) throws IOException {
        ArrayList<String> accessList = new ArrayList<String>();
        String raw;
        BufferedReader bf = new BufferedReader(new FileReader(location));
        while ((raw = bf.readLine()) != null) {
            accessList.add(new BigInteger(raw, 16).toString(2));
        }
        for (String anAccessList : accessList) {
            System.out.println(anAccessList);
        }
    }
}
