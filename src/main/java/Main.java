import runner.Runner;

public class Main {
  public static void main(String[] args) {
    if (args.length < 2) {
      System.err.println("Usage: ./your_program.sh tokenize <filename>");
      System.exit(1);
    }

    String command = args[0];
    String filename = args[1];

    if (command.equals("tokenize")) {
      Runner.tokenize(filename);
      return;
    }else if(command.equals("parse")){
      Runner.parse(filename);
      return;
    }

    System.err.println("Unknown command: " + command);
    System.exit(1);
  }
}
