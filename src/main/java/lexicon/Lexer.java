package lexicon;

import java.util.ArrayList;
import java.util.List;
import static lexicon.TokenType.*;

public class Lexer {
    public Lexer(String fileContents) {
        source = fileContents;
    }
    private final String source;
    private char getCurrMoveNext(){
        return source.charAt(curr++);
    }
    private Character getCurr() {
        if(curr >= source.length()) return null;
        return source.charAt(curr);
    }
    private Character getNext() {
        int next = curr+1;
        if(next >= source.length()) return null;
        return source.charAt(next);
    }

    private int line = 1;
    private int curr = 0;
    private int start = 0;

    private final List<Token> tokens = new ArrayList<>();
    private void addToken(TokenType type, Object literal) {
        String lexeme = source.substring(start,curr);
        tokens.add(new Token(type, lexeme, literal, line));
    }

    public static class Result {
        public final List<Token> tokens;
        public final Exception exception;

        public Result(List<Token> tokens, Exception exception) {
            this.tokens = tokens;
            this.exception = exception;
        }
    }

    public Result scan() {
        ScanException isException = null;
        int len = source.length();
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
            case ' ','\r','\t':
                break;
            case '\n':
                line++;
                break;
            case '"':
                return string();
            case '=', '!', '<', '>', '/':
                return handleDualCharacterTokens(current);
            default:
                if(Character.isDigit(current)){
                    return number();
                } else if (isLoxAlpha(current)) {
                    identifier();
                } else{
                    return new ScanException("[line "+line+"] Error: Unexpected character: "+current);
                }
        }
        return null;
    }

    private void identifier() {
        while(curr < source.length() && isAlphaNumberic(getCurr())) getCurrMoveNext();
        String text = source.substring(start, curr);
        addToken(IDENTIFIER, null);
    }

    private boolean isAlphaNumberic(char curr) {
        return isLoxAlpha(curr) || Character.isDigit(curr);
    }

    private boolean isLoxAlpha(char c) {
        return c == '_' || Character.isAlphabetic(c);
    }

    private ScanException number() {
        while(curr<source.length() && Character.isDigit(getCurr())) getCurrMoveNext();
        if(curr < source.length() && getCurr() == '.'){
            getCurrMoveNext();
            if(curr >= source.length() || (curr < source.length() && !Character.isDigit(getCurr()))){
                return new ScanException("[line "+line+"] Error: expected property name after '.'");
            } else if (curr < source.length() && Character.isDigit(getCurr())) {
                while(curr<source.length() && Character.isDigit(getCurr())) getCurrMoveNext();
            }
        }
        String number = source.substring(start, curr);
        Double obj = Double.parseDouble(number);
        addToken(NUMBER, obj);
        return null;
    }

    private ScanException string() {
        while(curr < source.length() && getCurr() != '"') {
            if(getCurr() == '\n') line++;
            getCurrMoveNext();
        }
        if(curr >= source.length()){
            return new ScanException("[line "+line+"] Error: Unterminated string.");
        }
        getCurrMoveNext();
        String value = source.substring(start+1, curr-1);
        addToken(STRING, value);
        return null;
    }

    private ScanException handleDualCharacterTokens(char current) {
        ScanException e = null;
        Character next = getCurr();
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
                break;
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
            case '/':
                if(next == null || (next != '/' && next != '*')){
                    addToken(SLASH,null);
                }else{
                    curr++;
                    if(next == '/') discardLine();
                    else e = discardMultipleLines();
                }
                break;
            default:
                throw new RuntimeException("Invalid character passed in to be checked for dual character token");
        }
        return e;
    }
    private void discardLine() {
        while(curr < source.length() && getCurr() != '\n'){
            curr++;
        }
    }
    private ScanException discardMultipleLines() {
        while(curr < source.length() &&
                curr+1 < source.length() &&
                !(getCurr() == '*' && getNext() == '/')
        ){
            if(getCurr() == '\n') line++;
            curr++;
        }
        if(curr >= source.length()){
            return new ScanException("[line "+line+"] Unterminated multi line comment.");
        }
        if(curr+1 >= source.length()){
            return new ScanException("[line "+line+"] Unterminated multi line comment.");
        }
        curr++; // *
        curr++; // /
        return null;
    }
}
