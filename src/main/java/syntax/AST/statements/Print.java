package syntax.AST.statements;

import syntax.AST.analysis.StatementVisitor;
import syntax.AST.expressions.Expression;

public class Print extends Statement {
    public final Expression expression;
    public Print(Expression expression) {
        this.expression = expression;
    }
    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitPrintStatement(this);
    }
}
