package syntax.AST.statements;

import syntax.AST.analysis.StatementVisitor;

public abstract class Statement {
    public abstract <T> T accept(StatementVisitor<T> visitor);
}
