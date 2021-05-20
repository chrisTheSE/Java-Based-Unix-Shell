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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import data.FileSystem;
import driver.JShell;
import io.StandardOutput;
import runtime.ErrorHandler;

/**
 * Takes a url and then creates a file in the current directory containing the
 * contents of the file at the given url
 * 
 * @author chris
 *
 */
public class ClientUrl extends Command {

  /**
   * Constructor for ClientUrl class. It initializes identifier,
   * maxNumOfArguments, minNumOfArguments errorTooManyArguments, missingOperand,
   * and description from its super class Commands.
   */
  public ClientUrl() {
    this.setIdentifier("curl");
    this.setDescription("Retrieve the file at that URL\r\n"
        + "and add it to the current working directory");
    this.setMaxNumOfArguments(2);
    this.setMinNumOfArguments(2);
    this.setErrorTooManyArguments("Too many arguments");
    this.setMissingOperand("Enter a URL");
  }

  /**
   * Takes in a url, retrieves the file at url and then returns the files
   * contents
   * 
   * @param url the url of a file to be retrieved
   * @return returns the contents of file at url
   */
  private String getFileContent(String url) {
    try {
      String output = "";
      String curLine = "";
      // Make the given file url into a URL object
      URL fileUrl = new URL(url);
      // Open a url connection to the website storing the file
      URLConnection fileConnect = fileUrl.openConnection();
      // Turns input stream from fileConnect into an inputStreamReader object
      InputStreamReader reader =
          new InputStreamReader(fileConnect.getInputStream());
      // Takes in reader and turns read into a buffered reader
      BufferedReader fileReader = new BufferedReader(reader);

      curLine = fileReader.readLine();
      while (curLine != null) {
        output += curLine + "\n";
        curLine = fileReader.readLine();
      }

      fileReader.close();

      return output;
    } catch (IOException e) {
      this.setErrors(ErrorHandler.invalidUrl(this, url));
      return null;
    }
  }



  /**
   * Retrieves file at given URL and inserts at current directory.
   * 
   * @param tokens, array of string tokens holding command arguments
   * @param shell an instance of JShell
   * @return returns an instance of command with output and errors
   */
  @Override
  public Command run(String[] tokens, JShell shell) {
    FileSystem fSystem = shell.getfSystem();
    String output = getFileContent(tokens[1]);

    if (output != null) {
      String fileName = fSystem.getPathLastEntry(tokens[1].replace(".", ""));

      if (fSystem.getCurrentDirectory().getDirectory()
          .getFileByFileName(fileName) == null) {
        String[] tokens2 = {"default", ">", fileName};
        StandardOutput.println(tokens2, output, shell, this);
      } else {
        this.setErrors(ErrorHandler.fileAlreadyExist(this, fileName));
      }
    }

    return this;
  }
  // Test: curl http://www.cs.cmu.edu/~spok/grimmtmp/073.txt
}
