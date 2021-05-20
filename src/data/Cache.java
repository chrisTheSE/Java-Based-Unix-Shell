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

import java.util.LinkedList;
import java.util.List;

/**
 * Stores the history and directory stack of the current session of the shell.
 */
public class Cache implements java.io.Serializable {

  // history
  private List<String> history = new LinkedList<String>();

  // directory stack
  private LinkedList<String> dirStack = new LinkedList<String>();


  /**
   * add user input line to the history list
   * 
   * @param line, string of user input line too add to history
   */
  public void addHistoryLine(String line) {
    history.add(line);
  }

  /**
   * Get history line at given index.
   * 
   * @param i, index of line to get from history
   * @return returns string of the history line at index i
   */
  public String getHistory(int i) {
    if (i >= 0 && i < history.size())
      return history.get(i);
    else
      return null;
  }

  /**
   * Get size of history list.
   * 
   * @return returns integer of the size of the history list.
   */
  public int getHistorySize() {
    return history.size();
  }

  /**
   * Pop the top directory from the directory stack.
   * 
   * @return returns the popped directory
   */
  public String popDirectoryStack() {
    if (dirStack.size() > 0)
      return dirStack.pop();
    else
      return null;
  }

  /**
   * Push given directory to the directory stack.
   * 
   * @param path, string of path to push to the directory stack
   */
  public void pushDirectoryStack(String path) {
    dirStack.push(path);
  }

  /**
   * Remove all the paths in dirStack that are path or sub-directories of path.
   * 
   * @param path, path of directory to be removed
   */
  public void removeDirectory(String path) {
    int end;
    String stackPath;
    for (int i = 0; i < dirStack.size(); i++) {
      stackPath = dirStack.get(i);
      end = path.length() > stackPath.length() ? stackPath.length()
          : path.length();
      if (path.equals(stackPath.substring(0, end))) {
        dirStack.remove(i);
      }
    }
  }
}
