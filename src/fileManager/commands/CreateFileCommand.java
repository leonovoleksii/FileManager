package fileManager.commands;

import fileManager.components.MainPanel;
import fileManager.components.ProtocolCreator;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class CreateFileCommand implements Command {
    private ProtocolCreator protocolCreator;
    private MainPanel mainPanel;

    public CreateFileCommand() {
        this.protocolCreator = ProtocolCreator.getInstance();
    }
    private void createFile(String filename, String currentDirectory) {
        File file = new File(currentDirectory + "/" + filename);
        try {
            if (file.createNewFile()) {
                protocolCreator.appendToProtocol("Created " + filename + " in " + currentDirectory,
                        ProtocolCreator.CHANGES);
            } else {
                String message = "Unable to create \"" + filename + "\" in " + currentDirectory;
                protocolCreator.appendToProtocol(message, ProtocolCreator.ERROR);
                JOptionPane.showMessageDialog(mainPanel, message, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            String message = "Unable to create \"" + filename + "\" in " + currentDirectory;
            protocolCreator.appendToProtocol(message, ProtocolCreator.ERROR);
            JOptionPane.showMessageDialog(mainPanel, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void execute(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        String fileName = JOptionPane.showInputDialog(mainPanel, "Choose the filename", "Filename",
                JOptionPane.INFORMATION_MESSAGE);
        if (fileName != null) {
            createFile(fileName, mainPanel.getActiveDirectory());
            mainPanel.refreshSidePanels();
        }
    }

    public String toString() {
        return "Create File";
    }
}
