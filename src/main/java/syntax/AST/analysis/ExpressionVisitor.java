package syntax.AST.analysis;

import syntax.AST.expressions.*;

public interface ExpressionVisitor<R> {
    R visitBinary(Binary expr);
    R visitUnary(Unary expr);
    R visitGrouping(Grouping expr);
    R visitLiteral(Literal expr);
    R visitTernary(Ternary ternary);

    R visitVariable(Variable variable);

    R visitAssignment(Assignment assignment);
}
