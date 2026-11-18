
package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import model.MemoryBlock;
import memory.*;

public class MemoryPanel extends JPanel {

    private JTextField blockIdField, blockSizeField, processSizeField;

    private JComboBox<String> algorithmBox;

    private DefaultTableModel blockModel;
    private DefaultTableModel allocationModel;

    private List<MemoryBlock> blocks = new ArrayList<>();

    public MemoryPanel() {

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("Memory Allocation");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        add(title, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        blockIdField = new JTextField(4);
        blockSizeField = new JTextField(5);
        processSizeField = new JTextField(5);

        inputPanel.add(new JLabel("Block ID:"));
        inputPanel.add(blockIdField);

        inputPanel.add(new JLabel("Block Size:"));
        inputPanel.add(blockSizeField);

        JButton addBlockButton = new JButton("Add Block");
        inputPanel.add(addBlockButton);

        inputPanel.add(new JLabel("Process Size:"));
        inputPanel.add(processSizeField);

        algorithmBox = new JComboBox<>(
            new String[]{"First Fit", "Best Fit", "Worst Fit"}
        );

        inputPanel.add(algorithmBox);

        JButton allocateButton = new JButton("Allocate");
        inputPanel.add(allocateButton);

        JButton resetButton = new JButton("Reset");
        inputPanel.add(resetButton);

        // Memory block table
        blockModel = new DefaultTableModel(
            new String[]{"Block ID", "Total Size", "Remaining Size"}, 0
        );

        JTable blockTable = new JTable(blockModel);

        // Allocation history table
        allocationModel = new DefaultTableModel(
            new String[]{"Process Size", "Algorithm", "Allocated Block"}, 0
        );

        JTable allocationTable = new JTable(allocationModel);

        JPanel tablesPanel = new JPanel(new GridLayout(2, 1, 5, 10));

        JPanel blockPanel = new JPanel(new BorderLayout());
        blockPanel.add(new JLabel("  Memory Blocks"), BorderLayout.NORTH);
        blockPanel.add(new JScrollPane(blockTable));

        JPanel allocationPanel = new JPanel(new BorderLayout());
        allocationPanel.add(new JLabel("  Allocation History"), BorderLayout.NORTH);
        allocationPanel.add(new JScrollPane(allocationTable));

        tablesPanel.add(blockPanel);
        tablesPanel.add(allocationPanel);

        add(inputPanel, BorderLayout.NORTH);
        add(tablesPanel, BorderLayout.CENTER);

        // Add memory block
        addBlockButton.addActionListener(e -> addBlock());

        // Allocate process
        allocateButton.addActionListener(e -> allocateProcess());

        // Reset memory
        resetButton.addActionListener(e -> resetMemory());
    }

    private void addBlock() {

        try {

            int id = Integer.parseInt(blockIdField.getText());
            int size = Integer.parseInt(blockSizeField.getText());

            if (id <= 0 || size <= 0) {
                throw new IllegalArgumentException(
                    "Block ID and size must be positive."
                );
            }

            for (MemoryBlock block : blocks) {
                if (block.getBlockId() == id) {
                    throw new IllegalArgumentException(
                        "Block ID already exists."
                    );
                }
            }

            MemoryBlock block = new MemoryBlock(id, size);
            blocks.add(block);

            blockModel.addRow(new Object[]{id, size, size});

            blockIdField.setText("");
            blockSizeField.setText("");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                this, "Enter valid integer values."
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void allocateProcess() {

        try {

            int processSize = Integer.parseInt(
                processSizeField.getText()
            );

            if (processSize <= 0) {
                throw new IllegalArgumentException(
                    "Process size must be positive."
                );
            }

            if (blocks.isEmpty()) {
                throw new IllegalArgumentException(
                    "Add memory blocks first."
                );
            }

            String selected = (String) algorithmBox.getSelectedItem();

            MemoryAllocator allocator;

            switch (selected) {
                case "Best Fit":
                    allocator = new BestFit();
                    break;

                case "Worst Fit":
                    allocator = new WorstFit();
                    break;

                default:
                    allocator = new FirstFit();
            }

            int blockId = allocator.allocate(blocks, processSize);

            String allocationStatus;

            if (blockId != -1) {
                allocationStatus = "Block " + blockId;
            } else {
                allocationStatus = "Not Allocated";
            }

            allocationModel.addRow(new Object[]{
                processSize, selected, allocationStatus
            });

            refreshBlockTable();

            processSizeField.setText("");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                this, "Enter a valid process size."
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void refreshBlockTable() {

        blockModel.setRowCount(0);

        for (MemoryBlock block : blocks) {
            blockModel.addRow(new Object[]{
                block.getBlockId(),
                block.getSize(),
                block.getRemainingSize()
            });
        }
    }

    private void resetMemory() {

        blocks.clear();
        blockModel.setRowCount(0);
        allocationModel.setRowCount(0);

        processSizeField.setText("");
        blockIdField.setText("");
        blockSizeField.setText("");
    }
}
