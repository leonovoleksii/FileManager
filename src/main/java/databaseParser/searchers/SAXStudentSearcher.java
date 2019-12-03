package databaseParser.searchers;

import databaseParser.model.Student;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SAXStudentSearcher implements StudentSearcher {

    public List<Student> search(File file, Student student) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        StudentHandler handler;
        List<Student> result = new ArrayList<>();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            handler = new StudentHandler();
            saxParser.parse(file, handler);
            for (Student s : handler.getStudents()) {
                if (s.similarTo(student)) {
                    result.add(s);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public static void main(String[] args) {
        SAXStudentSearcher searcher = new SAXStudentSearcher();
        List<Student> students = searcher.search(new File("students.xml"),
                new Student(null,  "K-25", null, null, null, null));
        for (Student s : students) {
            System.out.println(s.toString());
        }
    }
}
