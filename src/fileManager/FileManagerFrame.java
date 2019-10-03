package fileManager;

import fileManager.components.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        help = new JMenuItem("help");
        help.addActionListener(this);
        helpMenu.add(help);

        setMinimumSize(new Dimension(1000, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == help) {
            JTextArea helpArea = new JTextArea();
            helpArea.setText("asdfasdfadfg");
            helpArea.setEditable(false);
            JOptionPane.showMessageDialog(this, helpArea, "Help", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new FileManagerFrame());
    }
}
