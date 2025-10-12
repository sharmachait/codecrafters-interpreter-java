package syntax.AST.statements;

import lexicon.Token;
import syntax.AST.analysis.StatementVisitor;

public class Continue extends Statement {
    public final Token token;

    public Continue(Token token) {
        this.token = token;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitContinueStatement(this);
    }
}