import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import gui.GanttChartPanel;
import memory.FirstFit;
import memory.MemoryAllocator;
import model.MemoryBlock;
import model.Process;
import scheduler.*;
import threads.Consumer;
import threads.Producer;
import threads.SharedBuffer;
import deadlock.*;
import threads.*;
import gui.MainFrame;

public class Main {

    public static void testMultithreading() {

    SharedBuffer buffer = new SharedBuffer();

    Thread producer = new Thread(new Producer(buffer));
    Thread consumer = new Thread(new Consumer(buffer));

    producer.start();
    consumer.start();

    try {
        producer.join();
        consumer.join();
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}



    public static void testDeadlock() {

    int[][] allocation = {
        {0, 1, 0},
        {2, 0, 0},
        {3, 0, 2},
        {2, 1, 1},
        {0, 0, 2}
    };

    int[][] max = {
        {7, 5, 3},
        {3, 2, 2},
        {9, 0, 2},
        {2, 2, 2},
        {4, 3, 3}
    };

    int[] available = {3, 3, 2};

    BankersAlgorithm banker = new BankersAlgorithm();

    DeadlockResult result = banker.checkSafeState(
        allocation, max, available
    );

    if (result.isSafe()) {
        System.out.println("System is in a SAFE state.");
        System.out.println("Safe Sequence: " + result.getSafeSequence());
    } else {
        System.out.println("System is in an UNSAFE state.");
    }
}

    public static void testMemoryAllocation() {

    List<MemoryBlock> blocks = new ArrayList<>();

    blocks.add(new MemoryBlock(1, 100));
    blocks.add(new MemoryBlock(2, 500));
    blocks.add(new MemoryBlock(3, 200));
    blocks.add(new MemoryBlock(4, 300));

    MemoryAllocator allocator = new FirstFit();

    int[] processes = {150, 250, 100, 400};

    for (int size : processes) {

        int blockId = allocator.allocate(blocks, size);

        if (blockId != -1) {
            System.out.println("Process " + size +
                " allocated to Block " + blockId);
        } else {
            System.out.println("Process " + size +
                " could not be allocated.");
        }
    }

    System.out.println("\nRemaining Memory:");

    for (MemoryBlock block : blocks) {
        System.out.println("Block " + block.getBlockId() +
            ": " + block.getRemainingSize());
    }
}



    public static List<Process> createProcesses() {

        List<Process> processes = new ArrayList<>();

        processes.add(new Process(1, 0, 5, 2));
        processes.add(new Process(2, 1, 3, 1));
        processes.add(new Process(3, 2, 8, 3));
        processes.add(new Process(4, 3, 6, 2));

        return processes;
    }

    public static void main(String[] args) {


        System.out.println("\n========== PRODUCER-CONSUMER ==========");
        testMultithreading();

        testDeadlock();

        testMemoryAllocation();

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