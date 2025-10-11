package syntax.AST.analysis;

import syntax.AST.expressions.*;
import syntax.AST.statements.*;

import java.util.List;

import static lexicon.TokenType.*;

public class Interpreter implements ExpressionVisitor<Object>, StatementVisitor<Object> {

    private Environment env = new Environment();
    public Object interpret(Expression e) {
        try{
            return e.accept(this);
        } catch (InterpreterException ex) {
            System.err.println(ex.getMessage());
            System.err.println("[line "+ex.token.line+"]");
            return ex;
        }
    }

    public Object interpret(Statement e) {
        try{
            return e.accept(this);
        } catch (InterpreterException ex) {
            System.err.println(ex.getMessage());
            System.err.println("[line "+ex.token.line+"]");
            return ex;
        }
    }

    @Override
    public Object visitBinary(Binary expr) {
        Object left = expr.left.accept(this);
        Object right = expr.right.accept(this);
        switch (expr.operator.type){
            case MINUS:
                if(!(left instanceof Double) || !(right instanceof Double) ){
                    throw new InterpreterException("Operands must be a numbers.", expr.operator);
                }
                return (double) left - (double) right;
            case PLUS:
                if(left instanceof String && right instanceof String){
                    return ""+left+right;
                }
                if(left instanceof String || right instanceof String){
                    throw new InterpreterException("Operands must be two numbers or two strings.", expr.operator);
                }
                return (double) left + (double) right;
            case STAR:
                if(!(left instanceof Double) || !(right instanceof Double) ){
                    throw new InterpreterException("Operands must be a numbers.", expr.operator);
                }
                return (double) left * (double) right;
            case SLASH:
                if(!(left instanceof Double) || !(right instanceof Double) ){
                    throw new InterpreterException("Operands must be a numbers.", expr.operator);
                }
                return (double) left / (double) right;
            case GREATER:
                if(! (right instanceof Double) || ! (left instanceof Double)){
                    throw new InterpreterException("Operands must be a numbers.", expr.operator);
                }
                return (double) left > (double) right;
            case GREATER_EQUAL:
                if(! (right instanceof Double) || ! (left instanceof Double)){
                    throw new InterpreterException("Operands must be a numbers.", expr.operator);
                }
                return (double) left >= (double) right;
            case LESS:
                if(! (right instanceof Double) || ! (left instanceof Double)){
                    throw new InterpreterException("Operands must be a numbers.", expr.operator);
                }
                return (double) left < (double) right;
            case LESS_EQUAL:
                if(! (right instanceof Double) || ! (left instanceof Double)){
                    throw new InterpreterException("Operands must be a numbers.", expr.operator);
                }
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
                if(!(right instanceof Double)){
                    throw new InterpreterException("Operand must be a number.", expr.operator);
                }
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

    @Override
    public Object visitVariable(Variable variable) {
        return env.get(variable.name);
    }

    @Override
    public Object visitAssignment(Assignment assignment) {
        //a=1; -> 1 -> a=(b=1) -> a=1
        Object value = assignment.value.accept(this);
        env.assign(assignment.name, value);
        return value;
    }

    private boolean isTruthy(Object condition) {
        if(condition == null)return false;
        if(condition instanceof Boolean) return (Boolean) condition;
        return true;
    }

    @Override
    public Object visitPrintStatement(Print expr) {
        Object result = expr.expression.accept(this);
        System.out.println(stringify(result));
        return result;
    }

    private String stringify(Object result) {
        if(result == null) return "nil";
        if(result instanceof Double){
            Double d = (Double) result;
            if(d.intValue() == d){
                int val = d.intValue();
                return ""+val;
            }else{
                return result.toString();
            }
        }
        return result.toString();
    }

    @Override
    public Object visitExpressionStatement(ExpressionStatement expr) {
        return expr.expression.accept(this);
    }

    @Override
    public Object visitVarDecl(VarDecl varDecl) {
        Object value = null;
        if(varDecl.initializer!=null) value = varDecl.initializer.accept(this);
        env.define(varDecl.name.lexeme, value);
        return value;
    }

    @Override
    public Object visitBlockStatement(Block block) {
        return executeBlock(block.statements, new Environment(env));
    }

    @Override
    public Object visitIfStatement(If stmt) {
        Object res = null;
        Object condition = stmt.condition.accept(this);
        if(isTruthy(condition)){
            res = stmt.thenBranch.accept(this);
        }else if(stmt.elseBranch!=null){
            res = stmt.elseBranch.accept(this);
        }
        return res;
    }

    private Object executeBlock(List<Statement> statements, Environment scopedEnv) {
        Environment prev = env;
        Object res = null;
        try{
            this.env = scopedEnv;
            for(Statement stmt: statements){
                res = stmt.accept(this);
            }
        }finally {
            this.env = prev;
        }

        return res;
    }
}