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
package runtime;

import commands.Command;
import commands.Manual;
import data.FileSystemNode;


/**
 * Defines error messages for the various commands and prints the error messages
 * to the terminal.
 */
public class ErrorHandler {

  /**
   * tooManyArguments the error message of command having too many 
   * arguments
   * 
   * @param command, an instance of Command class or its subclasses
   * @return the error message of command having too many arguments
   */
  public static String tooManyArguments(Command command) {
    return command.getIdentifier() + ": " + command.getErrorTooManyArguments();
  }

  /**
   * commandNotFound returns the error message of tokens[0] not being a 
   * valid command
   * 
   * @param tokens, array of string tokens holding command arguments
   * @return the error message of tokens[0] not being a valid command
   */
  public static String commandNotFound(String[] tokens) {
    return tokens[0] + ": Command not found";
  }

  /**
   * commandNotFoundManual returns the error message of commandNotFound not 
   * being found in Manual
   * 
   * @param commandNotFound, name of command for which error occurred
   * @param man an instance of man class
   * @return  the error of commandNotFound not being found in Manual
   */
  public static String commandNotFoundManual(Manual man,
      String commandNotFound) {
    return man.getIdentifier() + ": No manual entry for: " + commandNotFound;
  }

  /**
   * missingOperand return the error message of missing the operant
   * 
   * @param command, an instance of Command class or its subclasses
   * @return the error of missing the operant
   */
  public static String missingOperand(Command command) {
    return command.getIdentifier() + ": " + command.getErrorMissingOperand();
  }

  /**
   * missingString returns the error message of missing the string format
   * 
   * @param command, an instance of Command class or its subclasses
   * @param tokens, array of string tokens holding command arguments
   * @return the error of missing the string format
   */
  public static String missingString(Command command, String[] tokens) {
    return command.getIdentifier() + ": " + tokens[1]
        + ": No string found, format string as \"string\"";
  }

  /**
   * illegalString returns the error message of parsing an illegal character
   * 
   * @return returns the error of parsing an illegal character
   */
  public static String illegalString() {
    return "parser: Illegal character in string";
  }

  /**
   * badInput returns the error message of having bad input
   * 
   * @param command, an instance of Command class or its subclasses
   * @param message, error message to be printed
   * @return the error of having bad input
   */
  public static String badInput(Command command, String message) {
    return command.getIdentifier() + ": " + message;
  }
  
  
  /**
   * moveDirectoryIntoFileError returns the error of givenPath being a 
   * path to a Directory and targetPath being a File path 
   * so you cannot move the directory
   * 
   * @param command, an instance of Command class or its subclasses
   * @param givenPath, a Directory path
   * @param targetPath, a File path
   * @return the error of trying to move a directory into a file
   */
  public static String moveDirectoryIntoFileError(Command command,
      String givenPath, String targetPath) {
    return command.getIdentifier() + ": cannot move directory at " + givenPath
        + " because " + targetPath + " refers to a file";
  }
  

  /**
   * fileAlreadyExist returns the error message of file already existing
   * 
   * @param command, an instance of Command class or its subclasses
   * @param file, name of file not found
   * @return the error of file already existing
   */
  public static String fileAlreadyExist(Command command, String file) {
    return command.getIdentifier() + ": " + file
        + ": File with given name already exists";
  }

  /**
   * invalidComboOfParams returns the error message of tokens being an
   * invalid combination of parameters
   * 
   * @param command, an instance of Command class or its subclasses
   * @param tokens, array of string tokens holding command arguments
   * @return output, the error message of tokens being an invalid 
   *    combination of parameters
   */
  public static String invalidComboOfParams(Command command, String[] tokens) {
    String output = "";
    output += command.getIdentifier() + ": ";
    for (int i = 1; i < tokens.length; i++) {
      output += tokens[i] + " ";
    }
    output += ": Invalid combination of arguments";
    return output;
  }

