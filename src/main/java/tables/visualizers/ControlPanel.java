package tables.visualizers;

import tables.processors.Checker;
import tables.processors.Evaluator;
import tables.processors.Replacer;

import javax.script.ScriptException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.TreeMap;
import java.util.TreeSet;

public class ControlPanel extends JPanel {
    private JLabel selectedCellLabel = new JLabel();

    private JTextField formulaField = new JTextField(30);

    private JButton addRow = new JButton("Add row");
    private JButton addColumn = new JButton("Add column");
    private JButton calculate = new JButton("Calculate");
    private JButton deleteRow = new JButton("Delete row");

    private JButton deleteColumn = new JButton("Delete column");
    private Table table;

    private TreeMap<String, String> nameToFormula = new TreeMap<>();
    private TreeMap<String, Object> nameToValue = new TreeMap<>();

    public TreeMap<String, String> getNameToFormula() {
        return nameToFormula;
    }

    public ControlPanel(Table table) {
        this.table = table;
        init();
    }

    public ControlPanel(Table table, TreeMap<String, String> nameToFormula) {
        this.table = table;
        this.nameToFormula = nameToFormula;
        init();
        recalculate();
    }

    private void init() {
        addRow.addActionListener((ActionEvent e) -> {
            table.addRow();
            setSelectedCell("");
        });


        deleteRow.addActionListener((ActionEvent e) -> {
            int row = table.removeRow();
            for (int i = 1; i <= table.getColumnAmount(); i++) {
                String cellName = table.getColumnName(i) + row;
                nameToValue.remove(cellName);
                nameToFormula.remove(cellName);
            }
            setSelectedCell("");
            recalculate();
        });


        addColumn.addActionListener((ActionEvent e) -> {
            table.addColumn();
            setSelectedCell("");
        });


        deleteColumn.addActionListener((ActionEvent e) -> {
            String columnName = table.getColumnName(table.removeColumn());
            for (int i = 1; i <= table.getRowAmount(); i++) {
                String cellName = columnName + i;
                nameToFormula.remove(cellName);
                nameToValue.remove(cellName);
            }
            recalculate();
            setSelectedCell("");
        });


        calculate.addActionListener((ActionEvent e) -> {
            if (selectedCellLabel.getText().equals("")) {
                JOptionPane.showMessageDialog(table, "Select cell first");
            } else {
                if (formulaField.getText().equals("")) {
                    nameToFormula.remove(selectedCellLabel.getText());
                    table.clearCell(selectedCellLabel.getText());
                    System.err.println("cleared cell");
                } else {
                    nameToFormula.put(selectedCellLabel.getText(), formulaField.getText());
                }
                recalculate();
            }
        });


        add(selectedCellLabel);
        add(formulaField);
        add(calculate);
        add(addRow);
        add(addColumn);
        add(deleteRow);
        add(deleteColumn);
        table.setControlPanel(this);
    }
    private void recalculate() {
        int calculated = 0, prev = -1;
        nameToValue = new TreeMap<>();
        TreeSet<String> evaluated = new TreeSet<>();
        while (calculated != prev) {
            prev = calculated;
            for (String n : nameToFormula.keySet()) {
                if (!Checker.check(nameToFormula.get(n))) continue;
                String formula = Replacer.process(nameToFormula.get(n), nameToValue);
                if (!nameToValue.containsKey(n)) {
                    try {
                        nameToValue.put(n, Evaluator.evaluate(formula));
                    } catch (ScriptException se) {
                        continue;
                    }
                    calculated++;
                    evaluated.add(n);
                }
            }
        }
        for (String n : nameToFormula.keySet()) {
            if (!evaluated.contains(n)) {
                nameToValue.put(n, Table.ERROR);
            }
        }
        table.setValues(nameToValue);
    }
    public void setSelectedCell(String name) {
        selectedCellLabel.setText(name);
        String formula = nameToFormula.get(name);
        if (formula != null) {
            formulaField.setText(formula);
        } else {
            formulaField.setText("");
        }
    }
}
