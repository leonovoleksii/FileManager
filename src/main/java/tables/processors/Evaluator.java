package tables.processors;

import javax.script.*;

public class Evaluator {
    static private ScriptEngine engine = (new ScriptEngineManager()).getEngineByName("js");
    static {
        try {
            engine.eval("function inc(a) { return a + 1; }");
            engine.eval("function dec(a) { return a - 1; }");
        } catch (ScriptException e) {
            System.err.println(e);
        }
    }
    public static Object evaluate(String s) throws ScriptException {
        return engine.eval(s);
    }
}
