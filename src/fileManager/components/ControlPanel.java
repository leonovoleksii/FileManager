package fileManager.components;

import fileManager.commands.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ControlPanel extends JPanel {
    ArrayList<Class> commands = new ArrayList<>();

    private void addCommandButton(MainPanel mainPanel, Class<Command> command) {
        try {
            Command commandInstance = command.newInstance();
            JButton commandButton = new JButton(commandInstance.toString());
            commandButton.addActionListener((ActionEvent e) -> {
                commandInstance.execute(mainPanel);
            });
            commandButton.setFocusable(false);
            add(commandButton);
        } catch (Exception e) {
            System.err.println("Unable to add JButton to ControlPanel object");
        }

    }

    public ControlPanel(MainPanel mainPanel) {
        commands.add(RenameCommand.class);
        commands.add(CreateFileCommand.class);
        commands.add(CreateDirectoryCommand.class);
        commands.add(DeleteCommand.class);
        commands.add(FindCommand.class);


        for (Class command : commands) {
            addCommandButton(mainPanel, command);
        }
        /*JButton renameButton = new JButton("Rename");
        renameButton.addActionListener((ActionEvent e) -> {
            Command command = new RenameCommand();
            command.execute(mainPanel);
        });
        add(renameButton);
        JButton createFileButton = new JButton("Create File");
        createFileButton.addActionListener((ActionEvent e) -> {
            Command command = new CreateFileCommand();
            command.execute(mainPanel);
        });
        add(createFileButton);
        JButton createDirectoryButton = new JButton("Create Directory");
        createDirectoryButton.addActionListener((ActionEvent e) -> {
            Command command = new CreateDirectoryCommand();
            command.execute(mainPanel);
        });
        add(createDirectoryButton);
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener((ActionEvent e) -> {
            Command command = new DeleteCommand();
            command.execute(mainPanel);
        });
        add(deleteButton);*/
    }
}
