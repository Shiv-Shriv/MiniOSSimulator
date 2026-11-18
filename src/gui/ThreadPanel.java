
package gui;

import javax.swing.*;
import java.awt.*;
import threads.*;

public class ThreadPanel extends JPanel {

    private JTextArea outputArea;
    private JButton startButton;

    private JTextField itemField;
    private JTextField capacityField;
    private JTextField producerDelayField;
    private JTextField consumerDelayField;

    public ThreadPanel() {

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("Producer-Consumer Simulation");
        title.setFont(new Font("Arial", Font.BOLD, 25));

        add(title, BorderLayout.NORTH);

        JPanel settings = new JPanel(new FlowLayout(FlowLayout.LEFT));

        itemField = new JTextField("10", 5);
        capacityField = new JTextField("5", 5);
        producerDelayField = new JTextField("300", 5);
        consumerDelayField = new JTextField("500", 5);

        settings.add(new JLabel("Items:"));
        settings.add(itemField);

        settings.add(new JLabel("Buffer Capacity:"));
        settings.add(capacityField);

        settings.add(new JLabel("Producer Delay (ms):"));
        settings.add(producerDelayField);

        settings.add(new JLabel("Consumer Delay (ms):"));
        settings.add(consumerDelayField);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        startButton = new JButton("Start Simulation");
        JButton resetButton = new JButton("Reset");

        JPanel controls = new JPanel();
        controls.add(startButton);
        controls.add(resetButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(settings, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);

        startButton.addActionListener(e -> startSimulation());

        resetButton.addActionListener(e -> outputArea.setText(""));
    }

    private void startSimulation() {

        try {

            int items = Integer.parseInt(itemField.getText());
            int capacity = Integer.parseInt(capacityField.getText());
            int producerDelay = Integer.parseInt(producerDelayField.getText());
            int consumerDelay = Integer.parseInt(consumerDelayField.getText());

            if (items <= 0 || capacity <= 0 ||
                producerDelay < 0 || consumerDelay < 0) {
                throw new IllegalArgumentException("Invalid settings.");
            }

            startButton.setEnabled(false);
            outputArea.setText("Simulation started...\n");

            SharedBuffer buffer = new SharedBuffer(
                capacity,
                message -> SwingUtilities.invokeLater(() -> {
                    outputArea.append(message + "\n");
                    outputArea.setCaretPosition(
                        outputArea.getDocument().getLength()
                    );
                })
            );

            Thread producer = new Thread(
                new Producer(buffer, items, producerDelay)
            );

            Thread consumer = new Thread(
                new Consumer(buffer, items, consumerDelay)
            );

            producer.start();
            consumer.start();

            Thread monitor = new Thread(() -> {

                try {
                    producer.join();
                    consumer.join();

                    SwingUtilities.invokeLater(() -> {
                        outputArea.append("\nSimulation completed!\n");
                        startButton.setEnabled(true);
                    });

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            });

            monitor.start();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this,
                "Enter valid integer values.");

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
