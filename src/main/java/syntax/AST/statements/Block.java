package syntax.AST.statements;

import syntax.AST.analysis.StatementVisitor;

import java.util.List;

public class Block extends Statement {
    public final List<Statement> statements;

    public Block(List<Statement> declarations) {
        this.statements = declarations;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitBlockStatement(this);
    }
}
