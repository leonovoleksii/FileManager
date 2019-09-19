package fileManager.components;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private SidePanel leftPanel, rightPanel;
    private HintPanel hintPanel;
    private CommandsExecutor commandsExecutor;

    private void init() {
        commandsExecutor = new CommandsExecutor(this);
        leftPanel = new SidePanel(this, "left");
        rightPanel = new SidePanel(this, "right");
        hintPanel = new HintPanel(commandsExecutor.getCommands());

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1;
        gridBagLayout.setConstraints(leftPanel, gridBagConstraints);
        add(leftPanel);

        gridBagConstraints.gridx = 1;
        gridBagLayout.setConstraints(rightPanel, gridBagConstraints);
        add(rightPanel);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.gridwidth = 2;
        gridBagLayout.setConstraints(hintPanel, gridBagConstraints);
        add(hintPanel);
    }

    public MainPanel() {
        init();
    }
    public void processCommand(int keyCode, SidePanel sidePanel, String activeFile) {
        commandsExecutor.processCommand(keyCode, sidePanel, activeFile);
    }

    public void refreshSelectedFiles(String s) {
        if (s.equals("left")) {
            rightPanel.refreshSelectedValues();
        } else if (s.equals("right")) {
            leftPanel.refreshSelectedValues();
        }
    }

    public void refreshSidePanels() {
        leftPanel.refresh();
        rightPanel.refresh();
    }

/*    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setMenuBar(new MenuBar());
        frame.setContentPane(new MainPanel());
        frame.setSize(1000, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }*/
}
