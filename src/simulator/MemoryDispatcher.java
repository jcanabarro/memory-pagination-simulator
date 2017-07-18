package simulator;

import simulator.Address.LogicalAddress;
import simulator.Replacer.Replacer;

class MemoryDispatcher {

    /**
     * The class that represents the substitutor.
     */
    private Replacer substitutionMethod;

    /**
     * Size of each frame address. (2^frameSize = number of addressable entities within a frame, 2^(32-frameSize) = number of addressble logical pages)
     */
    private int frameSize;

    /**
     * Construct the MemoryDispatcher
     * @param method The replacement method.
     * @param frameSize Size of each frame.
     */
    MemoryDispatcher (Replacer method, int frameSize) {
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
     * Perform the access.
     */
    void run () {
        this.substitutionMethod.run();
    }
}
