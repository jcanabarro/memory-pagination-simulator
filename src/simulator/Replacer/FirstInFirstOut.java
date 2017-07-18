package simulator.Replacer;

import simulator.Address.LogicalAddress;

public class FirstInFirstOut extends Replacer {

    /**
     * Next frame to be substituted.
     */
    private int nextVictim = 0;

    /**
     * Number of page faults.
     */
    private int pageFaultCount = 0;

    public FirstInFirstOut (int framesNumber) {
        super(framesNumber);
    }

    @Override
    public void inputAddress (LogicalAddress address) {
        if (!isAddressPresent(address.getPageNumber())){
            frames[nextVictim] = address.getPageNumber();
            nextVictim = (nextVictim + 1) % this.frames.length;
            pageFaultCount++;
            System.out.println("Page fault #" + pageFaultCount + " at address " + address.getPageNumber());
        }
    }
}
