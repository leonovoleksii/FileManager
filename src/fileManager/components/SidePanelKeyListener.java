package fileManager.components;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SidePanelKeyListener implements KeyListener {
    private JList<String> list;
    private SidePanel sidePanel;
    private MainPanel mainPanel;

    public SidePanelKeyListener(MainPanel mainPanel, SidePanel sidePanel, JList<String> list) {
        this.list = list;
        this.sidePanel = sidePanel;
        this.mainPanel = mainPanel;
    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        mainPanel.processCommand(keyEvent.getKeyCode(), sidePanel, list.getSelectedValue());
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {}
    @Override
    public void keyReleased(KeyEvent keyEvent) {}
}
