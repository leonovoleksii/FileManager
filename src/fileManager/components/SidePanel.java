package fileManager.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

public class SidePanel extends JPanel {
    private JList<String> directoryList, fileList;
    private String activeDirectory;
    private JTextField activeDirectoryField;
    private ProtocolCreator protocolCreator;

    public SidePanel(MainPanel mainPanel, String side) {
        protocolCreator = ProtocolCreator.getInstance();

        activeDirectory = System.getProperty("user.home");
        activeDirectoryField = new JTextField(activeDirectory);
        activeDirectoryField.setEditable(false);

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 0.03;
        gridBagLayout.setConstraints(activeDirectoryField, constraints);
        add(activeDirectoryField);

        JSplitPane splitPane = new JSplitPane();
        directoryList = new JList<>();
        directoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList = new JList<>();
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        splitPane.setResizeWeight(0.25);
        splitPane.setLeftComponent(new JScrollPane(directoryList));
        splitPane.setRightComponent(new JScrollPane(fileList));
        constraints.gridy = 1;
        constraints.weighty = 1;
        gridBagLayout.setConstraints(splitPane, constraints);
        add(splitPane);

        directoryList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                fileList.setSelectedValue(null, false);
                String selectedValue = directoryList.getSelectedValue();

                if (selectedValue.equals("..")) {
                    File f = new File(activeDirectory);
                    selectedValue = f.getParent();
                } else {
                    selectedValue = activeDirectory + (!activeDirectory.equals("/") ? "/" : "")
                                + selectedValue;
                }

                mainPanel.refreshSelectedFile(side, selectedValue);

                if (mouseEvent.getClickCount() == 2) {
                    protocolCreator.appendToProtocol("Moved to " + selectedValue, ProtocolCreator.TRANSITION);
                    activeDirectory = selectedValue;
                    refresh();
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

        fileList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                directoryList.setSelectedValue(null, false);
                mainPanel.refreshSelectedFile(side, activeDirectory + "/" + fileList.getSelectedValue());
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

        refresh();
    }

    public void refreshSelectedValues() {
        directoryList.setSelectedValue(null, false);
        fileList.setSelectedValue(null, false);
    }

    public void refresh(){
        File dir = new File(activeDirectory);
        activeDirectoryField.setText(activeDirectory);
        ArrayList<String> dirs = new ArrayList<>(), files = new ArrayList<>();
        if (!activeDirectory.equals("/")) dirs.add("..");
        for (File f : dir.listFiles()) {
            if (f.getName().startsWith(".") || !f.canRead()) continue;
            if (f.isFile()) {
                files.add(f.getName());
            } else if (f.isDirectory()){
                dirs.add(f.getName());
            }
        }
        dirs.sort(String.CASE_INSENSITIVE_ORDER);
        files.sort(String.CASE_INSENSITIVE_ORDER);

        String[] typeArr = new String[1];
        this.directoryList.setListData(dirs.toArray(typeArr));
        typeArr = new String[1];
        this.fileList.setListData(files.toArray(typeArr));
        System.out.println("REFRESHED");
    }

    public String getActiveDirectory() {
        return activeDirectory;
    }
}
