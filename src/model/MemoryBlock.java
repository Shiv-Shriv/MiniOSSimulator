package model;

public class MemoryBlock {

    private int blockId;
    private int size;
    private int remainingSize;

    public MemoryBlock(int blockId, int size) {
        this.blockId = blockId;
        this.size = size;
        this.remainingSize = size;
    }

    public int getBlockId() {
        return blockId;
    }

    public int getSize() {
        return size;
    }

    public int getRemainingSize() {
        return remainingSize;
    }

    public void setRemainingSize(int remainingSize) {
        this.remainingSize = remainingSize;
    }
}