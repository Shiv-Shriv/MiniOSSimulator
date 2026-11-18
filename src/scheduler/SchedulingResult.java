package scheduler;

import java.util.List;
import model.Process;

public class SchedulingResult {

    private double averageWaitingTime;
    private double averageTurnaroundTime;

    public SchedulingResult(List<Process> processes) {

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (Process p : processes) {
            totalWaitingTime += p.getWaitingTime();
            totalTurnaroundTime += p.getTurnaroundTime();
        }

        int n = processes.size();

        if (n > 0) {
            averageWaitingTime = totalWaitingTime / n;
            averageTurnaroundTime = totalTurnaroundTime / n;
        }
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public double getAverageTurnaroundTime() {
        return averageTurnaroundTime;
    }

    public void displayResults(List<Process> processes) {

        System.out.println("\nProcess\tCT\tTAT\tWT");

        for (Process p : processes) {
            System.out.println(
                "P" + p.getProcessId() + "\t" +
                p.getCompletionTime() + "\t" +
                p.getTurnaroundTime() + "\t" +
                p.getWaitingTime()
            );
        }

        System.out.printf("\nAverage Waiting Time: %.2f%n", averageWaitingTime);
        System.out.printf("Average Turnaround Time: %.2f%n", averageTurnaroundTime);
    }
}