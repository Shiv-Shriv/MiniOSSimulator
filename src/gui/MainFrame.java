
package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainFrame() {

        setTitle("Mini OS Simulator");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(30, 35, 50));
        sidebar.setPreferredSize(new Dimension(230, 700));
        sidebar.setLayout(new GridLayout(6, 1, 5, 5));

        JLabel heading = new JLabel("  MINI OS SIMULATOR");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Arial", Font.BOLD, 17));

        sidebar.add(heading);

        String[] sections = {
            "Dashboard",
            "CPU Scheduling",
            "Memory Allocation",
            "Deadlock Detection",
            "Multithreading"
        };

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        for (String section : sections) {

            JButton button = new JButton(section);
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(45, 52, 72));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 14));

            button.addActionListener(e ->
                cardLayout.show(contentPanel, section)
            );

            sidebar.add(button);
        }

        // Dashboard
        contentPanel.add(createDashboard(), "Dashboard");

        // Main modules
        contentPanel.add(new SchedulingPanel(), "CPU Scheduling");
        contentPanel.add(new MemoryPanel(), "Memory Allocation");
        contentPanel.add(new DeadlockPanel(), "Deadlock Detection");
        contentPanel.add(new ThreadPanel(), "Multithreading");

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "Dashboard");
    }

    private JPanel createDashboard() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));

        JLabel title = new JLabel("Welcome to Mini OS Simulator");
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel description = new JLabel(
            "A Java-based simulation of fundamental Operating System concepts."
        );
        description.setFont(new Font("Arial", Font.PLAIN, 15));

        panel.add(title);
        panel.add(Box.createVerticalStrut(15));
        panel.add(description);
        panel.add(Box.createVerticalStrut(40));

        String[] modules = {
            "CPU Scheduling",
            "Memory Allocation",
            "Deadlock Detection",
            "Multithreading"
        };

        String[] descriptions = {
            "Simulate FCFS, SJF and Round Robin scheduling algorithms.",
            "Explore First Fit, Best Fit and Worst Fit memory allocation.",
            "Check system safety using Banker's Algorithm.",
            "Explore Producer-Consumer synchronization using Java threads."
        };

        for (int i = 0; i < modules.length; i++) {

            JLabel moduleTitle = new JLabel(modules[i]);
            moduleTitle.setFont(new Font("Arial", Font.BOLD, 18));

            JLabel moduleDescription = new JLabel(descriptions[i]);
            moduleDescription.setFont(new Font("Arial", Font.PLAIN, 14));

            panel.add(moduleTitle);
            panel.add(Box.createVerticalStrut(5));
            panel.add(moduleDescription);
            panel.add(Box.createVerticalStrut(30));
        }

        return panel;
    }
}
