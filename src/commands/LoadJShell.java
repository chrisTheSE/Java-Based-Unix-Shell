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
 * Loads a .ser save such that the full state of the shell is loaded into an
 * empty JShell.
 *
 */
public class LoadJShell extends Command {

  /**
   * Constructor for loadJShell class. It initializes identifier,
   * maxNumOfArguments, minNumOfArguments errorTooManyArguments, missingOperand,
   * and description from its super class Commands.
   */
  public LoadJShell() {
    this.setIdentifier("loadJShell");
    this.setDescription(
        "The file FileName is some file that is stored on the actual filesystem"
            + " of your computer. The purpose of this\r\n"
            + "command is to load the saved session of the JShell before the"
            + " user" + " closed it down. ");
    this.setMaxNumOfArguments(2);
    this.setMinNumOfArguments(2);
    this.setErrorTooManyArguments("Too many arguments, please enter fileName");
    this.setMissingOperand("What file name, do wish to call the save?");
  }


  /**
   * Given a .ser file save, then load the save into an empty JShell
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param shell, an instance of JShell
   * @return returns an instance of the command
   */
  @Override
  public Command run(String[] tokens, JShell shell) {
    if (shell.getCache().getHistorySize() <= 1) {
      try {
        FileInputStream file = new FileInputStream(tokens[1] + ".ser");
        ObjectInputStream inStream = new ObjectInputStream(file);
        JShell newShell = (JShell) inStream.readObject();
        shell.setfSystem(newShell.getfSystem());
        shell.setCache(newShell.getCache());

        inStream.close();
        file.close();

      } catch (IOException | ClassNotFoundException e) {
        this.setErrors(ErrorHandler.invalidPath(this, tokens[1]));
      }
    } else {
      this.setErrors(ErrorHandler.badInput(this, "Non-empty JShell"));
    }

    return this;
  }
}
