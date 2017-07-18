package simulator.Replacer;

import simulator.Address.LogicalAddress;

import java.util.ArrayList;

public class Optimal extends Replacer {

    /**
     * Initialize new Optimal instance.
     *
     * @param framesNumber Number of frames.
     */
    public Optimal (int framesNumber, ArrayList<LogicalAddress> accessList) {
        super(framesNumber, accessList);
    }

    private int getNextVictim (int start) {
        int next = 0;
        int distance = 0;
        for (int i = 0; i < frames.length; i++) {
            for (int j = start; j < this.accessList.size(); j++) {
                if (frames[i] == this.accessList.get(j).getPageNumber()) {
                    if (j > distance) {
                        distance = j;
                        next = i;
                    }
                    break;
                }
            }
        }
        return next;
    }

    /**
     * Execute the memory access.
     */
    @Override
    public void run () {
        for (int i = 0; i < this.accessList.size(); i++) {
            if (!isAddressPresent(this.accessList.get(i).getPageNumber())) {
                frames[this.getNextVictim(i + 1)] = this.accessList.get(i).getPageNumber();
                pageFaultCount++;
                System.out.println("Page fault #" + pageFaultCount + " at address " + this.accessList.get(i).getPageNumber());
            }
        }
    }
}
