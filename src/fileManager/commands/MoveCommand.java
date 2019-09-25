package fileManager.commands;

import fileManager.components.MainPanel;

import javax.swing.*;
import java.io.File;

public class MoveCommand implements Command {
    public void execute(MainPanel mainPanel) {
        File tempFile = new File(mainPanel.getSelectedFile());
        String input = JOptionPane.showInputDialog(mainPanel, "Move " + mainPanel.getSelectedFile() + " to:",
                mainPanel.getActiveDirectory());
        if (tempFile.renameTo(new File(input + "/" + tempFile.getName()))) {
            mainPanel.refreshSidePanels();
            mainPanel.refreshSelectedFile("same", mainPanel.getActiveDirectory());
            JOptionPane.showMessageDialog(mainPanel, "File moved successfully");
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Unable to move the file");
        }
    }

    public String toString() {
        return "Move";
    }
}
