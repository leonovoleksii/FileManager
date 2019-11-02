package tables.visualizers;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.activation.ActivationID;
import java.util.Vector;

public class Table extends JScrollPane implements TableModelListener {
    private final int DEFAULT_COLUMN_AMOUNT = 10;
    private final int DEFAULT_ROW_AMOUNT = 15;

    private int columnAmount = DEFAULT_COLUMN_AMOUNT;
    private int rowAmount = DEFAULT_ROW_AMOUNT;

    private Vector<String> columnNames = new Vector<>();
    private Vector<Vector<String>> data = new Vector<>();

    private JTable mainTable;
    private JTable rowNumberTable;

    private String getColumnName(int index) {
        final int base = 'Z' - 'A' + 1;

        StringBuilder result = new StringBuilder();
        do {
            index--;
            result.append((char)((int)'A' + index % base));
            index /= base;
        } while (index > 0);
        return result.reverse().toString();
    }

    public Table() {
        for (int i = 1; i <= DEFAULT_COLUMN_AMOUNT; i++) {
            columnNames.add(getColumnName(i));
        }
        for (int i = 0; i < DEFAULT_ROW_AMOUNT; i++) {
            data.add(new Vector<>(DEFAULT_COLUMN_AMOUNT));
        }
        mainTable = new JTable(data, columnNames);
        mainTable.setRowSelectionAllowed(false);
        rowNumberTable = new RowNumberTable(mainTable);
        rowNumberTable.setRowSelectionAllowed(false);

        getViewport().add(mainTable);

        this.setRowHeaderView(rowNumberTable);
        setCorner(JScrollPane.UPPER_LEFT_CORNER, rowNumberTable.getTableHeader());

        mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public void addRow() {
        Vector<String> newRow = new Vector<>(columnAmount);
        DefaultTableModel model = (DefaultTableModel)mainTable.getModel();
        model.addRow(newRow);
        rowAmount++;
    }

    public void addColumn() {
        DefaultTableModel model = (DefaultTableModel)mainTable.getModel();
        model.addColumn(getColumnName(++columnAmount));
    }

    @Override
    public void tableChanged(TableModelEvent tableModelEvent) {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setLayout(gridBagLayout);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Table table = new Table();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1;
        constraints.weightx = 1;
        gridBagLayout.setConstraints(table, constraints);
        panel.add(table);
        JButton button = new JButton("Add row");
        frame.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                table.addRow();
                System.out.println("Added new row!");
            }
        });
        constraints.gridy = 1;
        constraints.weighty = 0.25;
        gridBagLayout.setConstraints(button, constraints);
        panel.add(button);
        JButton button2 = new JButton("Add row");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                table.addColumn();
                System.out.println("Added new column!");
            }
        });
        constraints.gridy = 2;
        gridBagLayout.setConstraints(button2, constraints);
        panel.add(button2);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }

}
