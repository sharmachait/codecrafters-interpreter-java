package syntax.AST.analysis;

import lexicon.Token;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, Object> values = new HashMap<>();
    public final Environment enclosing;

    public Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    public Environment(){
        enclosing = null;
    }

    void define(String name, Object value){
        values.put(name, value);
    }
    Object get(Token name){
        if(values.containsKey(name.lexeme)){
            return values.get(name.lexeme);
        }

        if(enclosing != null) return enclosing.get(name);

        throw new InterpreterException("Undefined variable '" + name.lexeme + "'.", name);
    }
    void assign(Token name, Object value){
        if(values.containsKey(name.lexeme)){
            values.put(name.lexeme, value);
            return;
        }

        if(enclosing!=null){
            enclosing.assign(name, value);
            return;
        }

        throw new InterpreterException("Undefined variable '" + name.lexeme + "'.", name);
    }
}
