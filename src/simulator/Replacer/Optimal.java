package simulator.Replacer;

import simulator.Address.LogicalAddress;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
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
    public Optimal(int framesNumber, ArrayList<LogicalAddress> accessList) {
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

    private int distanceToNextUse(int address) {
        ArrayList<Integer> nextAccess = accessPoints.get(address);
        for (int i = 0; i < nextAccess.size(); i++) {
            if (nextAccess.get(i) == -1) continue;

            int dif;

            if (i < nextAccess.size() - 1)
                dif = nextAccess.get(i + 1) - nextAccess.get(i);
            else
                dif = EMPTY;
            nextAccess.set(i, -1);
            return dif;
        }

        return EMPTY;
    }

    private int getNextVictim() {
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

    private void updateNextUseAuxiliar(int victim) {
        for (int i = 0; i < nextUseAuxiliar.length; i++) {
            if (i == victim || nextUseAuxiliar[i] == 1) {
                nextUseAuxiliar[i] = distanceToNextUse(frames[victim]);
            } else if (nextUseAuxiliar[i] != EMPTY) {
                nextUseAuxiliar[i]--;
            }
        }
    }

    private void printAux() {
        for (int aNextUseAuxiliar : nextUseAuxiliar) {
            System.out.print(aNextUseAuxiliar + ",");
        }
        System.out.println("");
    }

    private void updateNextUseAuxiliar() {
        for (int i = 0; i < nextUseAuxiliar.length; i++) {
            if (nextUseAuxiliar[i] > 0) {
                nextUseAuxiliar[i]--;
            }

            if (nextUseAuxiliar[i] == 0) {
                nextUseAuxiliar[i] = distanceToNextUse(frames[i]);
            }
        }
    }

    private void printHash() {
        for (int key : accessPoints.keySet()) {
            System.out.println(key + ":" + accessPoints.get(key));
        }
    }

    /**
     * Execute the memory access.
     */
    @Override
    public void run() {
        fillHashTable();
        long startTime = System.nanoTime();
        for (LogicalAddress anAccessList : this.accessList) {
            if (!isAddressPresent(anAccessList.getPageNumber())) {
                int victim;
                victim = this.getNextVictim();
                frames[victim] = anAccessList.getPageNumber();
                updateNextUseAuxiliar(victim);
                pageFaultCount++;
            } else {
                updateNextUseAuxiliar();
           }
        }
        long estimatedTime = System.nanoTime() - startTime;
        double finalTime = estimatedTime/1000000000.0;
        System.out.println("Execution Time : " + new DecimalFormat("#.####").format(finalTime) + " Seconds");
        finalTable();
    }
}
