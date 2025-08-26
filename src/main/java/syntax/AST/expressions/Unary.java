package syntax.AST.expressions;

import lexicon.Token;
import lexicon.TokenType;

public class Unary extends Expression{
    public final Token operator;
    public final Expression right;

    public Unary(Token operator, Expression expression) {
        if(operator.type != TokenType.MINUS && operator.type != TokenType.BANG){
            throw new RuntimeException("Invalid arguments for unary expression");
        }
        this.operator = operator;
        this.right = expression;
    }
}
