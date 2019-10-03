package textEditor;

public class Capitalizer {
    public String capitalize(String s) {
        StringBuilder sb = new StringBuilder();
        if (s.length() > 0) {
            sb.append(Character.toUpperCase(s.charAt(0)));
        }
        boolean foundTheEndOfLine = false;
        for (int i = 1; i < s.length(); i++) {
            if (foundTheEndOfLine && !Character.isSpaceChar(s.charAt(i))) {
                sb.append(Character.toUpperCase(s.charAt(i)));
                foundTheEndOfLine = false;
            } else {
                sb.append(s.charAt(i));
            }
            if (s.charAt(i) == '.') {
                foundTheEndOfLine = true;
            }
        }
        return sb.toString();
    }
}
