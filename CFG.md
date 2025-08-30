```cfg
expression     -> unary | binary | literal | grouping ;
grouping       -> "(" expression ")" ;
unary          -> ( "-" | "!" ) expression ;
binary         -> expression operator expression ;
literal        -> NUMBER | STRING | "true" | "false" | "nil" ;
operator       -> "==" | "!=" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "/" ;
```

## precedence
1. equality == !=
2. comparison > >= < <=
3. term - +
4. factor / *
5. unary ! -
6. brackets

false

```cfg
expression     -> equality ;
equality       -> comparison ( ( "!=" | "==" ) comparison )* ;
comparison     -> term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term           -> factor ( ( "-" | "+" ) factor )* ;
factor         -> unary ( ( "/" | "*" ) unary )* ;
unary          -> ( "-" | "!" ) unary | primary ;
primary        -> NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")";
```