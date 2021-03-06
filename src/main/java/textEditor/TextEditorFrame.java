package textEditor;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.TreeMap;

public class TextEditorFrame extends JFrame implements ActionListener {
    private JTextComponent textComponent;
    private JScrollPane scrollPane;
    private TextAreaController controller;
    private JMenuItem saveItem, removeAttributes, replace, capitalize, help;
    private String oldVersionOfFile;

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
        textComponent = new JEditorPane();
        scrollPane = new JScrollPane(textComponent);
        gridBagLayout.setConstraints(scrollPane, constraints);
        add(scrollPane);

        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu helpMenu = new JMenu("Help");
        menu.add(file);
        menu.add(edit);
        menu.add(helpMenu);
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

        help = new JMenuItem("About");
        help.addActionListener(this);
        helpMenu.add(help);


        setTitle(filename);
        controller = new TextAreaController(textComponent, filename);
        controller.readFile();
        oldVersionOfFile = textComponent.getText();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (oldVersionOfFile.equals(textComponent.getText())) {
                    windowEvent.getComponent().setVisible(false);
                    return;
                }
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
            oldVersionOfFile = textComponent.getText();
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
            controller.capitalize(textComponent.getText());
        } else if (actionEvent.getSource() == help) {
            JTextArea helpArea = new JTextArea();
            String text = "File > Save - saves the file\n" +
                    "Edit > Remove html attributes - removes all attributes from html code except 'src' and 'href'\n" +
                    "Edit > Replace - replaces one sequence in the text with another\n" +
                    "Edit > Capitalize - capitalizes first characters in the sentences";
            helpArea.setText(text);
            helpArea.setEditable(false);
            JOptionPane.showMessageDialog(this, helpArea, "Help", JOptionPane.PLAIN_MESSAGE);
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
