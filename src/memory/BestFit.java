package memory;

import java.util.List;
import model.MemoryBlock;

public class BestFit extends MemoryAllocator {

    @Override
    public int allocate(List<MemoryBlock> blocks, int processSize) {

        MemoryBlock best = null;

        for (MemoryBlock block : blocks) {

            if (block.getRemainingSize() >= processSize) {

                if (best == null ||
                    block.getRemainingSize() < best.getRemainingSize()) {
                    best = block;
                }
            }
        }

        if (best != null) {
            best.setRemainingSize(
                best.getRemainingSize() - processSize
            );

            return best.getBlockId();
        }

        return -1;
    }
}