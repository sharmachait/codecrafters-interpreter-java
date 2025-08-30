package syntax.AST.analysis;

import syntax.AST.expressions.*;

public class AstPrinter implements ExpressionVisitor<String> {
    public String print(Expression expression){
        return expression.accept(this);
    }
    @Override
    public String visitBinary(Binary expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitUnary(Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }

    @Override
    public String visitGrouping(Grouping expr) { // ( group ( + 1 2 ) )
        return parenthesize("group", expr.expression);
    }

    private String parenthesize(String name, Expression... expressions){
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(name);
        for(Expression e: expressions){
            sb.append(" ");
            sb.append(e.accept(this));
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String visitLiteral(Literal expr) {
        if(expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitTernary(Ternary ternary) {
        String trueBranch = ternary.trueBranch.accept(this);
        String falseBranch = ternary.falseBranch.accept(this);
        String condition = ternary.condition.accept(this);

        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append("(");
        sb.append(condition);
        sb.append(") ? ");
        sb.append(trueBranch);
        sb.append(" : ");
        sb.append(falseBranch);
        sb.append(")");
        sb.append(")");

        return sb.toString();
    }
}
