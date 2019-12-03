package databaseParser.form;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MyWindowListener implements WindowListener {

    private JFrame frame;

    public MyWindowListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        int sure = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?",
                "Closing dialogue", JOptionPane.YES_NO_OPTION);
        if (sure == 0) {
            frame.setVisible(false);
            frame.dispose();
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
}
