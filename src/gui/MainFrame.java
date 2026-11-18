
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

        
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new SchedulingPanel(), "CPU Scheduling");

        contentPanel.add(new MemoryPanel(), "Memory Allocation");

        contentPanel.add(new DeadlockPanel(), "Deadlock Detection");
        
        contentPanel.add(new ThreadPanel(), "Multithreading");

        

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "Dashboard");
    }

    private void addPage(String name, String heading) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel(heading, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        panel.add(title, BorderLayout.CENTER);

        contentPanel.add(panel, name);
    }
}
