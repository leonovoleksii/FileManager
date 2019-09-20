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
    private MainPanel mainPanel;

    private String rename(String oldName, String newName) {
        System.out.println(oldName + " " + newName);
        File file = new File(oldName);
        file.renameTo(new File(newName));
        return "Renamed " + oldName + " to " + newName;
    }

    private void showError(String message) {
        System.out.println("Error");
        JDialog errorDialog = new JDialog(FileManagerFrame.getInstance(), "ERROR");
        errorDialog.setLayout(new FlowLayout());
        JButton okButton = new JButton("OK");
        okButton.addActionListener((ActionEvent e) -> {
            errorDialog.setVisible(false);
        });
        errorDialog.add(new JLabel(message));
        errorDialog.add(okButton);
        errorDialog.setSize(300, 200);
        errorDialog.setLocationRelativeTo(mainPanel);
        errorDialog.setVisible(true);
        protocolCreator.appendToProtocol(message, ProtocolCreator.ERROR);
    }

    public void execute(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        if (mainPanel.getActiveDirectory().equals(mainPanel.getSelectedFile())) {
            showError("You have to select file!");
            return;
        }                    // mainPanel.refreshSelectedFile(side, null);
        if (mainPanel.getActiveDirectory().equals(mainPanel.getSelectedFile())) {
            showError("Cannot operate on \"..\"!");
            return;
        }
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
                       rename(mainPanel.getSelectedFile(),
                               mainPanel.getActiveDirectory() + "/" + textField.getText()),
                       ProtocolCreator.CHANGES);
               mainPanel.refreshSidePanels();
               mainPanel.refreshSelectedFile("same", mainPanel.getActiveDirectory());
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
                            rename(mainPanel.getSelectedFile(),
                                    mainPanel.getActiveDirectory() + "/" + textField.getText()),
                            ProtocolCreator.CHANGES);
                    mainPanel.refreshSidePanels();
                    frame.setVisible(false);
                    mainPanel.refreshSelectedFile("same", mainPanel.getActiveDirectory());
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
