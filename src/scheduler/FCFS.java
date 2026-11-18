package scheduler;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import model.Process;

public class FCFS implements Scheduler {

    @Override
    public List<GanttChartEntry> schedule(List<Process> processes) {

        List<Process> sorted = new ArrayList<>(processes);
        sorted.sort(Comparator.comparingInt(Process::getArrivalTime));

        List<GanttChartEntry> chart = new ArrayList<>();
        int currentTime = 0;

        for (Process p : sorted) {

            if (currentTime < p.getArrivalTime()) {
                currentTime = p.getArrivalTime();
            }

            int startTime = currentTime;
            currentTime += p.getBurstTime();

            chart.add(new GanttChartEntry(
                p.getProcessId(), startTime, currentTime
            ));

            p.setCompletionTime(currentTime);
            p.setTurnaroundTime(currentTime - p.getArrivalTime());
            p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());
        }

        return chart;
    }
}