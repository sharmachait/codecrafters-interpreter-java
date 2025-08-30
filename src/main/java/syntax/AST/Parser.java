package syntax.AST;

import lexicon.Token;
import lexicon.TokenType;
import static lexicon.TokenType.*;
import runner.Runner;
import syntax.AST.expressions.*;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }
    public int current = 0;
    private Token peek(){
        return tokens.get(current);
    }
    private Token previous(){
        return tokens.get(current-1);
    }
    private boolean isAtEnd(){
        return peek().type == TokenType.EOF;
    }
    private Token advance(){
        if(!isAtEnd()) current++;
        return previous();
    }
    private boolean checkCurrentType(TokenType type){
        if(isAtEnd()) return false;
        return peek().type == type;
    }
    private boolean matchCurrentToken(TokenType... types){
        for(TokenType type: types){
            if(checkCurrentType(type)){
                advance();
                return true;
            }
        }
        return false;
    }
    private Token consume(TokenType tokenType, String message) {
        if(checkCurrentType(tokenType)) return advance();
        Token curr = peek();
        throw report(peek(), message);
    }

    private ParserException report(Token token, String message) {
        int line = token.line;
        String where;
        if(token.type == EOF){
            where = " at end";
        }else{
            where = " at '"+ token.lexeme +"'";
        }
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        ParserException exception = new ParserException(token.lexeme + ": " + message);
        Runner.setParserException(exception);
        return exception;
    }

    public Expression parse() {
        try{
            return expression();
        }catch (ParserException e){
            return null;
        }
    }
    private Expression primary(){
        if(matchCurrentToken(FALSE)) return new Literal(false);
        if(matchCurrentToken(TRUE)) return new Literal(true);
        if(matchCurrentToken(NIL)) return new Literal(null);
        
        if(matchCurrentToken(NUMBER,STRING)){
            return new Literal(previous().literal);
        }
        
        if(matchCurrentToken(LEFT_PAREN)){
            Expression expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new Grouping(expr);
        }
        throw report(peek(), "Expect expression.");
    }
    private Expression expression() {
        return equality();
    }
    private Expression equality() {
        //equality       -> comparison ( ( "!=" | "==" ) comparison )* ;
        Expression expr = comparison();
        while(matchCurrentToken(BANG_EQUAL, EQUAL_EQUAL)){
            Token operator = previous();
            Expression right = comparison();
            expr = new Binary(operator, expr, right);
        }
        return expr;
    }

    private Expression comparison() {
        //comparison     -> term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
        Expression expr = term();
        while(matchCurrentToken(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)){
            Token operator = previous();
            Expression right = term();
            expr = new Binary(operator, expr, right);
        }
        return expr;
    }

    private Expression term() {
        //term           -> factor ( ( "-" | "+" ) factor )* ;
        Expression expr = factor();
        while(matchCurrentToken(MINUS, PLUS)){
            Token operator = previous();
            Expression right = factor();
            expr = new Binary(operator, expr, right);
        }
        return expr;
    }

    private Expression factor() {
        //factor         -> unary ( ( "/" | "*" ) unary )* ;
        Expression expr = unary();
        while(matchCurrentToken(STAR, SLASH)){
            Token operator = previous();
            Expression right = unary();
            expr = new Binary(operator, expr, right);
        }
        return expr;
    }

    private Expression unary() {
        //unary          -> ( "-" | "!" ) unary | primary ;
        if(matchCurrentToken(BANG, MINUS)){
            Token operator = previous();
            Expression right = unary();
            return new Unary(operator, right);
        }
        return primary();
    }

}
