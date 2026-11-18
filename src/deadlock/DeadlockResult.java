package deadlock;

import java.util.List;

public class DeadlockResult {

    private boolean safe;
    private List<Integer> safeSequence;

    public DeadlockResult(boolean safe, List<Integer> safeSequence) {
        this.safe = safe;
        this.safeSequence = safeSequence;
    }

    public boolean isSafe() {
        return safe;
    }

    public List<Integer> getSafeSequence() {
        return safeSequence;
    }
}