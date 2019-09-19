package fileManager.commands;

import fileManager.FileManagerFrame;
import fileManager.components.MainPanel;
import fileManager.components.ProtocolCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class CreateDirectoryCommand implements Command {
    private ProtocolCreator protocolCreator;
    public CreateDirectoryCommand() {
        this.protocolCreator = ProtocolCreator.getInstance();
    }
    private void createDir(JTextField dirNameTextField, String currentDirectory) {
        String dirName = dirNameTextField.getText();
        File file = new File(currentDirectory + "/" + dirName);
        if (file.mkdir()) {
            protocolCreator.appendToProtocol("Created directory " + dirName + " in " + currentDirectory,
                    ProtocolCreator.CHANGES);
        } else {
            protocolCreator.appendToProtocol("Unable to create " + dirName + " in " + currentDirectory,
                    ProtocolCreator.ERROR);
        }
    }
    public void execute(MainPanel mainPanel, String currentDirectory, String activeFile) {
        JDialog frame = new JDialog(FileManagerFrame.getInstance());
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());
        JButton submitButton = new JButton("Ok");
        JTextField dirNameTextField = new JTextField();
        dirNameTextField.setColumns(15);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createDir(dirNameTextField, currentDirectory);
                mainPanel.refreshSidePanels();
                frame.setVisible(false);
            }
        });
        dirNameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    createDir(dirNameTextField, currentDirectory);
                    mainPanel.refreshSidePanels();
                    frame.setVisible(false);
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
        frame.add(dirNameTextField);
        frame.add(submitButton);
        frame.setVisible(true);
    }

    public String toString() {
        return "Mkdir";
    }
}
