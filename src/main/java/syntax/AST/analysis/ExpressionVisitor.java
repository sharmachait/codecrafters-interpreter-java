package syntax.AST.analysis;

import syntax.AST.expressions.Binary;
import syntax.AST.expressions.Grouping;
import syntax.AST.expressions.Literal;
import syntax.AST.expressions.Unary;

public interface ExpressionVisitor<R> {
    R visitBinary(Binary expr);
    R visitUnary(Unary expr);
    R visitGrouping(Grouping expr);
    R visitLiteral(Literal expr);
}
