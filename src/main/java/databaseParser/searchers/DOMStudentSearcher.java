package databaseParser.searchers;

import databaseParser.model.Student;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import databaseParser.model.StudentBuilder;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DOMStudentSearcher implements StudentSearcher {
    @Override
    public List<Student> search(File file, Student student) {
        List<Student> students = new ArrayList<>();
        Document document;
        try {
          document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        } catch (ParserConfigurationException e) {
            System.err.println("Unable to create DocumentBuilder");
            return null;
        } catch (IOException | SAXException e) {
            System.err.println("Unable to parse the file " + file.getAbsolutePath());
            return null;
        }
        NodeList studentNodes = document.getElementsByTagName("student");
        for (int i = 0; i < studentNodes.getLength(); i++) {
            Element studentElement = (Element)studentNodes.item(i);
            Element groupElement = (Element)studentElement.getParentNode();
            Element specialtyElement = (Element)groupElement.getParentNode();

            StudentBuilder studentBuilder = new StudentBuilder();

            studentBuilder.setSpecialty(specialtyElement.getAttribute("NAME"));
            studentBuilder.setGroupID(groupElement.getAttribute("ID"));

            NodeList studentChildNodes = studentElement.getChildNodes();
            for (int j = 0; j < studentChildNodes.getLength(); j++) {
                Node childNode = studentChildNodes.item(j);
                switch (childNode.getNodeName()) {
                    case "name":
                        studentBuilder.setName(childNode.getTextContent());
                        break;
                    case "surname":
                        studentBuilder.setSurname(childNode.getTextContent());
                        break;
                    case "phone":
                        studentBuilder.setPhoneNumber(childNode.getTextContent());
                        break;
                    case "city":
                        studentBuilder.setCity(childNode.getTextContent());
                        break;
                }
            }

            Student currentStudent = studentBuilder.build();
            if (currentStudent.similarTo(student)) {
                students.add(currentStudent);
            }
        }

        return students;
    }

    public static void main(String[] args) {
        StudentSearcher studentSearcher = new DOMStudentSearcher();

        for (Student student : studentSearcher.search(new File("students.xml"),
                new Student("Computer Science", "K-25", "Mykyta", "Sadok",  "123", "Zaporizhzhia")))
            System.out.println(student.toString());
    }
}
