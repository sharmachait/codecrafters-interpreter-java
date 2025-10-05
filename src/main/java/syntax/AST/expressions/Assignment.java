package syntax.AST.expressions;

import lexicon.Token;
import syntax.AST.analysis.ExpressionVisitor;

public class Assignment extends Expression {
    public final Token name;
    public final Expression value;

    public Assignment(Token name, Expression value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitAssignment(this);
    }
}
