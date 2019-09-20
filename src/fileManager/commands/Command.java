package fileManager.commands;

import fileManager.components.MainPanel;
import fileManager.components.SidePanel;

import java.io.File;

public interface Command {
    // returns the result of command in format
    // dd-MM-yyyy HH:mm:ss MESSAGE
    void execute(MainPanel mainPanel);
}
