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

var a;
a=1;
a=b=1; //1

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
assignment     → IDENTIFIER "=" assignment | equality ;  
equality       → comparison ( ( "!=" | "==" ) comparison )* ;  
comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;  
term           → factor ( ( "-" | "+" ) factor )* ;  
factor         → unary ( ( "/" | "*" ) unary )* ;  
unary          → ( "!" | "-" ) unary | primary ;  
primary        → NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" | IDENTIFIER ;  
```

### block syntax
```cfg  
program        → declaration* EOF ;
declaration    → varDecl | statement ;
varDecl        → "var" IDENTIFIER ( "=" expression )? ";" ;
statement      → exprStmt | printStmt | block ;
block          → "{" declaration* "}" ;
printStmt      → "print" expression ";" ;
exprStmt       → expression ";" ;
expression     → comma ;  
comma          → ternary ( "," ternary )* ;  
ternary        → assignment ( "?" expression ":" ternary )? ;  
assignment     → IDENTIFIER "=" assignment | equality ;  
equality       → comparison ( ( "!=" | "==" ) comparison )* ;  
comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;  
term           → factor ( ( "-" | "+" ) factor )* ;  
factor         → unary ( ( "/" | "*" ) unary )* ;  
unary          → ( "!" | "-" ) unary | primary ;  
primary        → NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" | IDENTIFIER ;  
```

### if statements
```cfg  
program        → declaration* EOF ;
declaration    → varDecl | statement ;
varDecl        → "var" IDENTIFIER ( "=" expression )? ";" ;

statement      → exprStmt | printStmt | block | ifStmt ;
ifStmt         → "if" "(" expression ")" statement ( "else" statement )? ;

block          → "{" declaration* "}" ;
printStmt      → "print" expression ";" ;
exprStmt       → expression ";" ;
expression     → comma ;  
comma          → ternary ( "," ternary )* ;  
ternary        → assignment ( "?" expression ":" ternary )? ;  
assignment     → IDENTIFIER "=" assignment | equality ;  
equality       → comparison ( ( "!=" | "==" ) comparison )* ;  
comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;  
term           → factor ( ( "-" | "+" ) factor )* ;  
factor         → unary ( ( "/" | "*" ) unary )* ;  
unary          → ( "!" | "-" ) unary | primary ;  
primary        → NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" | IDENTIFIER ;  
```

### logic OR and AND
```cfg  
program        → declaration* EOF ;
declaration    → varDecl | statement ;
varDecl        → "var" IDENTIFIER ( "=" expression )? ";" ;
statement      → exprStmt | printStmt | block | ifStmt ;
ifStmt         → "if" "(" expression ")" statement ( "else" statement )? ;
block          → "{" declaration* "}" ;
printStmt      → "print" expression ";" ;
exprStmt       → expression ";" ;
expression     → comma ;  
comma          → ternary ( "," ternary )* ;  
ternary        → assignment ( "?" expression ":" ternary )? ;  

assignment     → IDENTIFIER "=" assignment | logic_or ;  
logic_or       → logic_and ( "or" logic_and )* ;
logic_and      → equality ( "and" equality )* ;

equality       → comparison ( ( "!=" | "==" ) comparison )* ;  
comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;  
term           → factor ( ( "-" | "+" ) factor )* ;  
factor         → unary ( ( "/" | "*" ) unary )* ;  
unary          → ( "!" | "-" ) unary | primary ;  
primary        → NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" | IDENTIFIER ;  
```

### loops, breaks and continues
```cfg  
program        → declaration* EOF ;
declaration    → varDecl | statement ;
varDecl        → "var" IDENTIFIER ( "=" expression )? ";" ;

statement      → exprStmt | printStmt | block | ifStmt | whileStmt | forStmt | breakStmt | continueStmt ;
whileStmt      → "while" "(" expression ")" statement ;
forStmt        → "for" "(" ( varDecl | exprStmt | ";") expression? ";" expression? ")" statement ;
breakStmt      → "break" ";" ;
continueStmt   → "continue" ";" ;

ifStmt         → "if" "(" expression ")" statement ( "else" statement )? ;
block          → "{" declaration* "}" ;
printStmt      → "print" expression ";" ;
exprStmt       → expression ";" ;
expression     → comma ;  
comma          → ternary ( "," ternary )* ;  
ternary        → assignment ( "?" expression ":" ternary )? ;  
assignment     → IDENTIFIER "=" assignment | logic_or ;  
logic_or       → logic_and ( "or" logic_and )* ;
logic_and      → equality ( "and" equality )* ;
equality       → comparison ( ( "!=" | "==" ) comparison )* ;  
comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;  
term           → factor ( ( "-" | "+" ) factor )* ;  
factor         → unary ( ( "/" | "*" ) unary )* ;  
unary          → ( "!" | "-" ) unary | primary ;  
primary        → NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" | IDENTIFIER ;  
```

### functions
```cfg  
program        → declaration* EOF ;
declaration    → varDecl | statement ;
varDecl        → "var" IDENTIFIER ( "=" expression )? ";" ;
statement      → exprStmt | printStmt | block | ifStmt | whileStmt | forStmt | breakStmt | continueStmt ;
whileStmt      → "while" "(" expression ")" statement ;
forStmt        → "for" "(" ( varDecl | exprStmt | ";") expression? ";" expression? ")" statement ;
breakStmt      → "break" ";" ;
continueStmt   → "continue" ";" ;
ifStmt         → "if" "(" expression ")" statement ( "else" statement )? ;
block          → "{" declaration* "}" ;
printStmt      → "print" expression ";" ;
exprStmt       → expression ";" ;
expression     → comma ;  
comma          → ternary ( "," ternary )* ;  
ternary        → assignment ( "?" expression ":" ternary )? ;  
assignment     → IDENTIFIER "=" assignment | logic_or ;  
logic_or       → logic_and ( "or" logic_and )* ;
logic_and      → equality ( "and" equality )* ;
equality       → comparison ( ( "!=" | "==" ) comparison )* ;  
comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;  
term           → factor ( ( "-" | "+" ) factor )* ;  
factor         → unary ( ( "/" | "*" ) unary )* ;  

unary          → ( "!" | "-" ) unary | call ;  
call           → primary ( "(" arguments? ")" )* ;
arguments      → expression ("," expression )* ; // 

primary        → NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")" | IDENTIFIER ;  
```