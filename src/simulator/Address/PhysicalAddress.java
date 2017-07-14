package simulator.Address;

public class PhysicalAddress {
    private int offset;

    private int frameNumber;

    public PhysicalAddress (int offset, int frameNumber) {
        this.offset = offset;
        this.frameNumber = frameNumber;
    }

    public int getOffset () {
        return offset;
    }

    public void setOffset (int offset) {
        this.offset = offset;
    }

    public int getFrameNumber () {
        return frameNumber;
    }

    public void setFrameNumber (int frameNumber) {
        this.frameNumber = frameNumber;
    }
}
