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
package data;


/**
 * Data type that contains a string and has a name/identifier
 */
public class File implements java.io.Serializable {

  /*
   * A File has two Strings fileName and content
   */
  private String fileName = "";
  private String content = "";

  /**
   * Constructor for File class. It initializes fileName to the parameter
   * fileName
   * 
   * @param fileName, a String
   */
  public File(String fileName) {
    this.fileName = fileName;
  }

  /**
   * setContent sets the content of this File to the parameter content
   *
   * @param content, a String
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * setFileName sets the fileName of this File to the parameter fileName
   *
   * @param fileName, a String
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * getFileName returns the fileName of this File
   *
   * @return The fileName of this File
   */
  public String getFileName() {
    return this.fileName;
  }

  /**
   * getContent returns the content of this File
   * 
   * @return The content of this file
   */
  public String getContent() {
    return this.content;
  }

}
