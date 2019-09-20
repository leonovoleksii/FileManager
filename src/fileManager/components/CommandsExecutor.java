package fileManager.components;

import fileManager.commands.Command;
import fileManager.commands.CreateDirectoryCommand;
import fileManager.commands.CreateFileCommand;
import fileManager.commands.RenameCommand;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;


public class CommandsExecutor {
    private TreeMap<Integer, Command> keyCodeToCommand;
    private MainPanel mainPanel;

    private void addCommand(Integer keyCode, Command command) {
        keyCodeToCommand.put(keyCode, command);
    }

    public CommandsExecutor(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        keyCodeToCommand = new TreeMap<>();
        addCommand(KeyEvent.VK_F9, new RenameCommand());
        addCommand(KeyEvent.VK_F8, new CreateDirectoryCommand());
        addCommand(KeyEvent.VK_F7, new CreateFileCommand());
    }

/*    public boolean processCommand(Integer keyCode, SidePanel sidePanel, String activeFile) {
        System.out.println(keyCode);
        if (!keyCodeToCommand.containsKey(keyCode)) return false;
        if (activeFile == null) return false;
        File file = new File(sidePanel.getActiveDirectory() + "/" + activeFile);
        Command command = keyCodeToCommand.get(keyCode);
        command.execute(mainPanel, sidePanel.getActiveDirectory(), activeFile);
        // protocolCreator.appendToProtocol(command.execute(sidePanel, file));
        return true;
    }*/

    public ArrayList<String> getCommands() {
        ArrayList<String> commands = new ArrayList<>();
        for (Integer keyCode : keyCodeToCommand.keySet()) {
            commands.add(KeyEvent.getKeyText(keyCode) + " " + keyCodeToCommand.get(keyCode));
        }
        return commands;
    }
}
