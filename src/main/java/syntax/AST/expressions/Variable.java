package syntax.AST.expressions;

import lexicon.Token;
import syntax.AST.analysis.ExpressionVisitor;

public class Variable extends Expression{
    public final Token name;

    public Variable(Token name) {
        this.name = name;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitVariable(this);
    }
}
