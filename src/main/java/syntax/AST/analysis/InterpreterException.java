package syntax.AST.analysis;

import lexicon.Token;

public class InterpreterException extends RuntimeException {
    public final Token token;
    public InterpreterException(String message, Token token) {
        super(message);
        this.token = token;
    }
}
