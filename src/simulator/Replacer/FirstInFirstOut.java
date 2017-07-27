package simulator.Replacer;

import simulator.Address.LogicalAddress;

import java.util.ArrayList;

public class FirstInFirstOut extends Replacer {

    /**
     * Next frame to be replaced.
     */
    private int nextVictim = 0;

    /**
     * Initializa new FIFO instance.
     *
     * @param framesNumber Number of frames.
     */
    public FirstInFirstOut (int framesNumber, ArrayList<LogicalAddress> accessList) {
        super(framesNumber, accessList);
    }

    /**
     * Execute the memory access.
     */
    @Override
    public void run () {
        for (LogicalAddress address : this.accessList) {
            if (!isAddressPresent(address.getPageNumber())) {
                frames[nextVictim] = address.getPageNumber();
                nextVictim = (nextVictim + 1) % this.frames.length;
                pageFaultCount++;
                printFaultPage(address.getPageNumber(), nextVictim);
                printFrames();
            }
        }
    }
}
