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

import data.FileSystem;
import data.FileSystemNode;
import driver.JShell;
import runtime.ErrorHandler;

/**
 * Change directory to DIR, which may be relative to the current directory or
 * may be a full path. As with Unix, .. means a parent directory and a . means
 * the current directory. The directory must be /, the forward slash. The root
 * of the file system is a single slash: /.
 */
public class ChangeDirectory extends Command {

  /**
   * Constructor for ChangeDirectory class. It initializes identifier,
   * maxNumOfArguments, minNumOfArguments errorTooManyArguments, 
   * missingOperand, and description from its super class Commands.
   */
  public ChangeDirectory() {
    this.setIdentifier("cd");

    // ChangeDirectory must have two tokens
    this.setMaxNumOfArguments(2);
    this.setMinNumOfArguments(2);

    // Error Handling
    this.setErrorTooManyArguments("there must be exactly one parameter");
    this.setMissingOperand("there must be exactly one parameter");

    // Description
    this.setDescription("Change directory to DIR, which may be relative to"
        + " the current directory\nor may be a full path. As with Unix, .."
        + " means a parent directory and a . \nmeans the current directory."
        + " The directory must be /, the forward slash. \nThe foot of the"
        + " file system is a single slash: /.");
  }

  /**
   * The run method of ChangeDirectory changes the currentDirectory of the
   * fileSystem to the given path tokens[1] if it is a valid/appropriate
   * Directory path in fileSystem and not a File path, otherwise, give back an
   * error message. Return this Command with its output and errors.
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param shell contains the FileSystem and cache
   * @return this command which will have its output and errors
   */
  @Override
  public Command run(String[] tokens, JShell shell) {

    FileSystem fSystem = shell.getfSystem();
    FileSystemNode targetNode = null;
    // Set targetNode to the FileSystemNode that the path leads to
    targetNode = fSystem.getFileSystemNode(tokens[1]);

    // Check if the targetNode is in the FileSystem
    if (targetNode != null) {
      // Set the current Directory to the targetNode
      shell.getfSystem().setCurrentDirectory(targetNode);
    } else if (fSystem.getSemiFileSystemNode(tokens[1]) != null
        && fSystem.getSemiFileSystemNode(tokens[1]).getDirectory()
            .isFileInsideByFileName(fSystem.getPathLastEntry(tokens[1]))) {
      this.setErrors(
          ErrorHandler.changeDirectoryIntoFileError(this, tokens[1]));
    } else if (!fSystem.inappropriatePath(tokens[1])) {
      this.setErrors(ErrorHandler.invalidPath(this, tokens[1]));
    } else {
      this.setErrors(ErrorHandler.inappropriatePath(this, tokens[1]));
    }
    return this;

  }

}
