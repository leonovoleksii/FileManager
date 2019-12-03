package tables.processors;

import gen.CalculatorLexer;
import gen.CalculatorParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Checker {
    public static boolean check(String s) {
        CalculatorLexer lexer = new CalculatorLexer(CharStreams.fromString(s));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CalculatorParser parser = new CalculatorParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new MyErrorListener());
        try {
            parser.expression();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
