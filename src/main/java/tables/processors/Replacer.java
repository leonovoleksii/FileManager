package tables.processors;

import java.util.TreeMap;

public class Replacer {
    public static String process(String s, TreeMap<String, Object> nameToValue) throws IllegalArgumentException {
        String res = s;
        for (String name : nameToValue.keySet()) {
            res = res.replaceAll(name, nameToValue.get(name).toString());
        }
        res = res.replaceAll("min", "Math.min");
        res = res.replaceAll("max", "Math.max");
        return res;
    }
}
