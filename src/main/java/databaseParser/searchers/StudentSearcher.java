package databaseParser.searchers;

import databaseParser.model.Student;

import java.io.File;
import java.util.List;

public interface StudentSearcher {
    List<Student> search(File file, Student student);
}
