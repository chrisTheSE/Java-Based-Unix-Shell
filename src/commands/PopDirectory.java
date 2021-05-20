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

import data.*;
import driver.JShell;
import runtime.ErrorHandler;

/**
 * Allows user to get the most recent directory from the stack, pop it, and
 * change directory into it.
 */
public class PopDirectory extends Command {

  /**
   * Constructor for PopDirectory class. It initializes identifier,
   * maxNumOfArguments, minNumOfArguments errorTooManyArguments, missingOperand,
   * and description from its super class Commands.
   */
  public PopDirectory() {
    this.setIdentifier("popd");
    this.setDescription("Remove the top entry from the directory stack,"
        + " and cd into it. \nThe removal must be consistent as per the LIFO"
        + " behavior of  a \nstack. The popd command removes the top"
        + " most directory from \nthe directory stack and makes it the"
        + " current working directory. \nIf there is no directory onto"
        + " the stack, then give appropriate \nerror message. ");
    this.setMaxNumOfArguments(1);
    this.setMinNumOfArguments(1);
    this.setErrorTooManyArguments("Doesn't take any arguments");
    this.setMissingOperand("");
  }

  /**
   * Remove the top entry from the directory stack, and cd into it. The removal
   * must be consistent as per the LIFO behavior of a stack. The popd command
   * removes the top most directory from the directory stack and makes it the
   * current working directory. If there is no directory onto the stack, then
   * give appropriate error message.
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param shell, an instance of JShell
   * @return returns an instance of the command
   */
  @Override
  public Command run(String[] tokens, JShell shell) {
    FileSystem fSystem = shell.getfSystem();
    Cache cache = shell.getCache();

    try {
      String path = cache.popDirectoryStack();
      fSystem.setCurrentDirectory(fSystem.getFileSystemNode(path));
    } catch (Exception e) {
      this.setErrors(ErrorHandler.badInput(this, "Directory stack is empty"));
    }

    return this;
  }
}
