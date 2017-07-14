package simulator.Replacer;

import simulator.Address.LogicalAddress;

import java.util.ArrayList;

public abstract class Replacer {
    private ArrayList<String> accessList;

    private int pageSize;

    private int framesNumber;

    private int trueOffset;

    private int offset;

    private int pageNumber;

    Replacer (ArrayList<String> accessList, int pageSize, int framesNumber) {
        this.accessList = accessList;
        this.pageSize = pageSize;
        this.framesNumber = framesNumber;
    }

    protected LogicalAddress convertAddress (String logicalAddress) {
        String temporaryOffset = "";
        String temporaryPageNumber = "";
        int i = 0;
        for (; i < 32 - pageSize; i++){
            temporaryPageNumber += logicalAddress.charAt(i);
        }
        for (; i < 32; i++) {
            temporaryOffset += logicalAddress.charAt(i);
        }

        offset = Integer.parseInt(temporaryOffset, 2);
        pageNumber = Integer.parseInt(temporaryPageNumber, 2);

        return new LogicalAddress(offset, pageNumber);
    }

    public abstract void run ();
}
