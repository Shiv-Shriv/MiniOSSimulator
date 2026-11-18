package memory;

import java.util.List;
import model.MemoryBlock;

public abstract class MemoryAllocator {

    public abstract int allocate(List<MemoryBlock> blocks, int processSize);

}