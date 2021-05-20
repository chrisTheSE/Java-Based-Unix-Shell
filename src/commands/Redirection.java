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
import runtime.ErrorHandler;

/**
 * Takes in an array of tokens and redirects output to a file. Creates a new
 * file if OUTFILE does not exists and erases the old contents if OUTFILE
 * already exists. In either case, the only thing in OUTFILE should be STRING.
 * If double_arrow OUTFILE: Like the single_arrow, but appends instead of
 * overwrites.
 */
public class Redirection extends Command {

  /**
   * Constructor for Redirection class. It initializes identifier,
   * maxNumOfArguments, minNumOfArguments errorTooManyArguments, missingOperand,
   * and description from its super class Commands.
   */
  public Redirection() {
    this.setDescription("Redirects output to a file when > or >> is added");
    this.setIdentifier("redirect");
    this.setMaxNumOfArguments(-1);
    this.setMinNumOfArguments(1);
    this.setErrorTooManyArguments("Too many arguments");
    this.setMissingOperand("Please specify a file");
  }

  /**
   * Updates a file's contents specified from tokens or creates a new file if
   * file doesn't exist.
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param node, an instance of FileSystemNode class that holds the position of
   *        the file in the path directory tree
   * @param name, name of the file
   */
  private void toFile(String[] tokens, FileSystemNode node, String name) {
    File curFile = node.getDirectory().getFileByFileName(name);
    String desc = tokens[1].replace("\"", "");
    // Check if file exists
    if (curFile != null) {
      if (tokens[2].equals(">>")) { // With >> then appends to file
        curFile.setContent(curFile.getContent() + desc);
      } else { // With > then override the file
        curFile.setContent(desc);
      }
      // If file doesn't exist then create new file with specified contents
    } else {
      File newFile = new File(name);
      newFile.setContent(desc);
      node.getDirectory().addFile(newFile);
    }
  }

  /**
   * Writes OUTPUT to a new file, overrides contents of a file if file already
   * exists and/or appends OUTPUT to a file.
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param shell, an instance of JShell
   * @return returns an instance of the command
   */
  @Override
  public Command run(String[] tokens, JShell shell) {

    if (!Command.containsArrow(tokens)) {
      this.setErrors(ErrorHandler.invalidComboOfParams(this, tokens));
      return this;
    }

    FileSystem fSystem = shell.getfSystem();
    String name;
    FileSystemNode node;

    name = fSystem.getPathLastEntry(tokens[3]);
    node = fSystem.getSemiFileSystemNode(tokens[3]);


    if (fSystem.inappropriateName(name)) {
      this.setErrors(ErrorHandler.invalidName(this, tokens[3]));
      return this;
    }

    if (node != null && !name.equals("")) {
      toFile(tokens, node, name);
    } else {
      this.setErrors(ErrorHandler.invalidPath(this, tokens[3]));
    }

    return this;
  }
}
