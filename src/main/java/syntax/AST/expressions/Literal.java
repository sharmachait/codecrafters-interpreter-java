package syntax.AST.expressions;

import syntax.AST.analysis.ExpressionVisitor;

public class Literal extends Expression{
    public final Object value;

    public Literal(Object value) {
        this.value = value;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitLiteral(this);
    }
}
