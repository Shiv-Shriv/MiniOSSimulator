package scheduler;

import java.util.List;
import java.util.ArrayList;
import model.Process;

public class SJF implements Scheduler {

    @Override
    public List<GanttChartEntry> schedule(List<Process> processes) {

        List<Process> pending = new ArrayList<>(processes);
        List<GanttChartEntry> chart = new ArrayList<>();

        int currentTime = 0;
        int completed = 0;
        int n = pending.size();

        while (completed < n) {

            Process shortest = null;

            for (Process p : pending) {

                if (p.getCompletionTime() == 0 &&
                    p.getArrivalTime() <= currentTime) {

                    if (shortest == null ||
                        p.getBurstTime() < shortest.getBurstTime()) {
                        shortest = p;
                    }
                }
            }

            if (shortest == null) {
                currentTime++;
                continue;
            }

            int startTime = currentTime;
            currentTime += shortest.getBurstTime();

            chart.add(new GanttChartEntry(
                shortest.getProcessId(), startTime, currentTime
            ));

            shortest.setCompletionTime(currentTime);
            shortest.setTurnaroundTime(currentTime - shortest.getArrivalTime());
            shortest.setWaitingTime(shortest.getTurnaroundTime() - shortest.getBurstTime());

            completed++;
        }

        return chart;
    }
}