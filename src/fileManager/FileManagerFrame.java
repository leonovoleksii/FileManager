package fileManager;

import fileManager.components.MainPanel;

import javax.swing.*;
import java.awt.*;

public class FileManagerFrame extends JFrame implements Runnable {
    private static final FileManagerFrame INSTANCE = new FileManagerFrame();

    public static FileManagerFrame getInstance() {
        return INSTANCE;
    }

    public void run() {
        setContentPane(new MainPanel());
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new FileManagerFrame());
    }
}
