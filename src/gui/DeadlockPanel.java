
package gui;

import javax.swing.*;
import java.awt.*;
import deadlock.*;

public class DeadlockPanel extends JPanel {

    private JTextArea allocationArea;
    private JTextArea maxArea;
    private JTextField availableField;
    private JTextArea resultArea;

    private JTextField processCountField;
    private JTextField resourceCountField;

    public DeadlockPanel() {

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("Deadlock Detection - Banker's Algorithm");
        title.setFont(new Font("Arial", Font.BOLD, 24));

        processCountField = new JTextField("5", 5);
        resourceCountField = new JTextField("3", 5);

        JButton generateButton = new JButton("Generate Matrices");

        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        settingsPanel.add(new JLabel("Processes:"));
        settingsPanel.add(processCountField);

        settingsPanel.add(new JLabel("Resource Types:"));
        settingsPanel.add(resourceCountField);

        settingsPanel.add(generateButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(settingsPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        allocationArea = new JTextArea(8, 15);
        maxArea = new JTextArea(8, 15);
        availableField = new JTextField();

        allocationArea.setText(
            "0,1,0\n2,0,0\n3,0,2\n2,1,1\n0,0,2"
        );

        maxArea.setText(
            "7,5,3\n3,2,2\n9,0,2\n2,2,2\n4,3,3"
        );

        availableField.setText("3,3,2");

        JPanel inputPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        inputPanel.add(createInputBox("Allocation Matrix", allocationArea));
        inputPanel.add(createInputBox("Maximum Matrix", maxArea));

        JPanel availablePanel = new JPanel(new BorderLayout(5, 5));
        availablePanel.add(
            new JLabel("Available Resources"),
            BorderLayout.NORTH
        );
        availablePanel.add(availableField, BorderLayout.CENTER);

        inputPanel.add(availablePanel);

        add(inputPanel, BorderLayout.CENTER);

        JButton checkButton = new JButton("Check Safe State");

        resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(checkButton, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        generateButton.addActionListener(e -> generateMatrices());
        checkButton.addActionListener(e -> checkDeadlock());
    }

    private JPanel createInputBox(String title, JTextArea area) {

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        panel.add(new JLabel(title), BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);

        return panel;
    }

    private void generateMatrices() {

        try {

            int processes = Integer.parseInt(
                processCountField.getText().trim()
            );

            int resources = Integer.parseInt(
                resourceCountField.getText().trim()
            );

            if (processes <= 0 || resources <= 0 ||
                processes > 20 || resources > 10) {

                throw new IllegalArgumentException(
                    "Processes: 1-20. Resource types: 1-10."
                );
            }

            StringBuilder allocation = new StringBuilder();
            StringBuilder max = new StringBuilder();
            StringBuilder available = new StringBuilder();

            for (int i = 0; i < processes; i++) {

                for (int j = 0; j < resources; j++) {

                    allocation.append("0");
                    max.append("0");

                    if (j < resources - 1) {
                        allocation.append(",");
                        max.append(",");
                    }
                }

                if (i < processes - 1) {
                    allocation.append("\n");
                    max.append("\n");
                }
            }

            for (int j = 0; j < resources; j++) {

                available.append("0");

                if (j < resources - 1) {
                    available.append(",");
                }
            }

            allocationArea.setText(allocation.toString());
            maxArea.setText(max.toString());
            availableField.setText(available.toString());

            resultArea.setText(
                "Matrices generated successfully.\n" +
                "Enter resource values and click Check Safe State."
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                this, "Enter valid process and resource counts."
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private int[][] parseMatrix(String input) {

        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException("Matrix cannot be empty.");
        }

        String[] rows = input.trim().split("\\R");
        int[][] matrix = new int[rows.length][];

        int columns = -1;

        for (int i = 0; i < rows.length; i++) {

            String[] values = rows[i].trim().split(",");

            if (columns == -1) {
                columns = values.length;
            }

            if (values.length != columns) {
                throw new IllegalArgumentException(
                    "Matrix rows must have equal columns."
                );
            }

            matrix[i] = new int[columns];

            for (int j = 0; j < columns; j++) {

                matrix[i][j] = Integer.parseInt(values[j].trim());

                if (matrix[i][j] < 0) {
                    throw new IllegalArgumentException(
                        "Resource values cannot be negative."
                    );
                }
            }
        }

        return matrix;
    }

    private int[] parseAvailable(String input) {

        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Available resources cannot be empty."
            );
        }

        String[] values = input.trim().split(",");
        int[] available = new int[values.length];

        for (int i = 0; i < values.length; i++) {

            available[i] = Integer.parseInt(values[i].trim());

            if (available[i] < 0) {
                throw new IllegalArgumentException(
                    "Available resources cannot be negative."
                );
            }
        }

        return available;
    }

    private void checkDeadlock() {

        try {

            int[][] allocation = parseMatrix(allocationArea.getText());
            int[][] max = parseMatrix(maxArea.getText());
            int[] available = parseAvailable(availableField.getText());

            if (allocation.length != max.length ||
                allocation[0].length != max[0].length ||
                available.length != allocation[0].length) {

                throw new IllegalArgumentException(
                    "Matrix dimensions do not match."
                );
            }

            for (int i = 0; i < allocation.length; i++) {

                for (int j = 0; j < available.length; j++) {

                    if (allocation[i][j] > max[i][j]) {
                        throw new IllegalArgumentException(
                            "Allocation cannot exceed maximum demand."
                        );
                    }
                }
            }

            BankersAlgorithm banker = new BankersAlgorithm();

            DeadlockResult result = banker.checkSafeState(
                allocation, max, available
            );

            if (result.isSafe()) {

                StringBuilder sequence = new StringBuilder();

                for (int i : result.getSafeSequence()) {
                    sequence.append("P").append(i).append(" -> ");
                }

                if (sequence.length() >= 4) {
                    sequence.setLength(sequence.length() - 4);
                }

                resultArea.setText(
                    "SYSTEM STATUS: SAFE\n\n" +
                    "Safe Sequence:\n" + sequence
                );

            } else {

                resultArea.setText(
                    "SYSTEM STATUS: UNSAFE\n\n" +
                    "No complete safe sequence exists."
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                this, "Enter valid integer resource values."
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
