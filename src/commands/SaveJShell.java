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
import data.FileSystem;
import driver.JShell;
import io.StandardOutput;
import runtime.ErrorHandler;
import java.io.*;

/**
 * Saves the full state of the shell in a .ser file that can be loaded in an
 * empty JShell.
 *
 */
public class SaveJShell extends Command {

  /**
   * Constructor for saveJShell class. It initializes identifier,
   * maxNumOfArguments, minNumOfArguments errorTooManyArguments, missingOperand,
   * and description from its super class Commands.
   */
  public SaveJShell() {
    this.setIdentifier("saveJShell");
    this.setDescription(
        "The file FileName is some file that is stored on the actual filesystem"
            + " of your computer. The purpose of this\r\n"
            + "command is to save the session of the JShell before the user"
            + " closes it down. ");
    this.setMaxNumOfArguments(2);
    this.setMinNumOfArguments(2);
    this.setErrorTooManyArguments("Too many arguments, please enter fileName");
    this.setMissingOperand("What file name, do wish to call the save?");
  }

  /**
   * Creates a save .ser file and stores it in the actual computer filesystem
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param shell, an instance of JShell
   * @return returns an instance of the command
   */
  @Override
  public Command run(String[] tokens, JShell shell) {
    try {
      FileOutputStream file = new FileOutputStream(tokens[1] + ".ser");
      ObjectOutputStream outStream = new ObjectOutputStream(file);
      outStream.writeObject(shell);
      outStream.close();
      file.close();

    } catch (IOException e) {
      this.setErrors(ErrorHandler.badInput(this, "Invalid filepath given"));
    }

    return this;
  }
}
