package textEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TreeMap;

public class TextEditorFrame extends JFrame implements ActionListener {
    private JTextArea textArea;
    private TextAreaController controller;
    private JMenuItem saveItem, removeAttributes, replace, capitalize;

    private TextEditorFrame(String filename) {
        setSize(1000, 500);
        setLocationRelativeTo(null);
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        gridBagLayout.setConstraints(scrollPane, constraints);
        add(scrollPane);

        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        menu.add(file);
        menu.add(edit);
        setJMenuBar(menu);

        saveItem = new JMenuItem("Save file");
        saveItem.addActionListener(this);
        file.add(saveItem);

        removeAttributes = new JMenuItem("Remove html attributes");
        removeAttributes.addActionListener(this);
        edit.add(removeAttributes);

        replace = new JMenuItem("Replace");
        replace.addActionListener(this);
        edit.add(replace);

        capitalize = new JMenuItem("Capitalize");
        capitalize.addActionListener(this);
        edit.add(capitalize);

        setTitle(filename);
        controller = new TextAreaController(textArea, filename);
        controller.readFile();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                int closing = JOptionPane.showConfirmDialog(windowEvent.getComponent(),
                        "Do you want to save the file?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
                if (closing == 0) {
                    controller.save();
                    windowEvent.getComponent().setVisible(false);
                } else if (closing == 1) {
                    windowEvent.getComponent().setVisible(false);
                }
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

        setVisible(true);
        requestFocus();
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == saveItem) {
            controller.save();
        } else if (actionEvent.getSource() == removeAttributes) {
            controller.simplify();
        } else if (actionEvent.getSource() == replace) {
            JTextField oldSequence = new JTextField(), newSequence = new JTextField();
            Object[] message = {"Replace", oldSequence, "To", newSequence};
            int option = JOptionPane.showConfirmDialog(this, message, "Replace", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                controller.replace(oldSequence.getText(), newSequence.getText());
            }
        } else if (actionEvent.getSource() == capitalize) {
            controller.capitalize(textArea.getText());
        }
    }

    private static TreeMap<String, TextEditorFrame> filenameToFrame = new TreeMap<>();

    public static TextEditorFrame newInstance(String filename) {
        if (filenameToFrame.get(filename) != null) {
            System.out.println("Found!");
            TextEditorFrame temp = filenameToFrame.get(filename);
            if (!temp.isVisible()) {
                filenameToFrame.remove(filename);
            } else {
                temp.toFront();
                temp.requestFocus();
                return filenameToFrame.get(filename);
            }
        }
        TextEditorFrame temp = new TextEditorFrame(filename);
        filenameToFrame.put(filename, temp);
        return temp;
    }
}
