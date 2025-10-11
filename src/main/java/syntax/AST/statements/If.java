package syntax.AST.statements;

import syntax.AST.analysis.StatementVisitor;
import syntax.AST.expressions.Expression;

public class If extends Statement{
    public final Expression condition;
    public final Statement thenBranch;
    public final Statement elseBranch;

    public If(Expression condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitIfStatement(this);
    }
}
