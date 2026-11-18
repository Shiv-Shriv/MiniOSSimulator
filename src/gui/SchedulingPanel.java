
package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import model.Process;
import scheduler.*;

public class SchedulingPanel extends JPanel {

    private JTextField pidField, arrivalField, burstField, priorityField;
    private JTextField quantumField;

    private JComboBox<String> algorithmBox;

    private DefaultTableModel processModel;
    private DefaultTableModel resultModel;

    private JPanel chartContainer;

    private List<Process> processes = new ArrayList<>();

    public SchedulingPanel() {

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("CPU Scheduling");
        title.setFont(new Font("Arial", Font.BOLD, 26));

        add(title, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setOpaque(false);


        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        pidField = new JTextField(4);
        arrivalField = new JTextField(4);
        burstField = new JTextField(4);
        priorityField = new JTextField(4);

        inputPanel.add(new JLabel("PID:"));
        inputPanel.add(pidField);

        inputPanel.add(new JLabel("Arrival:"));
        inputPanel.add(arrivalField);

        inputPanel.add(new JLabel("Burst:"));
        inputPanel.add(burstField);

        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(priorityField);

        JButton addButton = new JButton("Add Process");
        inputPanel.add(addButton);

    
        processModel = new DefaultTableModel(
            new String[]{"PID", "Arrival", "Burst", "Priority"}, 0
        );

        JTable processTable = new JTable(processModel);
        JScrollPane processScroll = new JScrollPane(processTable);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(processScroll, BorderLayout.CENTER);

    
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        algorithmBox = new JComboBox<>(
            new String[]{"FCFS", "SJF", "Round Robin"}
        );

        quantumField = new JTextField("2", 4);

        JButton runButton = new JButton("Run Scheduling");

        controlPanel.add(new JLabel("Algorithm:"));
        controlPanel.add(algorithmBox);
        controlPanel.add(new JLabel("Time Quantum:"));
        controlPanel.add(quantumField);
        controlPanel.add(runButton);

    
        resultModel = new DefaultTableModel(
            new String[]{"PID", "CT", "TAT", "WT"}, 0
        );

        JTable resultTable = new JTable(resultModel);
        JScrollPane resultScroll = new JScrollPane(resultTable);

    
        chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBackground(Color.WHITE);
        chartContainer.setPreferredSize(new Dimension(700, 150));

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(controlPanel, BorderLayout.NORTH);
        bottomPanel.add(resultScroll, BorderLayout.CENTER);
        bottomPanel.add(chartContainer, BorderLayout.SOUTH);

        topPanel.setMinimumSize(new Dimension(700, 140));
        bottomPanel.setMinimumSize(new Dimension(700, 180));

        JSplitPane splitPane = new JSplitPane(
            JSplitPane.VERTICAL_SPLIT,
            topPanel,
            bottomPanel
        );

        splitPane.setResizeWeight(0.4);
        splitPane.setDividerLocation(230);
        splitPane.setOneTouchExpandable(true);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);


        addButton.addActionListener(e -> addProcess());

        runButton.addActionListener(e -> runScheduling());
    }

    private void addProcess() {

        try {

            int pid = Integer.parseInt(pidField.getText());
            int arrival = Integer.parseInt(arrivalField.getText());
            int burst = Integer.parseInt(burstField.getText());
            int priority = Integer.parseInt(priorityField.getText());

            if (pid <= 0 || arrival < 0 || burst <= 0 || priority < 0) {
                throw new IllegalArgumentException("Invalid process values.");
            }

            for (Process p : processes) {
                if (p.getProcessId() == pid) {
                    throw new IllegalArgumentException("PID already exists.");
                }
            }

            processes.add(new Process(pid, arrival, burst, priority));

            processModel.addRow(new Object[]{
                pid, arrival, burst, priority
            });

            pidField.setText("");
            arrivalField.setText("");
            burstField.setText("");
            priorityField.setText("");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                this, "Please enter valid integer values."
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void runScheduling() {

        if (processes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Add processes first.");
            return;
        }

        List<Process> executionList = new ArrayList<>();

        for (Process p : processes) {
            executionList.add(new Process(
                p.getProcessId(),
                p.getArrivalTime(),
                p.getBurstTime(),
                p.getPriority()
            ));
        }

        String selected = (String) algorithmBox.getSelectedItem();

        Scheduler scheduler;

        try {

            if ("FCFS".equals(selected)) {
                scheduler = new FCFS();

            } else if ("SJF".equals(selected)) {
                scheduler = new SJF();

            } else {
                int quantum = Integer.parseInt(quantumField.getText());

                if (quantum <= 0) {
                    throw new IllegalArgumentException(
                        "Time quantum must be positive."
                    );
                }

                scheduler = new RoundRobin(quantum);
            }

            List<GanttChartEntry> chart =
                scheduler.schedule(executionList);

            resultModel.setRowCount(0);

            for (Process p : executionList) {
                resultModel.addRow(new Object[]{
                    "P" + p.getProcessId(),
                    p.getCompletionTime(),
                    p.getTurnaroundTime(),
                    p.getWaitingTime()
                });
            }

            SchedulingResult result =
                new SchedulingResult(executionList);

            JOptionPane.showMessageDialog(
                this,
                String.format(
                    "Average Waiting Time: %.2f\nAverage Turnaround Time: %.2f",
                    result.getAverageWaitingTime(),
                    result.getAverageTurnaroundTime()
                )
            );

            chartContainer.removeAll();
            chartContainer.add(
                new GanttChartPanel(chart),
                BorderLayout.CENTER
            );

            chartContainer.revalidate();
            chartContainer.repaint();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                this, "Enter a valid time quantum."
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
