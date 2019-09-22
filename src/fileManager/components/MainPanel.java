package fileManager.components;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainPanel extends JPanel {
    private static final boolean LEFT = true, RIGHT = false;
    private SidePanel leftPanel, rightPanel;
    private HintPanel hintPanel;
    private String selectedFile = System.getProperty("user.home");
    private boolean side = LEFT;

    private void init() {
        leftPanel = new SidePanel(this, "left");
        rightPanel = new SidePanel(this, "right");
        hintPanel = new HintPanel(this);

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

    public void refreshSelectedFile(String s, String file) {
        selectedFile = file;
        if (s.equals("left") || s.equals("same") && side == LEFT) {
            rightPanel.refreshSelectedValues();
            side = LEFT;
        } else if (s.equals("right") || s.equals("same") && side == RIGHT) {
            leftPanel.refreshSelectedValues();
            side = RIGHT;
        }
        System.out.println(selectedFile + " " + side);
    }

    public void refreshSidePanels() {
        leftPanel.refresh();
        rightPanel.refresh();
    }

    public String getSelectedFile() {
        return selectedFile;
    }

    public String getActiveDirectory() {
        return (side == RIGHT ? rightPanel.getActiveDirectory() : leftPanel.getActiveDirectory());
    }

    public void openDirectoryWithFile(String file) {
        File tempFile = new File(file);
        if (side == RIGHT) {
            rightPanel.openDirectoryWithFile(tempFile.getParentFile().getAbsolutePath(), file);
        } else {
            leftPanel.openDirectoryWithFile(tempFile.getParentFile().getAbsolutePath(), file);
        }
        selectedFile = file;
    }

}
