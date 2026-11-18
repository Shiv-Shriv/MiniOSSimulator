package scheduler;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import model.Process;

public class RoundRobin implements Scheduler {

    private int quantum;

    public RoundRobin(int quantum) {
        this.quantum = quantum;
    }

    @Override
    public List<GanttChartEntry> schedule(List<Process> processes) {

        List<Process> sorted = new ArrayList<>(processes);
        sorted.sort((a, b) -> Integer.compare(
            a.getArrivalTime(), b.getArrivalTime()));

        List<GanttChartEntry> chart = new ArrayList<>();

        Queue<Process> readyQueue = new LinkedList<>();

        int currentTime = 0;
        int index = 0;
        int completed = 0;

        while (completed < sorted.size()) {

            while (index < sorted.size() &&
                   sorted.get(index).getArrivalTime() <= currentTime) {
                readyQueue.add(sorted.get(index++));
            }

            if (readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            Process p = readyQueue.poll();

            int startTime = currentTime;
            int executionTime = Math.min(quantum, p.getRemainingTime());

            currentTime += executionTime;
            p.setRemainingTime(p.getRemainingTime() - executionTime);

            chart.add(new GanttChartEntry(
                p.getProcessId(), startTime, currentTime
            ));

            while (index < sorted.size() &&
                   sorted.get(index).getArrivalTime() <= currentTime) {
                readyQueue.add(sorted.get(index++));
            }

            if (p.getRemainingTime() > 0) {
                readyQueue.add(p);
            } else {
                p.setCompletionTime(currentTime);
                p.setTurnaroundTime(currentTime - p.getArrivalTime());
                p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());
                completed++;
            }
        }

        return chart;
    }
}