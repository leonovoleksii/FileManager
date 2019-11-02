package textEditor;

import org.jsoup.Jsoup;

public class Capitalizer {
    public String capitalize(String s) {
        StringBuilder sb = new StringBuilder();
        if (s.length() > 0) {
            sb.append(Character.toUpperCase(s.charAt(0)));
        }
        boolean foundTheEndOfSentence = false;
        for (int i = 1; i < s.length(); i++) {
            if (foundTheEndOfSentence && !Character.isWhitespace(s.charAt(i))) {
                sb.append(Character.toUpperCase(s.charAt(i)));
                foundTheEndOfSentence = false;
            } else {
                sb.append(s.charAt(i));
            }
            if (s.charAt(i) == '.') {
                foundTheEndOfSentence = true;
            }
        }
        return sb.toString();
    }
}
