package simulator.Replacer;

import simulator.Address.LogicalAddress;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Replacer {
    /**
     * The memory access list.
     */
    ArrayList<LogicalAddress> accessList;

    /**
     * Each frame position have in itself the page that it belong.
     * This way, we don't need to manage valid/invalid bits inside the pages table.
     */
    int[] frames;

    /**
     * Number of page faults.
     */
    int pageFaultCount = 0;

    Replacer (int framesNumber, ArrayList<LogicalAddress> accessList) {
        this.frames = new int[framesNumber];
        this.accessList = accessList;
        Arrays.fill(this.frames, -1);
    }

    /**
     * Verify if the address is allocated.
     *
     * @param page The page identifier.
     *
     * @return Present/Not present
     */
    boolean isAddressPresent (int page) {
        for (int frame : this.frames) {
            if (page == frame) {
                return true;
            }
        }
        return false;
    }

    void print () {
        System.out.print("{");
        for (int i = 0; i < frames.length; i++) {
            System.out.print(frames[i] == -1 ? "Ø" : frames[i]);
            if (i < frames.length - 1) {
                System.out.print(",");
            }
        }
        System.out.println("}");
        System.out.println();
    }

    /**
     * Try to access a determined address.
     */
    public abstract void run ();
}
