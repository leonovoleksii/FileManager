package textEditor;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TreeMap;

public class TextEditorFrame extends JFrame {
    private String filename;
    private JTextArea textArea;
    private TextAreaController controller;

    private TextEditorFrame(String filename) {
        this.filename = filename;
        setSize(500, 300);
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
        gridBagLayout.setConstraints(menu, constraints);
        menu.add(file);
        setJMenuBar(menu);

        JMenuItem saveItem = new JMenuItem("Save file");
        saveItem.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                controller.save();
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
        file.add(saveItem);

        JMenuItem removeAttributes = new JMenuItem("Remove html attributes");
        removeAttributes.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                System.out.println(mouseEvent);
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
        file.add(removeAttributes);

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
