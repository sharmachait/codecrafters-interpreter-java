package syntax.AST.expressions;

import lexicon.Token;
import syntax.AST.analysis.ExpressionVisitor;

public class Logical extends Expression {
    public final Expression left;
    public final Token operator;
    public final Expression right;

    public Logical(Expression left, Token operator, Expression right) {
        this.right = right;
        this.operator = operator;
        this.left = left;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitLogical(this);
    }
}
