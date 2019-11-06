package tables.visualizers;

import tables.processors.ClosingListener;
import tables.processors.Saver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.TreeMap;

public class TableFrame extends JFrame {
    private Table table;
    private ControlPanel controlPanel;
    private String filename;
    private static TreeMap<String, TableFrame> filenameToTableFrame = new TreeMap<>();
    private Saver saver = new Saver();

    public static TableFrame getTableFrame(String filename) {
        if (filenameToTableFrame.containsKey(filename)) {
            return filenameToTableFrame.get(filename);
        } else {
            TableFrame tableFrame = new TableFrame(filename);
            filenameToTableFrame.put(filename, tableFrame);
            return tableFrame;
        }
    }

    private TableFrame(String filename) {
        setTitle(filename);
        this.filename = filename;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            int rowAmount = Integer.parseInt(bufferedReader.readLine());
            int columnAmount = Integer.parseInt(bufferedReader.readLine());
            int formulaAmount = Integer.parseInt(bufferedReader.readLine());
            TreeMap<String, String> nameToFormula = new TreeMap<>();
            for (int i = 0; i < formulaAmount; i++) {
                String name = bufferedReader.readLine();
                String formula = bufferedReader.readLine();
                nameToFormula.put(name, formula);
            }
            table = new Table(rowAmount, columnAmount);
            controlPanel = new ControlPanel(table, nameToFormula);
            table.setControlPanel(controlPanel);
        } catch (Exception e) {
            System.err.println("Unable to read table");
            table = new Table();
            controlPanel = new ControlPanel(table);
            table.setControlPanel(controlPanel);
        }
        JPanel contentPane = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        contentPane.setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.85;
        constraints.weightx = 1;
        gridBagLayout.setConstraints(table, constraints);
        contentPane.add(table);
        constraints.weighty = 0.15;
        constraints.gridy = 1;
        gridBagLayout.setConstraints(controlPanel, constraints);
        contentPane.add(controlPanel);
        setContentPane(contentPane);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem saveFileMenu = new JMenuItem("Save file");
        saveFileMenu.addActionListener((ActionEvent e) -> {
            saver.save(this);
        });
        fileMenu.add(saveFileMenu);

        addWindowListener(new ClosingListener(this));

        TableFrame.filenameToTableFrame.put(filename, this);
    }

    public Table getTable() {
        return table;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public String getFilename() {
        return filename;
    }

    public void removeFilename() {
        filenameToTableFrame.remove(filename);
    }

    public static void main(String[] args) {
        JFrame frame = new TableFrame("/home/oleksii/test.test");
        frame.setMinimumSize(new Dimension(1000, 600));
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
