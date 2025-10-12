package syntax.AST.statements;

import syntax.AST.analysis.StatementVisitor;
import syntax.AST.expressions.Expression;

public class While extends Statement {
    public final Expression condition;
    public final Statement body;

    public While(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitWhileStatement(this);
    }
}
