package databaseParser.searchers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchInitializer {
    List<String> specialities = new ArrayList<>(), groups = new ArrayList<>();
    public void search(File file) {
        Document document;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        } catch (ParserConfigurationException e) {
            System.err.println("Unable to create DocumentBuilder");
            return;
        } catch (IOException | SAXException e) {
            System.err.println("Unable to parse the file " + file.getAbsolutePath());
            return;
        }
        NodeList specialitiesList = document.getElementsByTagName("specialty");
        for (int i = 0; i < specialitiesList.getLength(); i++) {
            Element element = (Element) specialitiesList.item(i);
            specialities.add(element.getAttribute("NAME"));
        }
        specialities.add(0, "Not selected");
        NodeList groupsList = document.getElementsByTagName("group");
        for (int i = 0; i < groupsList.getLength(); i++) {
            Element element = (Element)groupsList.item(i);
            groups.add(element.getAttribute("ID"));
        }
        groups.add(0, "Not selected");
    }

    public List<String> getSpecialities() {
        return specialities;
    }

    public List<String> getGroups() {
        return  groups;
    }
}
