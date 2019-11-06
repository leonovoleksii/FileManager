package tables.visualizers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.TreeMap;

public class TableFrame extends JFrame implements Serializable {
    private Table table;
    private ControlPanel controlPanel;
    private String filename;
    private static TreeMap<String, TableFrame> filenameToTableFrame = new TreeMap<>();

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
        saveFileMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                save();
            }
        });
        fileMenu.add(saveFileMenu);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                int closing = JOptionPane.showConfirmDialog(windowEvent.getComponent(),
                        "Do you want to save the file?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
                if (closing == 0) {
                    save();
                    windowEvent.getComponent().setVisible(false);
                } else if (closing == 1) {
                    windowEvent.getComponent().setVisible(false);
                }
                TableFrame.filenameToTableFrame.remove(filename);
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });

        TableFrame.filenameToTableFrame.put(filename, this);
    }

    private void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))) {
            bufferedWriter.write(table.getRowAmount() + "\n");
            bufferedWriter.write(table.getColumnAmount() + "\n");
            TreeMap<String, String> nameToFormula = controlPanel.getNameToFormula();
            bufferedWriter.write(nameToFormula.size() + "\n");
            for (String formula : nameToFormula.keySet()) {
                bufferedWriter.write(formula + "\n");
                bufferedWriter.write(nameToFormula.get(formula) + "\n");
            }
            bufferedWriter.flush();
        } catch (Exception e) {

        }
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
