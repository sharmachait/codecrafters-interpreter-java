package syntax.AST.statements;

import lexicon.Token;
import syntax.AST.analysis.StatementVisitor;

public class Break extends Statement {
    public final Token token;

    public Break(Token token) {
        this.token = token;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitBreakStatement(this);
    }
}
