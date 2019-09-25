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
        commands.add(MoveCommand.class);


        for (Class command : commands) {
            addCommandButton(mainPanel, command);
        }
    }
}
