package syntax.AST.analysis;

import syntax.AST.expressions.*;

import static lexicon.TokenType.*;

public class Interpreter implements ExpressionVisitor<Object> {

    public Object interpret(Expression e) {
        return e.accept(this);
    }

    @Override
    public Object visitBinary(Binary expr) {
        Object left = expr.left.accept(this);
        Object right = expr.right.accept(this);
        switch (expr.operator.type){
            case MINUS:
                return (double) left - (double) right;
            case PLUS:
                if(left instanceof String || right instanceof String){
                    return ""+left+right;
                }
                return (double) left + (double) right;
            case STAR:
                return (double) left * (double) right;
            case SLASH:
                return (double) left / (double) right;
            case GREATER:
                return (double) left > (double) right;
            case GREATER_EQUAL:
                return (double) left >= (double) right;
            case LESS:
                return (double) left < (double) right;
            case LESS_EQUAL:
                return (double) left <= (double) right;
            case BANG_EQUAL:
                return !isEqual(left, right);
            case EQUAL_EQUAL:
                return isEqual(left, right);
        }
        return null;
    }

    private boolean isEqual(Object left, Object right) {
        if(left == null && right == null) return true;
        if(left == null || right ==null) return false;
        return left.equals(right);
    }

    @Override
    public Object visitUnary(Unary expr) {
        Object right = expr.right.accept(this);
        switch (expr.operator.type){
            case MINUS:
                return -(double) right;
            case BANG:
                return !isTruthy(right);
        }
        return null;
    }

    @Override
    public Object visitGrouping(Grouping expr) {
        return expr.expression.accept(this);
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitTernary(Ternary ternary) {
        Object condition = ternary.condition.accept(this);
        if(isTruthy(condition)){
            return ternary.trueBranch.accept(this);
        }else{
            return ternary.falseBranch.accept(this);
        }
    }

    private boolean isTruthy(Object condition) {
        if(condition == null)return false;
        if(condition instanceof Boolean) return (Boolean) condition;
        return true;
    }
}