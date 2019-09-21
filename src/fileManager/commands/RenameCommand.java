package fileManager.commands;

import fileManager.components.MainPanel;
import fileManager.components.ProtocolCreator;

import javax.swing.*;
import java.io.File;

public class RenameCommand implements Command {
    private ProtocolCreator protocolCreator = ProtocolCreator.getInstance();
    private MainPanel mainPanel;

    private String rename(String oldName, String newName) {
        System.out.println(oldName + " " + newName);
        File file = new File(oldName);
        file.renameTo(new File(newName));
        return "Renamed " + oldName + " to " + newName;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(mainPanel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void execute(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        if (mainPanel.getActiveDirectory().equals(mainPanel.getSelectedFile())) {
            showError("You have to select file!");
            return;
        }
        if (mainPanel.getActiveDirectory().equals(mainPanel.getSelectedFile())) {
            showError("Cannot operate on \"..\"!");
            return;
        }
        String fileName = "";
        while (fileName != null && fileName.equals("")) {
            fileName = JOptionPane.showInputDialog(mainPanel, "Choose new name for " + mainPanel.getSelectedFile());
            if (fileName != null && fileName.equals("")) {
                JOptionPane.showMessageDialog(mainPanel, "You need to enter the new name of the file", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        if (fileName == null) return;

        protocolCreator.appendToProtocol(
                rename(mainPanel.getSelectedFile(),
                        mainPanel.getActiveDirectory() + "/" + fileName),
                ProtocolCreator.CHANGES);
        mainPanel.refreshSidePanels();
        mainPanel.refreshSelectedFile("same", mainPanel.getActiveDirectory());
    }

    public String toString() {
        return "Rename";
    }
}
