package tables.processors;

import gen.calculatorLexer;
import gen.calculatorParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Checker {
    public static boolean check(String s) {
        calculatorLexer lexer = new calculatorLexer(CharStreams.fromString(s));
        lexer.removeErrorListeners();
        lexer.addErrorListener(new MyErrorListener());
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        calculatorParser parser = new calculatorParser(tokens);
        try {
            parser.expression();
        } catch (Exception e) {
            System.err.println("OUCH");
            return false;
        }
        return true;
    }
}
