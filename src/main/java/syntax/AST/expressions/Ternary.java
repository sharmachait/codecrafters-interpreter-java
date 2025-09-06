package syntax.AST.expressions;

import syntax.AST.analysis.ExpressionVisitor;

public class Ternary extends Expression{
    public final Expression trueBranch;
    public final Expression falseBranch;
    public final Expression condition;

    public Ternary(Expression trueBranch, Expression falseBranch, Expression condition) {
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
        this.condition = condition;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitTernary(this);
    }
}
