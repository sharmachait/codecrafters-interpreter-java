package syntax.AST.functions;

import syntax.AST.analysis.Interpreter;

import java.util.List;

public interface LoxCallable {
    Object call(Interpreter interpreter, List<Object> arguments);
    int arity();
}
