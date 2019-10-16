package fileManager.commands;

import fileManager.components.MainPanel;
import fileManager.components.ProtocolCreator;

import javax.swing.*;
import java.io.*;
import org.apache.commons.io.FileUtils;

public class CopyCommand implements Command {
    public void execute(MainPanel mainPanel) {
        ProtocolCreator protocolCreator = ProtocolCreator.getInstance();
        File tempFile = new File(mainPanel.getSelectedFile());
        if (tempFile.getAbsolutePath().equals(mainPanel.getActiveDirectory())) {
            JOptionPane.showMessageDialog(mainPanel, "You have to select file you want to copy!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String input = JOptionPane.showInputDialog(mainPanel, "Copy " + mainPanel.getSelectedFile() + " to:",
                mainPanel.getActiveDirectory());

        File newFile = new File(input);
        if (newFile.exists()) {
            protocolCreator.appendToProtocol("Unable to copy " + tempFile.getAbsolutePath() + " to " + newFile.getAbsolutePath(),
                    ProtocolCreator.ERROR);
            JOptionPane.showMessageDialog(mainPanel, "Unable to copy " + tempFile.getAbsolutePath() + " to " + newFile.getAbsolutePath(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tempFile.isFile()) {
            try {
                FileUtils.copyFile(tempFile, newFile);
                mainPanel.setActiveDirectory(newFile.getParent());
                mainPanel.refreshSelectedFile("same", newFile.getAbsolutePath());
            } catch (IOException e) {
                protocolCreator.appendToProtocol("Unable to copy " + tempFile.getAbsolutePath() + " to " + newFile.getAbsolutePath(),
                        ProtocolCreator.ERROR);
                JOptionPane.showMessageDialog(mainPanel, "Unable to copy " + tempFile.getAbsolutePath() + " to " + newFile.getAbsolutePath(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            try {
                FileUtils.copyDirectory(tempFile, newFile);
                mainPanel.refreshSelectedFile("same", newFile.getAbsolutePath());
            } catch (IOException e) {
                protocolCreator.appendToProtocol("Unable to copy " + tempFile.getAbsolutePath() + " to " + newFile.getAbsolutePath(),
                        ProtocolCreator.ERROR);
                JOptionPane.showMessageDialog(mainPanel, "Unable to copy " + tempFile.getAbsolutePath() + " to " + newFile.getAbsolutePath(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        mainPanel.refreshSidePanels();
    }

    public String toString() {
        return "Copy";
    }
}
