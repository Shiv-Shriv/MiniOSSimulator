import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import gui.GanttChartPanel;

import model.Process;
import scheduler.*;

public class Main {

    public static List<Process> createProcesses() {

        List<Process> processes = new ArrayList<>();

        processes.add(new Process(1, 0, 5, 2));
        processes.add(new Process(2, 1, 3, 1));
        processes.add(new Process(3, 2, 8, 3));
        processes.add(new Process(4, 3, 6, 2));

        return processes;
    }

    public static void main(String[] args) {

        List<Process> fcfsProcesses = createProcesses();
        Scheduler fcfs = new FCFS();
        List<GanttChartEntry> fcfsChart = fcfs.schedule(fcfsProcesses);

        System.out.println("\n========== FCFS ==========");
        new SchedulingResult(fcfsProcesses).displayResults(fcfsProcesses);
        
        JFrame frame = new JFrame("FCFS Gantt Chart");
        frame.add(new GanttChartPanel(fcfsChart));
        frame.setSize(800, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        List<Process> sjfProcesses = createProcesses();
        Scheduler sjf = new SJF();
        List<GanttChartEntry> sjFChart = sjf.schedule(sjfProcesses);

        System.out.println("\n========== SJF ==========");
        new SchedulingResult(sjfProcesses).displayResults(sjfProcesses);

        List<Process> rrProcesses = createProcesses();
        Scheduler rr = new RoundRobin(2);
        List<GanttChartEntry> rrChart = rr.schedule(rrProcesses);

        System.out.println("\n========== ROUND ROBIN ==========");
        new SchedulingResult(rrProcesses).displayResults(rrProcesses);
    }
}