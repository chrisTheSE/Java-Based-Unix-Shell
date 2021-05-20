// **********************************************************
// Assignment2:
// Student1: Christian Chen Liu
// UTORID user_name: Chenl147
// UT Student #: 1006171009
// Author: Christian Chen Liu
//
// Student2: Christopher Suh
// UTORID user_name: suhchris
// UT Student #: 1006003664
// Author: Christopher Suh
//
// Student3: Andrew D'Amario
// UTORID user_name: damario4
// UT Student #: 1006618947
// Author: Andrew D'Amario
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package commands;

import data.Cache;
import driver.JShell;
import io.StandardOutput;
import runtime.ErrorHandler;

/**
 * Access the History data of the current session of the java shell and print it
 * out.
 */
public class History extends Command {

  /**
   * Constructor for History class. It initializes identifier,
   * maxNumOfArguments, minNumOfArguments errorTooManyArguments, missingOperand,
   * and description from its super class Commands.
   */
  public History() {
    this.setIdentifier("history");
    this.setDescription("This command will print out recent commands,"
        + " one command per line. ");
    this.setMaxNumOfArguments(2);
    this.setMinNumOfArguments(1);
    this.setErrorTooManyArguments("Too many arguments");
    this.setMissingOperand("");
  }

  /**
   * Try to convert string to a non-negative integer and return it. If it the
   * string is not a non-negative integer it returns -1.
   * 
   * @param token, the string token from the terminal to try to convert into a
   *        non-negative int
   * @return returns the non-negative integer in the string if the conversion
   *         was successful or else returns -1
   */
  private int tryNonNegInteger(String token) {
    try {
      int truncate = Integer.parseInt(token);
      if (truncate >= 0)
        return truncate;
      else
        return -1;
    } catch (Exception e) {
      return -1;
    }
  }

  /**
   * Prints out the recent commands, one command per line. The first column is
   * numbered such that the line with the highest number is the most recent
   * command. The output can be truncated by specifying a number as a second
   * token (less or equal to 0) after the command, say n, which will show n
   * recent commands. If n is greater than the history, it will print all the
   * history.
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param shell an instance of JShell
   * @return returns an instance of command with output and errors
   */

  @Override
  public Command run(String[] tokens, JShell shell) {
    Cache cache = shell.getCache();
    String output = "";
    boolean containArrow = Command.containsArrow(tokens);

    // start counting from
    int start;
    // size of history
    int n = cache.getHistorySize();

    // if no number is given, start from the beginning
    if (tokens.length == 1 || (tokens.length == 3 && containArrow))
      start = 0;
    else {
      // try to get the truncate value to start at
      int truncate = tryNonNegInteger(tokens[1]);
      // bigger than history, just print all history
      if (truncate >= n)
        start = 0;
      // if it's non-negative, start at the truncate most recent
      else if (truncate >= 0)
        start = n - truncate;
      else {
        this.setErrors(ErrorHandler.badInput(this,
            "Operand must" + " be a non-negative integer"));
        return this;
      }
    }

    for (int i = start; i < n; i++) {
      // for each line, print the history line from start to n-1 (the most
      // recent) numbering starting at 1 (i+1)
      output += String.valueOf(i + 1) + ". " + cache.getHistory(i);
      if (i + 1 < n)
        output += "\n";
    }

    this.setOutput(output);

    return this;
  }
}
