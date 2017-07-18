package simulator.Replacer;

import simulator.Address.LogicalAddress;

public abstract class Replacer {

    /**
     * Each frame position have in itself the page that it belong.
     * This way, we don't need to manage valid/invalid bits inside the pages table.
     */
    int[] frames;

    Replacer (int framesNumber) {
        this.frames = new int[framesNumber];
    }

    /**
     * Verify if the address is allocated.
     * @param page The page identifier.
     * @return Present/Not present
     */
    boolean isAddressPresent (int page){
        for (int frame : this.frames) {
            if (page == frame) {
                return true;
            }
        }

        return false;
    }

    /**
     * Try to access a determined address.
     * @param address The logical access address, which will be translated into frame access.
     */
    public abstract void inputAddress (LogicalAddress address);
}
