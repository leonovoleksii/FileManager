package fileManager.commands;

import fileManager.FileManagerFrame;
import fileManager.components.MainPanel;
import fileManager.components.ProtocolCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class RenameCommand implements Command {
    private JTextField textField;
    private JButton submitButton;
    private ProtocolCreator protocolCreator = ProtocolCreator.getInstance();

    private String rename(String oldName, String newName) {
        File file = new File(oldName);
        file.renameTo(new File(newName));
        return "Renamed " + oldName + " to " + newName;
    }

    public void execute(MainPanel mainPanel, String currentDirectory, String activeFile) {
        JDialog frame = new JDialog(FileManagerFrame.getInstance());
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        textField = new JTextField();
        submitButton = new JButton("Ok");
        frame.setLayout(new FlowLayout());
        textField.setColumns(15);
        frame.add(textField);
        frame.add(submitButton);
        submitButton.addActionListener(
            (ActionEvent actionEvent) -> {
               protocolCreator.appendToProtocol(
                       rename(currentDirectory + "/" + activeFile,
                               currentDirectory + "/" + textField.getText()),
                       ProtocolCreator.CHANGES);
               mainPanel.refreshSidePanels();
               frame.setVisible(false);
            }
        );
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == keyEvent.VK_ENTER) {
                    protocolCreator.appendToProtocol(
                            rename(currentDirectory + "/" + activeFile,
                                    currentDirectory + "/" + textField.getText()),
                            ProtocolCreator.CHANGES);
                    mainPanel.refreshSidePanels();
                    frame.setVisible(false);
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
        frame.setVisible(true);
    }

    public String toString() {
        return "Rename";
    }
}
