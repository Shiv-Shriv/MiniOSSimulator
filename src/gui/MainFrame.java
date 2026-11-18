package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Mini OS Simulator");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(30, 35, 50));
        sidebar.setPreferredSize(new Dimension(220, 700));
        sidebar.setLayout(new GridLayout(6, 1, 5, 5));

        JLabel heading = new JLabel("  MINI OS SIMULATOR");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Arial", Font.BOLD, 17));

        sidebar.add(heading);

        // Main content
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(245, 247, 250));

        JLabel welcome = new JLabel(
            "Welcome to Mini OS Simulator",
            SwingConstants.CENTER
        );

        welcome.setFont(new Font("Arial", Font.BOLD, 28));

        content.add(welcome, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);
    }
}