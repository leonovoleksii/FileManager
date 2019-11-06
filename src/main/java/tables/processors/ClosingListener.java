package tables.processors;

import tables.visualizers.TableFrame;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ClosingListener implements WindowListener {
    private TableFrame tableFrame;
    private Saver saver = new Saver();

    public ClosingListener(TableFrame tableFrame) {
        this.tableFrame = tableFrame;
    }
    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(tableFrame.getFilename()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
                result.append("\n");
            }
        } catch (IOException e) {
            System.err.println("Unable to open the file");
        }
        if (saver.generateFileContent(tableFrame).equals(result.toString())) {
            return;
        }
        int closing = JOptionPane.showConfirmDialog(windowEvent.getComponent(),
                "Do you want to save the file?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
        if (closing == 0) {
            saver.save(tableFrame);
            windowEvent.getComponent().setVisible(false);
        } else if (closing == 1) {
            windowEvent.getComponent().setVisible(false);
        }
        tableFrame.removeFilename();
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
