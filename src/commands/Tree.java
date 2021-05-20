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

/**
 * Tree displays the FileSystem as a tree structure
 */
public class Tree extends Command {

  /**
   * Constructor for Tree class. It initializes identifier, maxNumOfArguments,
   * minNumOfArguments, errorTooManyArguments, missingOperand, and description
   * from its super class Commands.
   */
  public Tree() {
    this.setIdentifier("tree");

    // Tree must only have one token
    this.setMaxNumOfArguments(1);
    this.setMinNumOfArguments(1);

    // Error Handling
    this.setErrorTooManyArguments("there must be no arguments");
    this.setMissingOperand("identifier tree is missing");

    // Description
    this.setDescription(
        "Displays the whole fileSystem in a tree structure drawing");
  }

  /**
   * The run method of Tree displays the fSystem as a tree structure of
   * subdirectories and files then return this Command with its output and
   * errors
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param shell contains the FileSystem and cache
   * @return this command which will have its output and errors
   */
  @Override
  public Command run(String[] tokens, JShell shell) {
    FileSystem fSystem = shell.getfSystem();
    int level = 0;
    String output = recursiveTreeDisplay("", fSystem.getRoot(), level);

    this.setOutput(output.substring(0, output.length() - 1));

    return this;

  }

  /**
   * The run method of Tree displays the fSystem as a tree structure of
   * subdirectories and files
   * 
   * @param output, the String that will hold the whole fileSystem as a tree
   * @param fileSystemNode, a FileSystemNode
   * @param level, the level of the recursion
   * @return output, the String that has the whole fileSystemas a tree
   */
  private String recursiveTreeDisplay(String output,
      FileSystemNode fileSystemNode, int level) {

    String indentationUnit = "  ";

    output += indentationUnit.repeat(level)
        + fileSystemNode.getDirectory().getDirectoryName() + "\n";

    int nextLevel = level + 1;

    for (FileSystemNode child : fileSystemNode.getChildren()) {
      output = recursiveTreeDisplay(output, child, nextLevel);
    }

    for (File file : fileSystemNode.getDirectory().getFiles()) {
      output += indentationUnit.repeat(nextLevel) + file.getFileName() + "\n";
    }

    return output;
  }
}
