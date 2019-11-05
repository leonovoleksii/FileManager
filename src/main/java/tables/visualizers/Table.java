package tables.visualizers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TreeMap;
import java.util.Vector;

public class Table extends JScrollPane {
    private final int DEFAULT_COLUMN_AMOUNT = 10;
    private final int DEFAULT_ROW_AMOUNT = 15;

    private int columnAmount = DEFAULT_COLUMN_AMOUNT;
    private int rowAmount = DEFAULT_ROW_AMOUNT;

    public static final Object ERROR = new Object();

    private Vector<String> columnNames = new Vector<>();
    private Vector<Vector<String>> data = new Vector<>();

    private JTable mainTable;
    private JTable rowNumberTable;
    private ControlPanel controlPanel;

    String getColumnName(int index) {
        final int base = 'Z' - 'A' + 1;

        StringBuilder result = new StringBuilder();
        do {
            index--;
            result.append((char)((int)'A' + index % base));
            index /= base;
        } while (index > 0);
        return result.reverse().toString();
    }

    private void init() {
        for (int i = 1; i <= columnAmount; i++) {
            columnNames.add(getColumnName(i));
        }
        for (int i = 0; i < rowAmount; i++) {
            data.add(new Vector<>(columnAmount));
        }
        mainTable = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        mainTable.setRowSelectionAllowed(false);
        rowNumberTable = new RowNumberTable(mainTable);
        rowNumberTable.setRowSelectionAllowed(false);

        getViewport().add(mainTable);

        this.setRowHeaderView(rowNumberTable);
        setCorner(JScrollPane.UPPER_LEFT_CORNER, rowNumberTable.getTableHeader());

        mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        mainTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                controlPanel.setSelectedCell(getColumnName(mainTable.getSelectedColumn() + 1) + (mainTable.getSelectedRow() + 1));
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        mainTable.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (mainTable.getSelectedRow() < rowAmount - 1) {
                        controlPanel.setSelectedCell(getColumnName(mainTable.getSelectedColumn() + 1) + (mainTable.getSelectedRow() + 2));
                    }
                }
                else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                    if (mainTable.getSelectedRow() > 0) {
                        controlPanel.setSelectedCell(getColumnName(mainTable.getSelectedColumn() + 1) + (mainTable.getSelectedRow()));
                    }
                }
                else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (mainTable.getSelectedColumn() > 0) {
                        controlPanel.setSelectedCell(getColumnName(mainTable.getSelectedColumn()) + (mainTable.getSelectedRow() + 1));
                    }
                }
                else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (mainTable.getSelectedColumn() < columnAmount - 1) {
                        controlPanel.setSelectedCell(getColumnName(mainTable.getSelectedColumn() + 2) + (mainTable.getSelectedRow() + 1));
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }

    public Table(int rowAmount, int columnAmount) {
        this.rowAmount = rowAmount;
        this.columnAmount = columnAmount;
        init();
    }

    public Table() {
        init();
    }

    public void addRow() {
        Vector<String> newRow = new Vector<>(columnAmount);
        DefaultTableModel model = (DefaultTableModel)mainTable.getModel();
        model.addRow(newRow);
        rowAmount++;
    }

    public int removeRow() {
        if (rowAmount > 0)
            ((DefaultTableModel) mainTable.getModel()).setRowCount(--rowAmount);
        return rowAmount + 1;
    }

    public void addColumn() {
        DefaultTableModel model = (DefaultTableModel) mainTable.getModel();
        model.addColumn(getColumnName(++columnAmount));
    }

    public int removeColumn() {
        if (columnAmount > 0)
            ((DefaultTableModel) mainTable.getModel()).setColumnCount(--columnAmount);
        return columnAmount + 1;
    }


    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    public void setValues(TreeMap<String, Object> nameToValue) {
        for (String name : nameToValue.keySet()) {
            Object value = nameToValue.get(name);
            if (value == ERROR) {
                mainTable.setValueAt("#Error", getRowFromName(name), getColumnFromName(name));
            } else {
                mainTable.setValueAt(value, getRowFromName(name), getColumnFromName(name));
            }
        }
    }

    int getRowFromName(String name) {
        int inx = 0;
        while (!Character.isDigit(name.charAt(inx))) {
            inx++;
        }
        return Integer.parseInt(name.substring(inx)) - 1;
    }

    private int getColumnFromName(String name) {
        int inx = 0;
        while (!Character.isDigit(name.charAt(inx))) {
            inx++;
        }
        name = name.substring(0, inx);
        int res = 0;
        int base = (int)'Z' - (int)'A' + 1;
        int k = 1;
        for (int i = name.length() - 1; i >= 0; i--) {
            res += ((int)name.charAt(i) - (int)'A' + 1) * k;
            k *= base;
        }
        return res - 1;
    }

    public int getColumnAmount() {
        return columnAmount;
    }

    public int getRowAmount() {
        return rowAmount;
    }
}
