package memory;

import java.util.List;
import model.MemoryBlock;

public class FirstFit extends MemoryAllocator {

    @Override
    public int allocate(List<MemoryBlock> blocks, int processSize) {

        for (MemoryBlock block : blocks) {

            if (block.getRemainingSize() >= processSize) {

                block.setRemainingSize(
                    block.getRemainingSize() - processSize
                );

                return block.getBlockId();
            }
        }

        return -1;
    }
}