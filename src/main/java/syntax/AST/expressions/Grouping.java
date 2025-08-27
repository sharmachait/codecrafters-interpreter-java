package syntax.AST.expressions;

import syntax.AST.analysis.ExpressionVisitor;

public class Grouping extends Expression {
    public final Expression expression;

    public Grouping(Expression expression) {
        this.expression = expression;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitGrouping(this);
    }
}
