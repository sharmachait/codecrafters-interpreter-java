```cfg
expression     -> unary | binary | literal | grouping ;
grouping       -> "(" expression ")" ;
unary          -> ( "-" | "!" ) expression ;
binary         -> expression operator expression ;
literal        -> NUMBER | STRING | "true" | "false" | "nil" ;
operator       -> "==" | "!=" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "/" ;
```

## precedence
comma, ternary
1. equality == != 
2. comparison > >= < <=
3. term - +
4. factor / *
5. unary ! -
6. brackets

false

```cfg
expression     -> comma;
comma          -> ternary ( "," ternary)* ;
ternary        -> equality ( "?" ternary ":" ternary )* ;
equality       -> comparison ( ( "!=" | "==" ) comparison )* ;
comparison     -> term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term           -> factor ( ( "-" | "+" ) factor )* ;
factor         -> unary ( ( "/" | "*" ) unary )* ;
unary          -> ( "-" | "!" ) unary | primary ;
primary        -> NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")";
```
then we added commas
```cfg  
expression     → comma ;  
comma          → assignment ( "," assignment )* ;  
assignment     → equality ;  
equality       → comparison ( ( "!=" | "==" ) comparison )* ;  
comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;  
term           → factor ( ( "-" | "+" ) factor )* ;  
factor         → unary ( ( "/" | "*" ) unary )* ;  
unary          → ( "!" | "-" ) unary | primary ;  
primary        → NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" ;  
```  
then ternary expressions
```cfg  
expression     → comma ;  
comma          → ternary ( "," ternary )* ;  
ternary        → assignment ( "?" expression ":" ternary )? ;  
assignment     → equality ;  
equality       → comparison ( ( "!=" | "==" ) comparison )* ;  
comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;  
term           → factor ( ( "-" | "+" ) factor )* ;  
factor         → unary ( ( "/" | "*" ) unary )* ;  
unary          → ( "!" | "-" ) unary | primary ;  
primary        → NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" ;  
```

but commas cause issues with function parameters and arguments so we will drop that