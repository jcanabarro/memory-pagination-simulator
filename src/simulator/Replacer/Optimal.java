package simulator.Replacer;

import simulator.Address.LogicalAddress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class Optimal extends Replacer {

    private final static int EMPTY = -1;

    private int nextUseAuxiliar[];

    /**
     * Hash table of the "time points" where each access will be claimed.
     */
    private static Hashtable<Integer, ArrayList<Integer>> accessPoints;


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

    private void fillHashTable() {
        accessPoints = new Hashtable<>();
        for (int i = 0; i < this.accessList.size(); i++) {
            ArrayList<Integer> positionAddress = accessPoints.computeIfAbsent(accessList.get(i).getPageNumber(), k -> new ArrayList<>());
            positionAddress.add(i);
        }
    }

    private int distanceToNextUse (int from, int address) {
        ArrayList<Integer> nextAccess = accessPoints.get(address);

        for (int i = 0; i < nextAccess.size(); i++) {
            if(nextAccess.get(i) < from) continue;

            return i;
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
  //          System.out.print(nextUseAuxiliar[i] + ",");
        }
//        System.out.println("");
    }

    private void updateNextUseAuxiliar() {
        for (int i = 0; i < nextUseAuxiliar.length; i++) {
            if (nextUseAuxiliar[i] == 0) {
                nextUseAuxiliar[i] = distanceToNextUse(i, frames[i]);
            } else if (nextUseAuxiliar[i] != EMPTY) {
                nextUseAuxiliar[i]--;
            }
        }
    }

    /**
     * Execute the memory access.
     */
    @Override
    public void run () {
        fillHashTable();
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
