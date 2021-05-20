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

import data.File;
import data.FileSystem;
import data.FileSystemNode;
import driver.JShell;
import io.StandardOutput;
import runtime.ErrorHandler;

/**
 * Takes in an array of tokens from execution and executes the Concatenate
 * command. The Concatenate command; displays the contents of FILE1 and other
 * files (i.e. File2 ....) concatenated in the shell.
 */
public class Concatenate extends Command {

  /**
   * Constructor for Concatenate class. It initializes identifier,
   * maxNumOfArguments, minNumOfArguments errorTooManyArguments, missingOperand,
   * and description from its super class Commands.
   */
  public Concatenate() {
    this.setIdentifier("cat");
    this.setDescription("Display the contents of FILE1 and other files"
        + " (i.e. File2 ....) concatenated in the shell.");
    this.setMaxNumOfArguments(-1);
    this.setMinNumOfArguments(2);
    this.setErrorTooManyArguments("");
    this.setMissingOperand("Which files do you wish to display?");
  }

  /**
   * Prints the contents of a specified file to the terminal.
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param shell an instance of JShell
   * @return returns an instance of command with ouput and errors
   */
  @Override
  public Command run(String[] tokens, JShell shell) {
    FileSystem fSystem = shell.getfSystem();
    int i = 1;
    String name;
    FileSystemNode node;
    String output = "";
    boolean containsArrow = Command.containsArrow(tokens);
    int shift = containsArrow ? -2 : 0;

    while (i < tokens.length && !tokens[i].equals(">")
        && !tokens[i].equals(">>")) {

      name = fSystem.getPathLastEntry(tokens[i]);
      node = fSystem.getSemiFileSystemNode(tokens[i]);
      File file = null;

      if (name != null && node != null) {
        file = node.getDirectory().getFileByFileName(name);
      }

      if (node != null && file != null) {
        if (i > 1) {
          output += "\n\n\n";
        }
        output += file.getContent();

      } else {
        this.setOutput(this.getOutput() + "\n");
        this.setErrors(ErrorHandler.invalidPath(this, tokens[i]));
        break;
      }
      i++;
    }

    this.setOutput(output);

    return this;
  }
}
