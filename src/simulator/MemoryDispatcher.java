package simulator;

import simulator.Address.LogicalAddress;
import simulator.Replacer.Replacer;

import java.util.ArrayList;

class MemoryDispatcher {

    /**
     * The class that represents the substitutor.
     */
    private Replacer substitutionMethod;

    /**
     * The memory access list.
     */
    private ArrayList<String> accessList;

    /**
     * Size of each frame address. (2^frameSize = number of addressable entities within a frame, 2^(32-frameSize) = number of addressble logical pages)
     */
    private int frameSize;

    /**
     * Construct the MemoryDispatcher
     * @param accessList List of desired memory access.
     * @param method The replacement method.
     * @param frameSize Size of each frame.
     */
    MemoryDispatcher (ArrayList<String> accessList, Replacer method, int frameSize) {
        this.accessList = accessList;
        this.substitutionMethod = method;
        this.frameSize = frameSize;
    }

    /**
     * Switch the replacer method to another.
     * @param method Desired method (must implement Replacer).
     */
    public void changeMethod (Replacer method) {
        this.substitutionMethod = method;
    }

    /**
     * Translate a raw access string into a readable logical address.
     * @param logicalAddress Binary string.
     * @return The logical address that the binary string represent.
     */
    private LogicalAddress translate (String logicalAddress) {
        String temporaryOffset = "";
        String temporaryPageNumber = "";
        int i = 0;
        for (; i < 32 - frameSize; i++){
            temporaryPageNumber += logicalAddress.charAt(i);
        }
        for (; i < 32; i++) {
            temporaryOffset += logicalAddress.charAt(i);
        }

        return new LogicalAddress(Integer.parseInt(temporaryOffset, 2), Integer.parseInt(temporaryPageNumber, 2));
    }

    /**
     * Perform the access.
     */
    void run () {
        for (String anAccessList : this.accessList) {
            this.substitutionMethod.inputAddress(translate(anAccessList));
        }
    }
}
