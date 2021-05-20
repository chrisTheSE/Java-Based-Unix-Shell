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
package io;

import commands.Command;
import commands.Echo;
import commands.Redirection;
import driver.JShell;
import runtime.ErrorHandler;

/**
 * Defines how the system prints out to the shell.
 */
public class StandardOutput {


  /**
   * Prints message out to the shell on it's own line.
   * 
   * @param message, to be printed out to the shell
   * @return returns message
   */
  public static String println(String message) {
    System.out.println(message);
    return message;
  }

  /**
   * Takes an output and redirects it appropriately
   * 
   * @param tokens array of type string containing command arguments
   * @param output output to be redirected
   * @param shell an instance of JShell
   * @param command a command instance
   * @return returns the output given
   */
  public static String println(String[] tokens, String output, JShell shell,
      Command command) {
    int indexArrow = tokens.length - 2 >= 0 ? tokens.length - 2 : 0;

    boolean containsArrow = Command.containsArrow(tokens);

    if (containsArrow) {
      String[] tokens2 = new String[4];

      tokens2[0] = "redirect";
      tokens2[1] = "\"" + output + "\"";
      tokens2[2] = tokens[indexArrow];
      tokens2[3] = tokens[indexArrow + 1];

      Redirection redirect = new Redirection();
      redirect = (Redirection) redirect.run(tokens2, shell);
      if (redirect.getErrors() != null) {
        return StandardOutput.println(redirect.getErrors());
      }
      // } else if (tokens.length <= command.getMaxNumOfArguments()-2
      // || command.getMaxNumOfArguments() == -1) {
    } else
      return StandardOutput.println(output);
    // } else {
    // ErrorHandler.invalidComboOfParams(command, tokens);
    // }
    return null;
  }


  /**
   * Prints message out to the shell without adding a line return.
   * 
   * @param message, to be printed out to the shell
   */
  public static void print(String message) {
    System.out.print(message);
  }
}
