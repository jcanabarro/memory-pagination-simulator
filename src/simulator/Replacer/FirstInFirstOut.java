package simulator.Replacer;

import simulator.Address.LogicalAddress;

import java.text.DecimalFormat;
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
        long startTime = System.nanoTime();
        for (LogicalAddress address : this.accessList) {
            if (!isAddressPresent(address.getPageNumber())) {
                frames[nextVictim] = address.getPageNumber();
                nextVictim = (nextVictim + 1) % this.frames.length;
                pageFaultCount++;
            }
        }
        long estimatedTime = System.nanoTime() - startTime;
        double finalTime = estimatedTime/1000000000.0;
        System.out.println("Execution Time : " + new DecimalFormat("#.####").format(finalTime) + " Seconds");
        finalTable();
    }
}
