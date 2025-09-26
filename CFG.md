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

```cfg 
program        → statement * EOF ;
statement      → printStmt | exprStmt ;
printStmt      → "print" expression ";" ;
exprStmt       → expression ";" ;
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

There is no place in the grammar where both an expression and a statement are allowed.
The operands of, say, + are always expressions, never statements. The body of a while loop is always a statement.

we’re going to split the statement grammar in two to handle them.
That’s because the grammar restricts where some kinds of statements are allowed.

The clauses in control flow statements—think the then and else branches of an if statement or the body of a while—are each a single statement.

But that statement is not allowed to be one that declares a name.

This is OK:
`if (monday) print "Ugh, already?";`
But this is not:
`if (monday) var beverage = "espresso";`

to accommodate this distinction we again change the grammar by adding declaration statements,
var declaration statements and updating Primaries to be identifiers as well
```cfg  
program        → declaration* EOF ;
declaration    → varDecl | statement ;
varDecl        → "var" IDENTIFIER ( "=" expression )? ";" ;
statement      → exprStmt | printStmt ;
printStmt      → "print" expression ";" ;
exprStmt       → expression ";" ;
expression     → comma ;  
comma          → ternary ( "," ternary )* ;  
ternary        → assignment ( "?" expression ":" ternary )? ;  
assignment     → equality ;  
equality       → comparison ( ( "!=" | "==" ) comparison )* ;  
comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;  
term           → factor ( ( "-" | "+" ) factor )* ;  
factor         → unary ( ( "/" | "*" ) unary )* ;  
unary          → ( "!" | "-" ) unary | primary ;  
primary        → NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" | IDENTIFIER ;  
```