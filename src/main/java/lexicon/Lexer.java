package lexicon;

import java.util.ArrayList;
import java.util.List;
import static lexicon.TokenType.*;

public class Lexer {
    private final String source;
    private char getCurrMoveNext(){
        return source.charAt(curr++);
    }
    private Character getNext() {
        if(curr >= source.length()) return null;
        return source.charAt(curr);
    }
    private int line = 1;
    private int curr = 0;
    private int start = 0; // (()
                           // s
                           //  c
    private final List<Token> tokens = new ArrayList<>();
    private void addToken(TokenType type, Object literal) {
        String lexeme = source.substring(start,curr);
        tokens.add(new Token(type, lexeme, literal, line));
    }
    public Lexer(String fileContents) {
        source = fileContents;
    }
// for
//    s
//    c
    public static class Result{
        public final List<Token> tokens;
        public final Exception exception;

        public Result(List<Token> tokens, Exception exception) {
            this.tokens = tokens;
            this.exception = exception;
        }
    }

    public Result scan() {
        ScanException isException = null;
        while(curr < source.length()) {
            char current = getCurrMoveNext();
            ScanException currentException = handleToken(current);
            if(currentException != null){
                isException = currentException;
            }
            start = curr;
        }

        addToken(EOF, null);
        return new Result(tokens, isException);
    }

    private ScanException handleToken(char current) {
        switch (current){
            case '(':
                addToken(LEFT_PAREN, null);
                break;
            case ')':
                addToken(RIGHT_PAREN, null);
                break;
            case '{':
                addToken(LEFT_BRACE, null);
                break;
            case '}':
                addToken(RIGHT_BRACE, null);
                break;
            case ';':
                addToken(SEMICOLON, null);
                break;
            case ':':
                addToken(COLON, null);
                break;
            case '?':
                addToken(QUESTION, null);
                break;
            case ',':
                addToken(COMMA, null);
                break;
            case '.':
                addToken(DOT, null);
                break;
            case '-':
                addToken(MINUS, null);
                break;
            case '+':
                addToken(PLUS, null);
                break;
            case '*':
                addToken(STAR, null);
                break;
            case '=', '!', '<', '>':
                ScanException dualCharError = handleDualCharacterTokens(current);
                if(dualCharError!=null) {
                    System.err.println(dualCharError.getMessage());
                    return dualCharError;
                }
                break;
            case '/':
                // maybe multi character
                addToken(SLASH, null);
                break;
            default:
                ScanException e = new ScanException("[line "+line+"] Error: Unexpected character: "+current);
                System.err.println(e.getMessage());
                return e;
        }
        return null;
    }

    private ScanException handleDualCharacterTokens(char current) {
        ScanException e = null;
        Character next = getNext();
        switch (current){
            case '=':
                if(next == null || next!='='){
                    addToken(EQUAL, null);
                }else{
                    curr++;
                    addToken(EQUAL_EQUAL, null);
                }
                break;
            case '!':
                if(next == null || next!='='){
                    addToken(BANG, null);
                }else{
                    curr++;
                    addToken(BANG_EQUAL, null);
                }
            case '<':
                if(next == null || next!='='){
                    addToken(LESS, null);
                }else{
                    curr++;
                    addToken(LESS_EQUAL, null);
                }
                break;
            case '>':
                if(next == null || next!='='){
                    addToken(GREATER, null);
                }else{
                    curr++;
                    addToken(GREATER_EQUAL, null);
                }
                break;
            default:
                throw new RuntimeException("Invalid character passed in to be checked for dual character token");
        }
        return e;
    }
}
