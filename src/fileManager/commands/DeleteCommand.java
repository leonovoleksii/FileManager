package fileManager.commands;

import fileManager.components.MainPanel;
import fileManager.components.ProtocolCreator;

import javax.swing.*;
import java.io.File;

public class DeleteCommand implements Command {
    private ProtocolCreator protocolCreator = ProtocolCreator.getInstance();

    private boolean delete(String fileName) {
        File file = new File(fileName);

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                delete(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    public void execute(MainPanel mainPanel) {
        File tempFile = new File(mainPanel.getActiveDirectory());
        if (mainPanel.getSelectedFile().equals(mainPanel.getActiveDirectory()) ||
            tempFile.getParent().equals(mainPanel.getSelectedFile())) {

            JOptionPane.showMessageDialog(mainPanel, tempFile.getParent().equals(mainPanel.getSelectedFile()) ?
                    "Cannot delete \"..\"" :"You have to choose file for deletion", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int res = JOptionPane.showConfirmDialog(mainPanel, "Are you sure?", "Deletion", JOptionPane.YES_NO_OPTION);

        if (res == 0 && !delete(mainPanel.getSelectedFile())) {
            protocolCreator.appendToProtocol("Unable to delete " + mainPanel.getSelectedFile(),
                    ProtocolCreator.ERROR);
            JOptionPane.showMessageDialog(mainPanel, "Unable to delete file", "Error", JOptionPane.ERROR_MESSAGE);

        } else if (res == 0) {
            protocolCreator.appendToProtocol("Deleted " + mainPanel.getSelectedFile(),
                    ProtocolCreator.CHANGES);
            mainPanel.refreshSidePanels();
            mainPanel.refreshSelectedFile("same", mainPanel.getActiveDirectory());
        }

    }

    public String toString() {
        return "Delete";
    }
}
