import databaseParser.model.Student;
import databaseParser.model.StudentBuilder;
import databaseParser.searchers.DOMStudentSearcher;
import databaseParser.searchers.SAXStudentSearcher;
import databaseParser.searchers.SearchInitializer;
import tables.processors.Evaluator;
import tables.processors.Replacer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

public class ProjectTester {
    @org.junit.Test
    public void testEvaluator() throws Exception {
        assertEquals(Evaluator.evaluate("2+2*(2 + 2)"), 10);
    }

    @org.junit.Test
    public void testReplacer() {
        TreeMap<String, Object> nameToValue = new TreeMap<>();
        nameToValue.put("A1", 2);
        nameToValue.put("A3", 3);
        String validExpression = "A1+A1*A3+4-5";
        String invalidExpression = "A1+A1*A4+4-5";
        assertEquals(Replacer.process(validExpression, nameToValue), "2+2*3+4-5");
        assertEquals(Replacer.process(invalidExpression, nameToValue), "2+2*A4+4-5");
    }

    @org.junit.Test
    public void testInitializer() {
        SearchInitializer searchInitializer = new SearchInitializer();
        File file = new File("/home/oleksii/test.xml");
        searchInitializer.search(file);
        List<String> groups = new ArrayList<>();
        groups.add("Not selected");
        groups.add("K-25");
        groups.add("K-24");
        groups.add("K-11");
        assertEquals(searchInitializer.getGroups(), groups);
    }

    @org.junit.Test
    public void testStudentEquality() {
        StudentBuilder sb = new StudentBuilder();
        sb.setName("Oleksii");
        sb.setCity("Zaporizhzhia");
        assertEquals(sb.build(), sb.build());
    }

    @org.junit.Test
    public void testDOMandSAXStudentSearchers() {
        File file = new File("/home/oleksii/test.xml");
        StudentBuilder sb = new StudentBuilder();
        List<Student> expectedStudents = new ArrayList<>();
        Student templateStudent;
        sb.setSpecialty("Computer Science");
        sb.setGroupID("K-25");
        sb.setCity("Zaporizhzhia");
        templateStudent = sb.build();

        sb.setSpecialty("Computer Science");
        sb.setGroupID("K-25");
        sb.setPhoneNumber("+380991234567");
        sb.setName("Oleksii");
        sb.setSurname("Leonov");
        sb.setCity("Zaporizhzhia");
        expectedStudents.add(sb.build());

        sb.setPhoneNumber(null);
        sb.setName("Mykyta");
        sb.setSurname("Sadok");
        expectedStudents.add(sb.build());

        DOMStudentSearcher domStudentSearcher = new DOMStudentSearcher();
        List<Student> DOMstudents = domStudentSearcher.search(file, templateStudent);

        SAXStudentSearcher saxStudentSearcher = new SAXStudentSearcher();
        List<Student> SAXstudents = saxStudentSearcher.search(file, templateStudent);

        assertEquals(DOMstudents, expectedStudents);
        assertEquals(SAXstudents, expectedStudents);
    }


}
