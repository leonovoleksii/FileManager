package tables.processors;

import javax.script.*;

public class Evaluator {
    static private ScriptEngine engine = (new ScriptEngineManager()).getEngineByName("js");
    public static Object evaluate(String s) throws ScriptException {
        return engine.eval(s);
    }
}
