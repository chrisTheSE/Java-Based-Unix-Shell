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
import data.FileSystemNode;
import driver.JShell;
import runtime.ErrorHandler;

/**
 * 
 * Removes a directory from the filesystem
 *
 */
public class Remove extends Command {

  /**
   * Constructor for Remove class. It initializes identifier, 
   * maxNumOfArguments,minNumOfArguments, errorTooManyArguments, 
   * missingOperand, and description from its super class Commands.
   */
  public Remove() {
    this.setIdentifier("rm");

    // Remove must only have two tokens
    this.setMaxNumOfArguments(2);
    this.setMinNumOfArguments(2);

    // Error Handling
    this.setErrorTooManyArguments("there must be exactly one parameter");
    this.setMissingOperand("there must be exactly one parameter");

    // Description
    this.setDescription("Removes the directory from the fileSystem "
        + "including the children of the directory as well as the subpaths "
        + "in the stack");
  }


  /**
   * The run method of Remove removes the directory that the tokens[1] refers 
   * to and deletes all the paths in the stack that depended on tokens[1] if
   * tokens[1] is a valid/proper path, otherwise, it displays an error message
   * and return true.
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param shell contains the FileSystem and cache
   * @return this command which will have the output and errors
   */
  public Command run(String[] tokens, JShell shell) {
    FileSystem fSystem = shell.getfSystem();
    Cache cache = shell.getCache();
    FileSystemNode beforeNode = fSystem.getSemiFileSystemNode(tokens[1]);
    if (beforeNode != null) {
      if (!fSystem.getCurrentDirectory().getPath().startsWith(tokens[1])) {
        if (beforeNode.isChildInsideByDirectoryName(
            fSystem.getPathLastEntry(tokens[1]))) {
          cache.removeDirectory(
              fSystem.getFileSystemNode(tokens[1]).getPath());
          beforeNode.removeChildByDirectoryName(
              fSystem.getPathLastEntry(tokens[1]));
        } else if (beforeNode.getDirectory()
            .isFileInsideByFileName(fSystem.getPathLastEntry(tokens[1]))) {
          beforeNode.getDirectory()
              .removeFileByFileName(fSystem.getPathLastEntry(tokens[1]));
        } else {
          this.setErrors(ErrorHandler.invalidPath(this, tokens[1]));
        }

      } else
        this.setErrors(ErrorHandler.removeDirectoryError(tokens[1]));

    } else if (!fSystem.inappropriatePath(tokens[1])) {
      this.setErrors(ErrorHandler.invalidPath(this, tokens[1]));
    } else {
      this.setErrors(ErrorHandler.inappropriatePath(this, tokens[1]));
    }
    return this;
  }

}
