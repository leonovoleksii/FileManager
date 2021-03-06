package fileManager.commands;

import fileManager.components.MainPanel;
import fileManager.components.ProtocolCreator;

import javax.swing.*;
import java.io.File;

public class MoveCommand implements Command {
    public void execute(MainPanel mainPanel) {
        ProtocolCreator protocolCreator = ProtocolCreator.getInstance();
        File tempFile = new File(mainPanel.getSelectedFile());
        if (tempFile.getAbsolutePath().equals(mainPanel.getActiveDirectory())) {
            JOptionPane.showMessageDialog(mainPanel, "You have to select file you want to move!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String input = JOptionPane.showInputDialog(mainPanel, "Move " + mainPanel.getSelectedFile() + " to:",
                mainPanel.getActiveDirectory());
        if (tempFile.renameTo(new File(input + "/" + tempFile.getName()))) {
            protocolCreator.appendToProtocol("Moved " + mainPanel.getSelectedFile() + " to " + tempFile.getAbsolutePath(),
                    ProtocolCreator.CHANGES);
            mainPanel.refreshSidePanels();
            mainPanel.refreshSelectedFile("same", mainPanel.getActiveDirectory());
            JOptionPane.showMessageDialog(mainPanel, "File moved successfully");
        } else if (input != null) {
            protocolCreator.appendToProtocol("Unable to move " + mainPanel.getSelectedFile() + " to " + tempFile.getAbsolutePath(),
                    ProtocolCreator.ERROR);
            JOptionPane.showMessageDialog(mainPanel, "Unable to move the file");
        }
    }

    public String toString() {
        return "Move";
    }
}
