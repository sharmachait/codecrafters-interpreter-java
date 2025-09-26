package syntax.AST.analysis;

import syntax.AST.statements.ExpressionStatement;
import syntax.AST.statements.Print;
import syntax.AST.statements.VarDecl;

public interface StatementVisitor<R> {
    R visitPrintStatement(Print expr);
    R visitExpressionStatement(ExpressionStatement expr);

    R visitVarDecl(VarDecl varDecl);
}
