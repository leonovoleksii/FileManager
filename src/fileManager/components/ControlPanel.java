package fileManager.components;

import fileManager.commands.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ControlPanel extends JPanel {
    ArrayList<Command> commands = new ArrayList<>();

    private void addCommandButton(MainPanel mainPanel, Command command) {
        try {
            JButton commandButton = new JButton(command.toString());
            commandButton.addActionListener((ActionEvent e) -> {
                command.execute(mainPanel);
            });
            commandButton.setFocusable(false);
            add(commandButton);
        } catch (Exception e) {
            System.err.println("Unable to add JButton to ControlPanel object");
        }

    }

    public ControlPanel(MainPanel mainPanel) {
        commands.add(new RenameCommand());
        commands.add(new CreateFileCommand());
        commands.add(new CreateDirectoryCommand());
        commands.add(new DeleteCommand());
        commands.add(new FindCommand());
        commands.add(new MoveCommand());


        for (Command command : commands) {
            addCommandButton(mainPanel, command);
        }
    }
}
