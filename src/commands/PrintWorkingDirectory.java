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

import driver.JShell;

/**
 * Print the current working directory (including the whole path).
 */
public class PrintWorkingDirectory extends Command {

  /**
   * Constructor for PrintWorkingDirectory class. It initializes identifier,
   * maxNumOfArguments, minNumOfArguments errorTooManyArguments, 
   * missingOperand, and description from its super class Commands.
   */
  public PrintWorkingDirectory() {
    this.setIdentifier("pwd");

    // PrintWorkingDirectory must not have any arguments
    this.setMaxNumOfArguments(1);
    this.setMinNumOfArguments(1);

    // Error Handling
    this.setErrorTooManyArguments("there must be no arguments");
    this.setMissingOperand("identifier pwd is missing");

    // Description
    this.setDescription("The command PrintWorkingDirectory prints "
        + "the current working directory (including the whole path).");

  }

  /**
   * The run method of PrintWorkingDirectory displays the full path of the
   * currentDirectory in fileSystem and returns true after being done.
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param shell, an instance of JShell
   * @return returns an instance of the command
   */
  @Override
  public Command run(String[] tokens, JShell shell) {

    // Print the path of the current directory
    this.setOutput(shell.getfSystem().getCurrentDirectory().getPath());

    return this;

  }
}
