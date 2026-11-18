
package gui;

import javax.swing.*;
import java.awt.*;
import deadlock.*;

public class DeadlockPanel extends JPanel {

    private JTextArea allocationArea;
    private JTextArea maxArea;
    private JTextField availableField;
    private JTextArea resultArea;

    public DeadlockPanel() {

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("Deadlock Detection - Banker's Algorithm");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        allocationArea = new JTextArea(
            "0,1,0\n2,0,0\n3,0,2\n2,1,1\n0,0,2", 8, 15
        );

        maxArea = new JTextArea(
            "7,5,3\n3,2,2\n9,0,2\n2,2,2\n4,3,3", 8, 15
        );

        availableField = new JTextField("3,3,2");

        inputPanel.add(createInputBox("Allocation Matrix", allocationArea));
        inputPanel.add(createInputBox("Maximum Matrix", maxArea));

        JPanel availablePanel = new JPanel(new BorderLayout(5, 5));
        availablePanel.add(new JLabel("Available Resources"), BorderLayout.NORTH);
        availablePanel.add(availableField, BorderLayout.CENTER);

        inputPanel.add(availablePanel);

        JButton checkButton = new JButton("Check Safe State");

        resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(checkButton, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(inputPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        checkButton.addActionListener(e -> checkDeadlock());
    }

    private JPanel createInputBox(String title, JTextArea area) {

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        panel.add(new JLabel(title), BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);

        return panel;
    }

    private int[][] parseMatrix(String input) {

        String[] rows = input.trim().split("\\n");
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

                sequence.setLength(sequence.length() - 4);

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
