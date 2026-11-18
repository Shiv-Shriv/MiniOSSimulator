package deadlock;

import java.util.ArrayList;
import java.util.List;

public class BankersAlgorithm {

    public DeadlockResult checkSafeState(
        int[][] allocation,
        int[][] max,
        int[] available
    ) {

        int n = allocation.length;
        int m = available.length;

        int[][] need = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        int[] work = available.clone();
        boolean[] finish = new boolean[n];

        List<Integer> safeSequence = new ArrayList<>();

        while (safeSequence.size() < n) {

            boolean found = false;

            for (int i = 0; i < n; i++) {

                if (finish[i]) {
                    continue;
                }

                boolean canExecute = true;

                for (int j = 0; j < m; j++) {
                    if (need[i][j] > work[j]) {
                        canExecute = false;
                        break;
                    }
                }

                if (canExecute) {

                    for (int j = 0; j < m; j++) {
                        work[j] += allocation[i][j];
                    }

                    finish[i] = true;
                    safeSequence.add(i);
                    found = true;
                }
            }

            if (!found) {
                return new DeadlockResult(false, safeSequence);
            }
        }

        return new DeadlockResult(true, safeSequence);
    }
}