package memory;

import java.util.List;
import model.MemoryBlock;

public class WorstFit extends MemoryAllocator {

    @Override
    public int allocate(List<MemoryBlock> blocks, int processSize) {

        MemoryBlock worst = null;

        for (MemoryBlock block : blocks) {

            if (block.getRemainingSize() >= processSize) {

                if (worst == null ||
                    block.getRemainingSize() > worst.getRemainingSize()) {
                    worst = block;
                }
            }
        }

        if (worst != null) {
            worst.setRemainingSize(
                worst.getRemainingSize() - processSize
            );

            return worst.getBlockId();
        }

        return -1;
    }
}