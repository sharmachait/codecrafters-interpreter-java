package runner;

import lexicon.Lexer;
import lexicon.Token;
import syntax.AST.Parser;
import syntax.AST.ParserException;
import syntax.AST.analysis.AstPrinter;
import syntax.AST.expressions.Expression;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Runner {
    private static ParserException parserException = null;
    public static void setParserException(ParserException pe){
        parserException = pe;
    }
    private static String getFileContents(String filename) {
        String fileContents = "";
        try {
            fileContents = Files.readString(Path.of(filename));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }

        if(fileContents.isEmpty()){
            System.out.println("EOF  null");
            return null;
        }
        return fileContents;
    }

    public static void tokenize(String filename) {
        String fileContents = getFileContents(filename);
        if (fileContents == null) return;

        Lexer lexer = new Lexer(fileContents);
        Lexer.Result result = lexer.scan();
        for(Token token : result.tokens){
            System.out.println(token);
        }
        if(result.exception != null) {
            System.exit(65);
        }
    }

    public static void parse(String filename) {
        String fileContents = getFileContents(filename);
        if (fileContents == null) return;

        Lexer lexer = new Lexer(fileContents);
        Lexer.Result result = lexer.scan();
        if(result.exception != null) {
            System.exit(65);
        }

        Parser parser = new Parser(result.tokens);
        Expression e = parser.parse();

        if(e!=null){
            AstPrinter astPrinter = new AstPrinter();
            astPrinter.print(e);
        }else{
            System.exit(65);
        }
    }
}
