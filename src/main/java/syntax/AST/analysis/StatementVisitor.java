package syntax.AST.analysis;

import syntax.AST.statements.*;

public interface StatementVisitor<R> {
    R visitPrintStatement(Print expr);
    R visitExpressionStatement(ExpressionStatement expr);

    R visitVarDecl(VarDecl varDecl);

    R visitBlockStatement(Block block);

    R visitIfStatement(If stmt);

    R visitWhileStatement(While aWhile);

    R visitBreakStatement(Break aBreak);

    R visitContinueStatement(Continue aContinue);
}
