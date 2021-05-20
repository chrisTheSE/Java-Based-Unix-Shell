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
 * Move moves a file or directory to the desired location
 */
public class Move extends Command {

  /**
   * Constructor for Move class. It initializes identifier, maxNumOfArguments,
   * minNumOfArguments errorTooManyArguments, missingOperand, and description
   * from its super class Commands.
   */
  public Move() {
    this.setIdentifier("mv");

    // MakeDirectory must have three tokens
    this.setMaxNumOfArguments(3);
    this.setMinNumOfArguments(3);

    // Error Handling
    this.setErrorTooManyArguments("there must be exactly two parameter");
    this.setMissingOperand("there must be exactly two parameter");

    // Description
    this.setDescription("This command moves a directory or file into the"
        + " desired directory etiher new or already existing one");
  }

  /**
   * The run method of Move moves a FileSystemNode into another directory or
   * moves a File into another FileSystemNode or overwrites a file 
   * into anotherfile path that already exists or copies the content of a 
   * file into another file path
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param shell contains the FileSystem and cache
   * @return this command which will have the output and errors
   */
  @Override
  public Command run(String[] tokens, JShell shell) {
    FileSystem fSystem = shell.getfSystem();
    FileSystemNode givenNode = fSystem.getSemiFileSystemNode(tokens[1]);

    if (tokens[2].startsWith(tokens[1]))
      this.setErrors(
          ErrorHandler.subFileSystemNodeError(this, tokens[1], tokens[2]));
    else if (givenNode != null) {
      if (givenNode
          .isChildInsideByDirectoryName(fSystem.getPathLastEntry(tokens[1]))) {
        moveFileSystemNode(tokens[1], tokens[2], shell);
      } else if (givenNode.getDirectory()
          .isFileInsideByFileName(fSystem.getPathLastEntry(tokens[1]))) {
        moveFile(tokens[1], tokens[2], shell);
      }
    } else if (!fSystem.inappropriatePath(tokens[1])) {
      this.setErrors(ErrorHandler.invalidPath(this, tokens[1]));
    } else
      this.setErrors(ErrorHandler.inappropriatePath(this, tokens[1]));

    return this;
  }


  /**
   * moveFileSystemNode moves a FileSystemNode that the givenPath refers to to
   * another FileSystemNode that targetPath refers if there is no file with
   * directory of the givenPath. It can also change the directoryName of the
   * givenPath if the targetPath last entry doesn't exists but the rest does, 
   * so it changes the directoryName to the last entry of targetPath
   * 
   * @param givenPath, a path to a FileSystemNode
   * @param targetPath, a path to a FileSystemNode or File
   * @param shell contains the FileSystem and cache
   */
  private void moveFileSystemNode(String givenPath, String targetPath,
      JShell shell) {
    FileSystem fSystem = shell.getfSystem();
    FileSystemNode targetNode = fSystem.getSemiFileSystemNode(targetPath);
    if (targetNode != null) {
        moveFileSystemNodeHelper(givenPath, targetPath, shell);
    } else if (!fSystem.inappropriatePath(targetPath)) {
      this.setErrors(ErrorHandler.invalidPath(this, targetPath));
    } else
      this.setErrors(ErrorHandler.inappropriatePath(this, targetPath));

  }
  
  /**
   * moveFileSystemNodeHelper moves a FileSystemNode that the givenPath 
   * refers to to another FileSystemNode that targetPath refers 
   * if there is no file with directory of the givenPath. 
   * 
   * @param givenPath, a path to a FileSystemNode
   * @param targetPath, a path to a FileSystemNode or File
   * @param shell contains the FileSystem and cache
   */
  private void moveFileSystemNodeHelper(String givenPath, String targetPath,
      JShell shell) {
    
    FileSystem fSystem = shell.getfSystem();
    FileSystemNode possibleNode = fSystem.getFileSystemNode(targetPath);
    FileSystemNode targetNode = fSystem.getSemiFileSystemNode(targetPath);
    FileSystemNode givenNode = fSystem.getFileSystemNode(givenPath);
    String targetName = fSystem.getPathLastEntry(targetPath);
    String givenName = fSystem.getPathLastEntry(targetPath);
    
    if (possibleNode != null) {
      if (!possibleNode.getPath().startsWith(givenNode.getPath())) {
        if (!possibleNode.getDirectory().isFileInsideByFileName(givenName)) {
          possibleNode.addChild(givenNode);
          givenNode.setParent(targetNode);
          removeTheObject(givenPath, shell);
        } else 
            this.setErrors(ErrorHandler.fileAlreadyExistantAtPath(this, 
               targetPath));
      } else 
        this.setErrors(ErrorHandler.subFileSystemNodeError(this, 
            givenPath, targetPath));
    } else if (!targetNode.getDirectory().isFileInsideByFileName(givenName)) {
        targetNode.addChild(givenNode);
        givenNode.setParent(targetNode);
        removeTheObject(givenPath, shell);
        givenNode.getDirectory().setDirectoryName(targetName);
    } else
      this.setErrors(ErrorHandler.moveDirectoryIntoFileError(this, givenPath,
          targetPath));
  }
  
  
  /**
   * removeTheObject removes the FileSystemNode or FIle tha the givenPath
   * refers to
   * 
   * @param givenPath, a path to a FileSystemNode
   * @param shell contains the FileSystem and cache
   */
  private void removeTheObject(String givenPath, JShell shell) {
    Remove remove = new Remove();
    String[] removeTokens = {"rm", givenPath};
    remove.run(removeTokens, shell);
  }

  /**
   * moveFile moves a File that the givenPath refers to to another File or
   * FileSystemNode targetPath refers to. If the File already exists, overwrite
   * it, if it doesn't, change name as corresponding
   * 
   * @param givenPath, a path to a File
   * @param targetPath, a path to a FileSystemNode or File
   * @param shell contains the FileSystem and cache
   */
  private void moveFile(String givenPath, String targetPath, JShell shell) {

    FileSystem fSystem = shell.getfSystem();
    FileSystemNode beforeNode = fSystem.getSemiFileSystemNode(targetPath);
    FileSystemNode possibleNode = fSystem.getFileSystemNode(targetPath);

    if (beforeNode != null) {
      if (possibleNode != null) {
        Redirection redirectionCommand = new Redirection();
        File file = fSystem.getSemiFileSystemNode(givenPath).getDirectory()
            .getFileByFileName(fSystem.getPathLastEntry(givenPath));
        String[] redirectionTokens =
            {"redirect", "\"" + file.getContent() + "\"", ">",
                targetPath + "/" + fSystem.getPathLastEntry(givenPath)};
        redirectionCommand.run(redirectionTokens, shell);
      } else {
        Redirection redirectionCommand = new Redirection();
        File file = fSystem.getSemiFileSystemNode(givenPath).getDirectory()
            .getFileByFileName(fSystem.getPathLastEntry(givenPath));
        String[] redirectionTokens =
            {"redirect", "\"" + file.getContent() + "\"", ">", targetPath};
        redirectionCommand.run(redirectionTokens, shell);
      }
      removeTheObject(givenPath, shell);
    } else if (!fSystem.inappropriatePath(targetPath)) {
      this.setErrors(ErrorHandler.invalidPath(this, targetPath));
    } else
      this.setErrors(ErrorHandler.inappropriatePath(this, targetPath));
  }
}
