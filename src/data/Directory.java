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

import java.util.ArrayList;
import java.util.List;

/**
 * Data type that contains a list of Files and has a name/identifier
 */
public class Directory implements java.io.Serializable {

  /*
   * A Directory has a String directoryName and an ArrayList of File: files
   */
  private String directoryName = "";
  private List<File> files = new ArrayList<File>();


  /**
   * Constructor for Directory class. It initializes directoryName to the
   * parameter directoryName
   * 
   * @param directoryName, a String
   */
  public Directory(String directoryName) {
    this.directoryName = directoryName;
  }


  /**
   * setDirectoryName sets the directoryName of this Directory to the given
   * directoryName
   * 
   * @param directoryName, a String
   */
  public void setDirectoryName(String directoryName) {
    this.directoryName = directoryName;
  }


  /**
   * getDirectoryName returns the directoryName of this Directory
   * 
   * @return The directoryName of this Directory
   */
  public String getDirectoryName() {
    return this.directoryName;
  }


  /**
   * addFile adds file to the files of this Directory
   * 
   * @param file, an instance of a File
   */
  public void addFile(File file) {
    // adds the file to the files
    files.add(file);
  }


  /**
   * getFiles returns the files of this Directory
   * 
   * @return The files of this Directory
   */
  public List<File> getFiles() {
    return this.files;
  }

  /**
   * getFileByFileName returns the File in files that has targetFileName 
   * as its fileName
   * 
   * @param targetFileName, a String that refers to a fileName
   * @return currentFIle, the File in files that has the targetFileName 
   *    as its fileName
   */
  public File getFileByFileName(String targetFileName) {
    for (File currentFile : this.files) {
      if (currentFile.getFileName().equals(targetFileName))
        return currentFile;
    }

    return null;
  }


  /**
   * removeFileByFileName removes the File in files that has as its fileName
   * targetFileName
   * 
   * @param targetFileName, a String that refers to a fileName
   */
  public void removeFileByFileName(String targetFileName) {

    int index = 0;

    for (File currentFile : this.files) {
      if (currentFile.getFileName().equals(targetFileName)) {
        this.files.remove(index);
        break;
      }
      index += 1;
    }
  }

  /**
   * isFileInsideByFileName returns true if there is a File in files with
   * fileName targetFileName
   * 
   * @param targetFileName, a String that refers to a fileName
   * @return true if there is a FIle in files with fileName targetFileName
   *    otherwise, false
   */
  public boolean isFileInsideByFileName(String targetFileName) {
    for (File file : this.getFiles()) {
      if (file.getFileName().equals(targetFileName))
        return true;
    }

    return false;
  }

}
