package scheduler;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import model.Process;

public class FCFS implements Scheduler {
     @Override
    public void schedule(List<Process> processes) {

        List<Process> sorted = new ArrayList<>(processes);

        sorted.sort(Comparator.comparingInt(Process::getArrivalTime));

        int currentTime = 0;

        for (Process p : sorted) {

            if (currentTime < p.getArrivalTime()) {
                currentTime = p.getArrivalTime();
            }

            currentTime += p.getBurstTime();

            p.setCompletionTime(currentTime);
            p.setTurnaroundTime(currentTime - p.getArrivalTime());
            p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());
        }
    }

}
