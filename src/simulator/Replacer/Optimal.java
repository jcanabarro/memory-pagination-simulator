package simulator.Replacer;

import simulator.Address.LogicalAddress;

import java.util.ArrayList;
import java.util.Arrays;

public class Optimal extends Replacer {

    private final static int EMPTY = -1;

    private int nextUseAuxiliar[];

    /**
     * Initialize new Optimal instance.
     *
     * @param framesNumber Number of frames.
     */
    public Optimal (int framesNumber, ArrayList<LogicalAddress> accessList) {
        super(framesNumber, accessList);
        nextUseAuxiliar = new int[framesNumber];
        Arrays.fill(nextUseAuxiliar, EMPTY);
    }

    private int distanceToNextUse (int start, int address) {
        for (int i = start + 1; i < this.accessList.size(); i++) {
            if (accessList.get(i).getPageNumber() == address) {
                return i - start;
            }
        }

        return EMPTY;
    }

    private int getNextVictim () {
        int biggestValue = 0;
        int indexOfBiggestValue = EMPTY;
        for (int i = 0; i < nextUseAuxiliar.length; i++) {
            if (nextUseAuxiliar[i] == EMPTY) {
                return i;
            } else if (nextUseAuxiliar[i] >= biggestValue) {
                biggestValue = nextUseAuxiliar[i];
                indexOfBiggestValue = i;
            }
        }

        return indexOfBiggestValue;
    }

    private void updateNextUseAuxiliar(int from, int newest) {
        for (int i = 0; i < nextUseAuxiliar.length; i++) {
            if (i == newest || nextUseAuxiliar[i] == 1) {
                nextUseAuxiliar[i] = distanceToNextUse(from, frames[newest]);
            } else if (nextUseAuxiliar[i] != EMPTY) {
                nextUseAuxiliar[i]--;
            }
//            System.out.print(nextUseAuxiliar[i] + ",");
        }
//        System.out.println("");
    }

    private void updateNextUseAuxiliar() {
        for (int i = 0; i < nextUseAuxiliar.length; i++) {
            if (nextUseAuxiliar[i] != EMPTY) {
                nextUseAuxiliar[i]--;
            }
        }
    }

    /**
     * Execute the memory access.
     */
    @Override
    public void run () {
        for (int i = 0; i < this.accessList.size(); i++) {
            if (!isAddressPresent(this.accessList.get(i).getPageNumber())) {
                int victim = this.getNextVictim();
                frames[victim] = this.accessList.get(i).getPageNumber();
                updateNextUseAuxiliar(i, victim);
                pageFaultCount++;
                System.out.println("Page fault #" + pageFaultCount + " at address " + this.accessList.get(i).getPageNumber() + " in position " + victim);
                print();
            } else {
                updateNextUseAuxiliar();
            }
        }
    }
}
