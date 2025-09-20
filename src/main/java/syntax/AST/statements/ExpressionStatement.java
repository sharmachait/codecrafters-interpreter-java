package syntax.AST.statements;

import syntax.AST.analysis.StatementVisitor;
import syntax.AST.expressions.Expression;

public class ExpressionStatement extends Statement {
    public final Expression expression;
    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }
    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitExpressionStatement(this);
    }
}
