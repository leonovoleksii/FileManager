package fileManager.commands;

import fileManager.components.MainPanel;
import fileManager.components.ProtocolCreator;

import javax.swing.*;
import java.io.File;

public class RenameCommand implements Command {
    private ProtocolCreator protocolCreator = ProtocolCreator.getInstance();
    private MainPanel mainPanel;

    private String rename(String oldName, String newName) {
        File file = new File(oldName);
        File newFile = new File(newName);
        if (newFile.exists()) {
            int input = JOptionPane.showConfirmDialog(mainPanel, newFile.getAbsolutePath() + " already exists." +
                    "Do you want to overwrite it?", "Overwrite", JOptionPane.YES_NO_CANCEL_OPTION);
            if (input == 0) {
                if (file.renameTo(newFile)) {
                    mainPanel.refreshSelectedFile("same", newFile.toString());
                    return file.getAbsolutePath() + " was overwritten to " + newFile.getAbsolutePath();
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Unable to rename " + file.getAbsolutePath() + " to " +
                            newFile.getAbsolutePath());
                    return "Unable to rename " + file.getAbsolutePath() + " to " +
                            newFile.getAbsolutePath();
                }
            } else {
                return "Refused to overwrite " + file.getAbsolutePath() + " to " + newFile.getAbsolutePath();
            }
        }
        file.renameTo(newFile);
        mainPanel.refreshSelectedFile("same", newFile.getName());
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
        mainPanel.openDirectoryWithFile(mainPanel.getActiveDirectory() + "/" + fileName);
    }

    public String toString() {
        return "Rename";
    }
}
