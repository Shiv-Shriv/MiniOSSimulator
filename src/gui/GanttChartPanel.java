package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import scheduler.GanttChartEntry;

public class GanttChartPanel extends JPanel {

    private List<GanttChartEntry> entries;

    public GanttChartPanel(List<GanttChartEntry> entries) {
        this.entries = entries;
        setPreferredSize(new Dimension(700, 150));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (entries == null || entries.isEmpty()) {
            return;
        }

        int x = 30;
        int y = 40;
        int width = 60;
        int height = 40;

        g.setColor(Color.BLACK);
        g.drawString("CPU Gantt Chart", 30, 20);

        for (GanttChartEntry entry : entries) {

            g.setColor(new Color(100, 160, 220));
            g.fillRect(x, y, width, height);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);

            g.drawString("P" + entry.getProcessId(), x + 20, y + 25);
            g.drawString(String.valueOf(entry.getStartTime()), x, y + 60);

            x += width;
        }

        GanttChartEntry last = entries.get(entries.size() - 1);
        g.drawString(String.valueOf(last.getEndTime()), x, y + 60);
    }
}