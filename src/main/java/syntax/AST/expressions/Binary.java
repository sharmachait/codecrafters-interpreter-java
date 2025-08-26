package syntax.AST.expressions;

import lexicon.Token;

public class Binary extends Expression {
    public final Token operator;
    public final Expression left, right;

    public Binary(Token operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
}
