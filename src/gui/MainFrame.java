
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

        // Sidebar
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

        // Main Modules
        contentPanel.add(new SchedulingPanel(), "CPU Scheduling");
        contentPanel.add(new MemoryPanel(), "Memory Allocation");
        contentPanel.add(new DeadlockPanel(), "Deadlock Detection");
        contentPanel.add(new ThreadPanel(), "Multithreading");

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "Dashboard");
    }

    private JPanel createDashboard() {

        JPanel panel = new JPanel(new BorderLayout(0, 25));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 35, 30, 35));

        // Welcome Banner
        JPanel banner = new JPanel();
        banner.setLayout(new BoxLayout(banner, BoxLayout.Y_AXIS));
        banner.setBackground(new Color(35, 45, 70));
        banner.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        banner.setPreferredSize(new Dimension(800, 135));

        JLabel title = new JLabel("Welcome to Mini OS Simulator");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 27));

        JLabel description = new JLabel(
            "Explore and visualize fundamental Operating System concepts."
        );
        description.setForeground(new Color(210, 220, 235));
        description.setFont(new Font("Arial", Font.PLAIN, 15));

        banner.add(title);
        banner.add(Box.createVerticalStrut(12));
        banner.add(description);

        panel.add(banner, BorderLayout.NORTH);

        // Modules Section
        JPanel moduleSection = new JPanel(new BorderLayout(0, 18));
        moduleSection.setBackground(new Color(245, 247, 250));

        JLabel moduleHeading = new JLabel("Modules Overview");
        moduleHeading.setFont(new Font("Arial", Font.BOLD, 21));
        moduleHeading.setForeground(new Color(35, 45, 70));

        moduleSection.add(moduleHeading, BorderLayout.NORTH);

        // Module Cards
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 18, 18));
        cardsPanel.setBackground(new Color(245, 247, 250));

        String[] modules = {
            "CPU Scheduling",
            "Memory Allocation",
            "Deadlock Detection",
            "Multithreading"
        };

        String[] descriptions = {
            "Simulate FCFS, SJF and Round Robin scheduling algorithms.",
            "Explore First Fit, Best Fit and Worst Fit allocation strategies.",
            "Check system safety using Banker's Algorithm.",
            "Explore Producer-Consumer synchronization using Java threads."
        };

        Color[] accents = {
            new Color(65, 120, 210),
            new Color(35, 155, 125),
            new Color(210, 145, 55),
            new Color(145, 100, 195)
        };

        for (int i = 0; i < modules.length; i++) {

            JPanel card = new JPanel(new BorderLayout(12, 0));
            card.setBackground(Color.WHITE);

            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(225, 230, 238)
                ),
                BorderFactory.createEmptyBorder(20, 18, 20, 18)
            ));

            JPanel accent = new JPanel();
            accent.setBackground(accents[i]);
            accent.setPreferredSize(new Dimension(5, 10));

            JPanel textPanel = new JPanel();
            textPanel.setLayout(
                new BoxLayout(textPanel, BoxLayout.Y_AXIS)
            );
            textPanel.setBackground(Color.WHITE);

            JLabel moduleTitle = new JLabel(modules[i]);
            moduleTitle.setFont(new Font("Arial", Font.BOLD, 18));
            moduleTitle.setForeground(new Color(35, 45, 70));

            JLabel moduleDescription = new JLabel(
                "<html><div style='width:260px;'>"
                + descriptions[i] + "</div></html>"
            );
            moduleDescription.setFont(
                new Font("Arial", Font.PLAIN, 13)
            );
            moduleDescription.setForeground(
                new Color(100, 110, 125)
            );

            textPanel.add(moduleTitle);
            textPanel.add(Box.createVerticalStrut(12));
            textPanel.add(moduleDescription);

            card.add(accent, BorderLayout.WEST);
            card.add(textPanel, BorderLayout.CENTER);

            cardsPanel.add(card);
        }

        moduleSection.add(cardsPanel, BorderLayout.CENTER);

        panel.add(moduleSection, BorderLayout.CENTER);

        return panel;
    }
}
