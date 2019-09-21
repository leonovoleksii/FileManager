package fileManager.commands;

import fileManager.components.MainPanel;
import fileManager.components.ProtocolCreator;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class DeleteCommand implements Command {
    private ProtocolCreator protocolCreator = ProtocolCreator.getInstance();

    private boolean delete(String fileName) {
        FileSystem fs = FileSystems.getDefault();

        try {
            Files.delete(fs.getPath(fileName));
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }

        return true;
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
