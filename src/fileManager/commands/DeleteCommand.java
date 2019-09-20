package fileManager.commands;

import fileManager.FileManagerFrame;
import fileManager.components.MainPanel;
import fileManager.components.ProtocolCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class DeleteCommand implements Command {
    private MainPanel mainPanel;
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
        this.mainPanel = mainPanel;
        JDialog confirmDialog = new JDialog(FileManagerFrame.getInstance());
        confirmDialog.setSize(300, 200);
        confirmDialog.setLocationRelativeTo(mainPanel);
        confirmDialog.setLayout(new FlowLayout());

        File tempFile = new File(mainPanel.getActiveDirectory());
        if (mainPanel.getSelectedFile().equals(mainPanel.getActiveDirectory()) ||
            tempFile.getParent().equals(mainPanel.getSelectedFile())) {

            confirmDialog.add(new JLabel(tempFile.getParent().equals(mainPanel.getSelectedFile()) ?
                    "Cannot delete \"..\"" :"You have to choose file for deletion"));
            JButton submitButton = new JButton("OK");
            submitButton.addActionListener((ActionEvent e) -> {
                confirmDialog.setVisible(false);
            });
            confirmDialog.add(submitButton);
            confirmDialog.setVisible(true);
            return;
        }

        JLabel label = new JLabel("Are you sure?");
        confirmDialog.add(label);
        JButton confirmButton = new JButton("Yes");
        JButton refuseButton = new JButton("No");

        refuseButton.addActionListener((ActionEvent e) -> {
            confirmDialog.setVisible(false);
        });
        confirmButton.addActionListener((ActionEvent e) -> {
            if (!delete(mainPanel.getSelectedFile())) {
                protocolCreator.appendToProtocol("Unable to delete " + mainPanel.getSelectedFile(),
                        ProtocolCreator.ERROR);
                label.setText("Unable to delete " + mainPanel.getSelectedFile());
                refuseButton.setText("OK");
                confirmDialog.remove(confirmButton);
                confirmDialog.repaint();

            } else {
                protocolCreator.appendToProtocol("Deleted " + mainPanel.getSelectedFile(),
                        ProtocolCreator.CHANGES);
                confirmDialog.setVisible(false);
                mainPanel.refreshSidePanels();
                mainPanel.refreshSelectedFile("same", mainPanel.getActiveDirectory());
            }
        });

        confirmDialog.add(confirmButton);
        confirmDialog.add(refuseButton);
        confirmDialog.setVisible(true);
    }

    public String toString() {
        return "Delete";
    }
}
