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

import java.util.Arrays;

import data.FileSystemNode;
import driver.JShell;
import io.StandardOutput;
import runtime.ErrorHandler;

/**
 * Command is the super class which defines the structure of all commands, their
 * constants and how to access them, and the method run to run them.
 */
public class Command implements CommandInterface {
  // the identifier is the shorthand for the command that
  // is entered in the terminal
  private String identifier;
  // max and min number of arguments the command can take
  private int maxNumOfArguments;
  private int minNumOfArguments;
  // error messages when given the wrong number of parameters
  private String errorTooManyArguments;
  private String errorMissingOperand;
  // the command's description
  private String description;
  private String output;
  private String errors;

  /**
   * Get the output for the command.
   * 
   * @return returns the output for the command
   */
  public String getOutput() {
    return output;
  }

  /**
   * Set the output.
   * 
   * @param output, the output to set for the command
   */
  public void setOutput(String output) {
    this.output = output;
  }


  /**
   * Get the errors for the command.
   * 
   * @return returns the error for the command
   */
  public String getErrors() {
    return errors;
  }

  /**
   * Set the error.
   * 
   * @param errors, the errors to set for the command
   */
  public void setErrors(String errors) {
    this.errors = errors;
  }

  /**
   * Set the identifier.
   * 
   * @param iden, the identifier to set for the command
   */
  public void setIdentifier(String iden) {
    this.identifier = iden;
  }

  /**
   * Set the max number of arguments.
   * 
   * @param num, the number to set for the max number of arguments of the
   *        command
   */
  public void setMaxNumOfArguments(int num) {
    this.maxNumOfArguments = num;
  }

  /**
   * Set the min number of arguments.
   * 
   * @param num, the number to set for the min number of arguments of the
   *        command
   */
  public void setMinNumOfArguments(int num) {
    this.minNumOfArguments = num;
  }

  /**
   * Set the error message for when too many arguments are given to the command.
   * 
   * @param err, the error message to set for too many arguments
   */
  public void setErrorTooManyArguments(String err) {
    this.errorTooManyArguments = err;
  }

  /**
   * Set the error message for missing operand(s) for the command.
   * 
   * @param err, the error message to set for missing operands
   */
  public void setMissingOperand(String err) {
    this.errorMissingOperand = err;
  }

  /**
   * Set the description for the command.
   * 
   * @param description, the description for the command
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Get the identifier of the command.
   * 
   * @return returns a string of the command's identifier
   */
  public String getIdentifier() {
    return this.identifier;
  }

  /**
   * Get the max number of arguments for the command.
   * 
   * @return returns an int of the max number of arguments for the command
   */
  public int getMaxNumOfArguments() {
    return this.maxNumOfArguments;
  }

  /**
   * Get the min number of arguments for the command.
   * 
   * @return returns an int of the min number of arguments for the command
   */
  public int getMinNumOfArguments() {
    return this.minNumOfArguments;
  }

  /**
   * Get the error message for too many arguments for the command.
   * 
   * @return return a string of the error message for too many arguments
   */
  public String getErrorTooManyArguments() {
    return this.errorTooManyArguments;
  }

  /**
   * Get the error message for missing operand(s) for the command.
   * 
   * @return return a string of the error message for missing operand(s)
   */
  public String getErrorMissingOperand() {
    return this.errorMissingOperand;
  }

  /**
   * Get the description for the command.
   * 
   * @return returns a string of the description for the command
   */
  public String getDescription() {
    return description;
  }

  /**
   * Checks if the tokens contains arrow or double_arrow
   * 
   * @param tokens, an array of string tokens containing command arguments
   * @return returns true if tokens contains arrow or double_arrow, else false
   */
  public static boolean containsArrow(String[] tokens) {
    if (tokens.length < 2)
      return false;
    int indexArrow = tokens.length - 2;
    return tokens[indexArrow].equals(">") || tokens[indexArrow].equals(">>");
  }

  /**
   * Checks if tokens has a valid redirection
   * 
   * @param tokens, an array of string tokens containing command arguments
   * @param shell an instance of JShell
   * @return returns true if tokens has a valid redirection, else false
   */
  public Command checkRun(String[] tokens, JShell shell) {
    boolean arrow = containsArrow(tokens);
    // if (this.getMaxNumOfArguments() == -1
    // || !arrow && tokens.length <= this.getMaxNumOfArguments()
    // || arrow && tokens.length <= this.getMaxNumOfArguments() + 2) {
    if (tokens.length == 0)
      return this;

    String name =
        shell.getfSystem().getPathLastEntry(tokens[tokens.length - 1]);
    FileSystemNode targetNode =
        shell.getfSystem().getSemiFileSystemNode(tokens[tokens.length - 1]);
    boolean validName = !shell.getfSystem().inappropriateName(name);
    boolean validPath = targetNode != null;
    if (arrow) {
      if (!validName) {
        this.setErrors(ErrorHandler.invalidName(this, name));
        return this;
      }
      if (!validPath) {
        this.setErrors(
            ErrorHandler.invalidPath(this, tokens[tokens.length - 1]));
        return this;
      }
      if (targetNode.isChildInsideByDirectoryName(name)) {
        this.setErrors(ErrorHandler.childAlreadyExistant(name, targetNode));
        return this;
      }
      if (!this.getIdentifier().equals("redirect")) {
        return this.run(Arrays.copyOfRange(tokens, 0, tokens.length - 2),
            shell);
      }
    }
    return this.run(tokens, shell);

    // }
    // this.setErrors(ErrorHandler.invalidComboOfParams(this, tokens));
    // return this;
  }



  /**
   * Runs the command (to be implemented for each command, overridden by each
   * subclass).
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param shell an instance of JShell
   * @return returns an instance of command with output and errors
   */
  public Command run(String[] tokens, JShell shell) {
    return null;
  }


}
