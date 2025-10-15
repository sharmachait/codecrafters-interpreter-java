package syntax.AST.functions;

import syntax.AST.analysis.Interpreter;

import java.util.List;
import java.util.Scanner;

public class ReadLine implements LoxCallable{
    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();
    }

    @Override
    public int arity() {
        return 0;
    }

    @Override
    public String toString(){ return "<native fn>"; }
}
