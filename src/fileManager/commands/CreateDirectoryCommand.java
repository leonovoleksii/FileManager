package fileManager.commands;

import fileManager.components.MainPanel;
import fileManager.components.ProtocolCreator;

import javax.swing.*;
import java.io.File;

public class CreateDirectoryCommand implements Command {
    private ProtocolCreator protocolCreator;
    private MainPanel mainPanel;
    public CreateDirectoryCommand() {
        this.protocolCreator = ProtocolCreator.getInstance();
    }
    private void createDir(String dirName, String currentDirectory) {
        File file = new File(currentDirectory + "/" + dirName);
        if (file.mkdir()) {
            protocolCreator.appendToProtocol("Created " + dirName + " in " + currentDirectory,
                    ProtocolCreator.CHANGES);
        } else {
            String message = "Unable to create \"" + dirName + "\" in " + currentDirectory;
            protocolCreator.appendToProtocol(message, ProtocolCreator.ERROR);
            JOptionPane.showMessageDialog(mainPanel, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void execute(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        String dirName = JOptionPane.showInputDialog(mainPanel, "Choose the filename", "Filename",
                JOptionPane.INFORMATION_MESSAGE);
        if (dirName != null) {
            createDir(dirName, mainPanel.getActiveDirectory());
            mainPanel.refreshSidePanels();
            mainPanel.openDirectoryWithFile(mainPanel.getActiveDirectory() + "/" + dirName);
        }
    }

    public String toString() {
        return "Create Directory";
    }
}
