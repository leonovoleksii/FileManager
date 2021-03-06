package tables.processors;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class MyErrorListener extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSybmol, int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new IllegalArgumentException();
    }
}
