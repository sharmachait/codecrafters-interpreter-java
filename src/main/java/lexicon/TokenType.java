package lexicon;

public enum TokenType {
    // single character tokens
    LEFT_PAREN,
    RIGHT_PAREN,
    LEFT_BRACE,
    RIGHT_BRACE,
    COMMA,
    DOT,
    MINUS,
    PLUS,
    SEMICOLON,
    COLON,
    QUESTION,
    STAR,

    // Maybe single character token
    SLASH,
    BANG,
    EQUALITY,
    GREATER,
    LESS,

    // multi character tokens
    IDENTIFIER,
    STRING,
    NUMBER,
    AND,
    CLASS,
    ELSE,
    IF,
    FALSE,
    TRUE,
    FUN,
    FOR,
    WHILE,
    NIL,
    OR,
    PRINT,
    RETURN,
    SUPER,
    THIS,
    VAR,
    BREAK,
    CONTINUE,

    EOF
}
