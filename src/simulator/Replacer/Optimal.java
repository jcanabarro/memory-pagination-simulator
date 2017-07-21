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

    private int distanceNextTime(int start, int andress) {
        for(int i = start; i < this.accessList.size(); i++) {
            if(accessList.get(i).getPageNumber() == andress) {
                int distance = i - start;
                return distance;
            }
        }

        return -1;
    }

    private int getNextVictim (int start) {
        int next = -1;
        int distance = 0;
        int tmpDistance = -2;
        for(int i = 0; i < frames.length; i++) {
            if(frames[i] == -1)
                return i;

            tmpDistance = distanceNextTime(start, frames[i]);
            if(tmpDistance == -1)
                return i;
            
            if(tmpDistance > distance) {
                distance = tmpDistance;
                next = i;
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
                int victim = this.getNextVictim(i + 1);
                frames[victim] = this.accessList.get(i).getPageNumber();
                pageFaultCount++;
                System.out.println("Page fault #" + pageFaultCount + " at address " + this.accessList.get(i).getPageNumber() + " in position " + victim);
                print();
            }
        }
    }
}
