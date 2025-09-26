package syntax.AST.statements;

import lexicon.Token;
import syntax.AST.analysis.StatementVisitor;
import syntax.AST.expressions.Expression;

public class VarDecl extends Statement{
    public final Token name;
    public final Expression initializer;

    public VarDecl(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitVarDecl(this);
    }
}