  /**
   * invalidPath returns the error message of invalidPath being an invalid
   * path
   * 
   * @param command, an instance of Command class or its subclasses
   * @param invalidPath, a bad path
   * @return returns the error message of invalidPath being an invalidPath
   */
  public static String invalidPath(Command command, String invalidPath) {
    return command.getIdentifier() + ": \"" + invalidPath
        + "\": No such file or directory";
  }

  /**
   * invalidName returns the error message of token being a invalid
   * file and/or directory name
   * 
   * @param command, an instance of Command class or its subclasses
   * @param token, a file or directory name
   * @return the error message of token being an invalid file and/or
   *    directory name
   */
  public static String invalidName(Command command, String token) {
    return command.getIdentifier() + ": \"" + token
        + "\": Invalid file and/or directory name";
  }

  /**
   * childAlreadyExistant returns the error message of a file or directory
   * already existing at node.getPath()
   * 
   * @param directoryName, name of directory
   * @param node, an instance of FileSystemNode that holds the position 
   *    of child node in FileSystem
   * @return  the error of a file or directory already existing at 
   *    node.getPath()
   */
  public static String childAlreadyExistant(String directoryName,
      FileSystemNode node) {
    return "The file/directory " + directoryName + " already exists at "
        + node.getPath();
  }

  /**
   * inappropriatePath returns the error message of givenPath 
   * being inappropriate
   * 
   * @param command, an instance of Command class or its subclasses
   * @param givenPath, the invalid path
   * @return the error of givenPath being inappropriate
   */
  public static String inappropriatePath(Command command, String givenPath) {
    return command.getIdentifier() + ": " + givenPath
        + " contains illicit characters";
  }

  
  /**
   * changeDirectoryIntoFileError returns the error of targetPath refering 
   * to a File so you cannot change directory
   * 
   * @param command, an instance of Command class or its subclasses
   * @param targetPath, a File path
   * @return returns the error
   */
  public static String changeDirectoryIntoFileError(Command command,
      String targetPath) {
    return command.getIdentifier() + ": cannot change directory to file "
        + targetPath;
  }
  
  
  /**
   * RemoveDirectoryError gives the String error of trying to 
   * remove a Directorythat is a subpath to the current directory
   * 
   * @param givenPath, the invalid path
   * @return String error of trying to remove a Directory that is a subpath to
   *         the current directory
   */
  public static String removeDirectoryError(String givenPath) {
    return "The given path: " + givenPath
        + " cannot be removed because it is a "
        + "subpath to the current directory";
  }

  /**
   * subFileSystemNodeError returns the error of targetPath being inside the
   * givenPath
   * 
   * @param command, an instance of Command class or its subclasses
   * @param givenPath, a FileSystemNode path
   * @param targetPath, a FileSystemNode path
   * @return the error of targetPath being inside givenPath
   */
  public static String subFileSystemNodeError(Command command, 
      String givenPath,String targetPath) {
    return command.getIdentifier() + ": could not perform action, " 
      + targetPath + " is inside " + givenPath;
  }

  /**
   * Prints invalidUrl error message
   * 
   * @param command, an instance of Command class or its subclasses
   * @param url, an invalid URL or file
   * @return the error
   */
  public static String invalidUrl(Command command, String url) {
    return command.getIdentifier() + ": \"" + url
        + "\": Invalid valid URL or file found";
  }


  /**
   * copyDirectoryIntoFileError returns an error for invalid copy direcoty
   * 
   * @param command instance of command
   * @param givenPath the file path
   * @param targetPath the target path
   * @return the error of trying to copy a directory into a file path
   */
  public static String copyDirectoryIntoFileError(Command command,
      String givenPath, String targetPath) {
    return command.getIdentifier() + ": cannot copy directory " + givenPath
        + " to a file " + targetPath;
  }
  
  /**
   * copyDirectoryIntoFileError returns an error for a File already existing
   * at givenPath
   * 
   * @param command instance of command
   * @param givenPath the file path
   * @return the error for a File already existing at givenPath
   */
  public static String fileAlreadyExistantAtPath(Command command, 
      String givenPath) {
    return command.getIdentifier() + ": File already exists at " + givenPath;
  }

}
