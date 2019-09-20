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
import java.io.IOException;

public class CreateFileCommand implements Command {
    private ProtocolCreator protocolCreator;
    public CreateFileCommand() {
        this.protocolCreator = ProtocolCreator.getInstance();
    }
    private void createFile(JTextField dirNameTextField, String currentDirectory) {
        String fileName = dirNameTextField.getText();
        File file = new File(currentDirectory + "/" + fileName);
        try {
            if (file.createNewFile()) {
                protocolCreator.appendToProtocol("Created " + fileName + " in " + currentDirectory,
                        ProtocolCreator.CHANGES);
            } else {
                protocolCreator.appendToProtocol("Unable to create " + fileName + " in " + currentDirectory,
                        ProtocolCreator.ERROR);
            }
        } catch (IOException e) {
            protocolCreator.appendToProtocol("Unable to create " + fileName + " in " + currentDirectory,
                    ProtocolCreator.ERROR);
        }
    }
    public void execute(MainPanel mainPanel) {
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
                createFile(dirNameTextField, mainPanel.getActiveDirectory());
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
                    createFile(dirNameTextField, mainPanel.getActiveDirectory());
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
        return "Create File";
    }
}
