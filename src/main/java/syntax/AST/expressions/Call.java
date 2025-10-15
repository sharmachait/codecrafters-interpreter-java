package syntax.AST.expressions;

import lexicon.Token;
import syntax.AST.analysis.ExpressionVisitor;

import java.util.List;

public class Call extends Expression {

    public final Expression callee;
    public final Token paren;
    public final List<Expression> arguments;

    public Call(Expression callee, Token paren, List<Expression> arguemnts) {
        this.callee = callee;
        this.paren = paren;
        this.arguments = arguemnts;
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitCallExpression(this);
    }
}
