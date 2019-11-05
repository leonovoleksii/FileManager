package fileManager.components;

import tables.visualizers.TableFrame;
import textEditor.TextEditorFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class MainPanel extends JPanel {
    private static final boolean LEFT = true, RIGHT = false;
    private SidePanel leftPanel, rightPanel;
    private ControlPanel controlPanel;
    private String selectedFile = System.getProperty("user.home");
    private boolean side = LEFT;

    private void init() {
        leftPanel = new SidePanel(this, "left");
        rightPanel = new SidePanel(this, "right");
        controlPanel = new ControlPanel(this);

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
        gridBagLayout.setConstraints(controlPanel, gridBagConstraints);
        add(controlPanel);

        setFocusable(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_F3 && selectedFile != null) {
                    File file = new File(selectedFile);
                    if (!file.isDirectory()) {
                        TextEditorFrame.newInstance(selectedFile);
                    }
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_F2) {
                    File file = new File(selectedFile);
                    openTables(file);
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

    }

    private void openTables(File file) {
        if (!file.getName().endsWith(".myxsl")) {
            JOptionPane.showMessageDialog(this, "The file extension must be \".myxsl\"!", "Error",  JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            TableFrame tableFrame = new TableFrame(file.getAbsolutePath());
            tableFrame.setSize(1000, 600);
            tableFrame.setLocationRelativeTo(this);
            tableFrame.setVisible(true);
        }
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

    public void setActiveDirectory(String dir) {
        if (side == RIGHT) {
            rightPanel.setActiveDirectory(dir);
        } else {
            leftPanel.setActiveDirectory(dir);
        }
    }

    public void openDirectoryWithFile(String file) {
        File tempFile = new File(file);
        if (side == RIGHT) {
            rightPanel.openDirectoryWithFile(tempFile.getParentFile().getAbsolutePath(), file);
        } else {
            leftPanel.openDirectoryWithFile(tempFile.getParentFile().getAbsolutePath(), file);
        }
        ProtocolCreator.getInstance().appendToProtocol("Moved to " + tempFile.getAbsolutePath(), ProtocolCreator.TRANSITION);
        selectedFile = file;
    }

}
