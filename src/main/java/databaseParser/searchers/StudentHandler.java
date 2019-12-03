package databaseParser.searchers;

import databaseParser.model.Student;
import databaseParser.model.StudentBuilder;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class StudentHandler extends DefaultHandler {
    private List<Student> students = new ArrayList<>();
    private StudentBuilder studentBuilder = new StudentBuilder();
    private String data;

    List<Student> getStudents() {
        return students;
    }

    @Override
    public void startElement(String uri, String localname, String qName, Attributes attributes) {
        switch (qName.toLowerCase()) {
            case "specialty":
                studentBuilder.setSpecialty(attributes.getValue(0));
                break;
            case "group":
                studentBuilder.setGroupID(attributes.getValue(0));
                break;
            case "student":
                studentBuilder
                        .setName(null)
                        .setSurname(null)
                        .setCity(null)
                        .setPhoneNumber(null);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localname, String qName) {
        switch (qName.toLowerCase()) {
            case "name":
                studentBuilder.setName(data);
                break;
            case "surname":
                studentBuilder.setSurname(data);
                break;
            case "phone":
                studentBuilder.setPhoneNumber(data);
                break;
            case "city":
                studentBuilder.setCity(data);
                break;
            case "student":
                students.add(studentBuilder.build());
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data = new String(ch, start, length);
    }
}
