package fileManager;

import fileManager.components.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FileManagerFrame extends JFrame implements Runnable, ActionListener {
    private static final FileManagerFrame INSTANCE = new FileManagerFrame();

    private JMenuItem help;

    public static FileManagerFrame getInstance() {
        return INSTANCE;
    }

    public void run() {
        setContentPane(new MainPanel());

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        help = new JMenuItem("About");
        help.addActionListener(this);
        helpMenu.add(help);

        setMinimumSize(new Dimension(1000, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == help) {
            JTextArea text = new JTextArea("This is the program that helps manipulate files on your computer\n" +
                    "This file manager can perform simple operations like creating, renaming, moving, deleting\n" +
                    "and finding files and directories\n\n" +
                    "In order to create, delete, rename or move the file or directory, choose the item in one\n" +
                    "of the lists and press the corresponding button in the bottom\n\n" +
                    "Developed by Oleksii Leonov");
            text.setEditable(false);
            JOptionPane.showMessageDialog(this, text, "Help", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new FileManagerFrame());
    }
}
