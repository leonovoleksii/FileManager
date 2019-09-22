package fileManager.commands;

import fileManager.FileManagerFrame;
import fileManager.components.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class FindCommand implements Command {
    private JList<String> list;
    private DefaultListModel<String> model;
    private JDialog dialog;

    private void findFile(String currentDir, String name) {
        File file = new File(currentDir);
        if (file.listFiles() != null)
            for (File f : file.listFiles()) {
                System.out.println(f.getName());
                if (f.getName().equals(name)) {
                    model.addElement(f.getAbsolutePath());
                    list.updateUI();
                }
                if (f.isDirectory() && f.canRead() && !f.getName().startsWith(".")) {
                    findFile(f.getAbsolutePath(), name);
                }
            }
    }

    public void execute(MainPanel mainPanel) {

        dialog = new JDialog(FileManagerFrame.getInstance());
        dialog.setMinimumSize(new Dimension(500, 500));
        dialog.setLocationRelativeTo(mainPanel);
        GridBagLayout gridBagLayout = new GridBagLayout();
        dialog.setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        model = new DefaultListModel<>();
        list = new JList<>(model);
        JScrollPane pane = new JScrollPane(list);
        gridBagLayout.setConstraints(pane, constraints);
        dialog.add(pane);
        dialog.setVisible(true);

        String filename = JOptionPane.showInputDialog("Choose which file you want to find");
        findFile(System.getProperty("user.home"), filename);


        if (model.getSize() == 0) {
            JOptionPane.showMessageDialog(mainPanel, "File not found");
            dialog.setVisible(false);
            return;
        }

        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    mainPanel.openDirectoryWithFile(list.getSelectedValue());
                    dialog.setVisible(false);
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    @Override
    public String toString() {
        return "Find";
    }
}
