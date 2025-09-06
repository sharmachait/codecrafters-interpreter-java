package syntax.AST.expressions;

import syntax.AST.analysis.ExpressionVisitor;

public abstract class Expression {
    public abstract <T> T accept(ExpressionVisitor<T> visitor);
}
