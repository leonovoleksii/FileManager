package databaseParser.form;

import databaseParser.searchers.SearchInitializer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ParserFrame extends JFrame {
    private JTextArea textArea = new JTextArea();
    private JPanel panel;

    public ParserFrame(File f) {
        setTitle(f.getName());
        SearchInitializer searchInitializer = new SearchInitializer();
        searchInitializer.search(f);
        panel = new FormPanel(f, textArea, searchInitializer.getSpecialities(), searchInitializer.getGroups());
        addWindowListener(new MyWindowListener(this));
        init();
    }

    private void init() {
        setMinimumSize(new Dimension(1000, 800));

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1;
        JScrollPane scrollPane = new JScrollPane(textArea);
        gridBagLayout.setConstraints(scrollPane, gridBagConstraints);
        add(scrollPane);


        gridBagConstraints.gridx = 1;
        gridBagLayout.setConstraints(panel, gridBagConstraints);
        add(panel);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

}
