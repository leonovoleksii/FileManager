package textEditor;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HTMLSimplifier {
    public String simplify(String text) {
        Document doc = Jsoup.parse(text);
        System.out.println(doc.getAllElements().size());
        for (Element e : doc.getAllElements()) {
            e.clearAttributes();
        }
        return doc.html();
    }
}
