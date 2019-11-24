package databaseParser.form;

import databaseParser.model.Student;
import databaseParser.model.StudentBuilder;
import databaseParser.searchers.DOMStudentSearcher;
import databaseParser.searchers.SAXStudentSearcher;
import databaseParser.searchers.StudentSearcher;

import javax.swing.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FormPanel extends JPanel {

    private JComboBox<String> specialities, groupIDs, typeSearch = new JComboBox<>(new String[]{"DOM", "SAX"});
    private JTextField name, surname, phone, city;
    private JButton clearButton = new JButton("Clear"), searchButton = new JButton("Search"),
                    generateHTMLButton = new JButton("Generate HTML");
    private List<Student> students = new ArrayList<>();


    public FormPanel(File file, JTextArea textArea, Collection<String> specialitiesSet, Collection<String> groupIDsSet) {
        specialities = new JComboBox<>(specialitiesSet.toArray(new String[0]));
        groupIDs = new JComboBox<>(groupIDsSet.toArray(new String[0]));
        name = new JTextField();
        surname = new JTextField();
        phone = new JTextField();
        city = new JTextField();

        clearButton.addActionListener((ActionEvent e) -> {
            name.setText(null);
            surname.setText(null);
            phone.setText(null);
            city.setText(null);
            textArea.setText(null);
            specialities.setSelectedIndex(0);
            groupIDs.setSelectedIndex(0);
            typeSearch.setSelectedIndex(0);
        });

        searchButton.addActionListener((ActionEvent e) -> {
            textArea.setText("");

            StudentBuilder studentBuilder = new StudentBuilder();
            studentBuilder.setSpecialty((specialities.getSelectedItem()).equals("Not selected") ? null :
                    (String)specialities.getSelectedItem());
            studentBuilder.setGroupID((groupIDs.getSelectedItem()).equals("Not selected") ? null :
                    (String)groupIDs.getSelectedItem());
            studentBuilder.setName(name.getText().equals("") ? null : name.getText());
            studentBuilder.setSurname(surname.getText().equals("") ? null : surname.getText());
            studentBuilder.setPhoneNumber(phone.getText().equals("") ? null : phone.getText());
            studentBuilder.setCity(city.getText().equals("") ? null : city.getText());

            StudentSearcher studentSearcher;
            if (typeSearch.getSelectedItem().equals("DOM")) {
                studentSearcher = new DOMStudentSearcher();
            } else {
                studentSearcher = new SAXStudentSearcher();
            }

            students = studentSearcher.search(file, studentBuilder.build());

            for (Student s : students) {
                textArea.append(s.toString() + "\n");
            }
        });

        generateHTMLButton.addActionListener((ActionEvent e) -> {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Source xslDoc = new StreamSource("/home/oleksii/java/FileManager/src/main/resources/students.xsl");
            Source xmlDoc = new StreamSource(file);
            String outputFilename = file.getParent() + "/report.html";
            try {
                OutputStream htmlFile = new FileOutputStream(outputFilename);
                Transformer transformer = transformerFactory.newTransformer(xslDoc);
                transformer.transform(xmlDoc, new StreamResult(htmlFile));
            } catch (Exception exc) {
                System.err.println("Unable to generate HTML file!");
            }
        });

        init();
    }

    public List<Student> getStudents() {
        return students;
    }

    private void init() {
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.weightx = 0.3;

        ArrayList<String> labelNames = new ArrayList<>();
        labelNames.add("Specialty"); labelNames.add("Group");
        labelNames.add("Name"); labelNames.add("Surname");
        labelNames.add("Phone"); labelNames.add("City");
        labelNames.add("Search type");

        int y = 0;
        for (String labelName : labelNames) {
            addLabel(gridBagConstraints, labelName, layout, y);
            y++;
        }

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.7;
        layout.setConstraints(specialities, gridBagConstraints);
        add(specialities);

        gridBagConstraints.gridy = 1;
        layout.setConstraints(groupIDs, gridBagConstraints);
        add(groupIDs);

        gridBagConstraints.gridy = 2;
        layout.setConstraints(name, gridBagConstraints);
        add(name);

        gridBagConstraints.gridy = 3;
        layout.setConstraints(surname, gridBagConstraints);
        add(surname);

        gridBagConstraints.gridy = 4;
        layout.setConstraints(phone, gridBagConstraints);
        add(phone);

        gridBagConstraints.gridy = 5;
        layout.setConstraints(city, gridBagConstraints);
        add(city);

        gridBagConstraints.gridy = 6;
        layout.setConstraints(typeSearch, gridBagConstraints);
        add(typeSearch);

        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weightx = 0.3;
        layout.setConstraints(clearButton, gridBagConstraints);
        add(clearButton);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0.7;
        layout.setConstraints(searchButton, gridBagConstraints);
        add(searchButton);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        layout.setConstraints(generateHTMLButton, gridBagConstraints);
        add(generateHTMLButton);

    }

    private void addLabel(GridBagConstraints gridBagConstraints, String labelName, GridBagLayout layout, int y) {
        gridBagConstraints.gridy = y;
        JLabel label = new JLabel(labelName);
        layout.setConstraints(label, gridBagConstraints);
        add(label);
    }
}
