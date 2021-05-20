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
 * Maintains a tree structure of FileSystemNodes: keeping both the root of the
 * tree and the current position in the tree
 */
public class FileSystem implements java.io.Serializable {

  /**
   * FileSystem has a FileSystem fileSystem, a FileSystemNode root, and a
   * FileSystemNode currentFileSystemNode
   */
  private static FileSystem fileSystem = null;
  private FileSystemNode root;
  private FileSystemNode currentFileSystemNode;

  /**
   * Constructor of FileSystem class. It initializes the root to a new
   * FileSystemNode that has as in its Directory the directoryName "/" and the
   * currentFileSystemNode to the root
   */
  private FileSystem() {
    this.root = new FileSystemNode(new Directory("/"));
    this.currentFileSystemNode = root;
  }


  /**
   * setRoot sets this root to node
   * 
   * @param node, a fileSystemNode
   */
  public void setRoot(FileSystemNode node) {
    this.root = node;
  }

  /**
   * getRoot returns the root of this FileSystem
   * 
   * @return The root of this FileSystem
   */
  public FileSystemNode getRoot() {
    return this.root;
  }


  /**
   * getCurrentDirectory returns the currentFileSystemNode of this FileSystem
   *
   * @return The currentFileSystemNode of this FileSystem
   */
  public FileSystemNode getCurrentDirectory() {
    return this.currentFileSystemNode;
  }


  /**
   * setCurrentDirectory sets the currentFileSystemNode to targetNode
   * 
   * @param targetNode A FileSystemNode
   */
  public void setCurrentDirectory(FileSystemNode targetNode) {
    this.currentFileSystemNode = targetNode;
  }


  /**
   * getFileSystemNode returns the FileSystemNode the givenPath refers to 
   * if its a valid/appropriate path, otherwise, return null and display 
   * an error message
   * 
   * @param givenPath A relative or full path
   * @return The FileSystemNode that givenPath points to
   */
  public FileSystemNode getFileSystemNode(String givenPath) {

    // root
    if (givenPath.equals("/"))
      return getRoot();

    // NEED FOR LIST CONTENTS FIX!!!!!!!!! Andrew D'Amario
    if (givenPath.equals(""))
      return getCurrentDirectory();

    // bad path
    if (inappropriatePath(givenPath))
      return null;

    return traversePath(givenPath);
  }

  /**
   * inappropraitePath returns true and an error message when the givenPath is
   * not an appropriate path (contains illicit characters), or false otherwise
   * 
   * @param givenPath A relative or full path
   * @return true if the given path is inappropriate, otherwise, false
   */
  public boolean inappropriatePath(String givenPath) {

    if (givenPath.indexOf("//") != -1) {
      // ErrorHandler.inappropriatePath(givenPath);
      return true;
    }

    if (givenPath.matches("(.+)?[ !@#$%^&*(){}~|<>?](.+)?")) {
      // ErrorHandler.inappropriatePath(givenPath);
      return true;
    }

    return false;
  }

  /**
   * Checks if the String name has valid format.
   * 
   * @param name string to check if is valid
   * @return true if name is a valid name
   */
  public boolean inappropriateName(String name) {
    return name.matches("(.+)?[ /.!@#$%^&*(){}~|<>?](.+)?");

  }

  /**
   * getSemiFileSystemNode returns the FileSystemNode the givenPath refers to
   * excluding the last entry if its an valid/appropriate path, otherwise,
   * return null and display an error message
   * 
   * @param givenPath A relative or full path
   * @return The FileSystemNode the givenPath refers to excluding the last 
   *    entry which is null if the givenPath is an invalid/inappropriate path
   */
  public FileSystemNode getSemiFileSystemNode(String givenPath) {

    String[] splitPath;
    String targetPath = "";

    if (inappropriatePath(givenPath)) {
      return null;
    }

    if (givenPath.charAt(0) == '/') {
      splitPath = givenPath.substring(1).split("/");
      targetPath = "/";
    } else {
      splitPath = givenPath.split("/");
    }

    // Check if givenPath referred to the current Directory or
    // a Directory at the root
    if (splitPath.length != 1) {

      for (int i = 0; i < splitPath.length - 2; i = i + 1) {
        targetPath = targetPath + splitPath[i] + "/";
      }

      targetPath += splitPath[splitPath.length - 2];

    } else {

      if (givenPath.charAt(0) != '/') {
        targetPath = ".";
      }
    }

    return getFileSystemNode(targetPath);

  }

  /**
   * getPathLastEntry returns the last FileSystemNode the givenPath refers to
   * 
   * @param givenPath A relative or full path
   * @return The last FileSystemNode the givenPath refers to
   */
  public String getPathLastEntry(String givenPath) {

    String[] splitPath;
    
    if (givenPath.equals("/")) {
      return "/";
    }
    
    if (givenPath.charAt(0) == '/') {
      splitPath = givenPath.substring(1).split("/");
    } else {
      splitPath = givenPath.split("/");
    }

    return splitPath[splitPath.length - 1];
  }


  /**
   * traversePath returns the FileSystemNode the givenPath refers to
   * 
   * @param givenPath A relative or full path
   * @return The FileSystemNode the givenPath points to
   */
  private FileSystemNode traversePath(String givenPath) {
    String splitPath[];
    FileSystemNode nodeTracker = null;
    // Check if the givenPath is a full or relative path, set the tracker
    if (givenPath.charAt(0) == '/') {
      splitPath = givenPath.substring(1).split("/");
      nodeTracker = root;
    } else {
      splitPath = givenPath.split("/");
      nodeTracker = currentFileSystemNode;
    }

    for (String singlePath : splitPath) {

      if (singlePath.equals("..")) {
        if (nodeTracker.getParent() != null)
          nodeTracker = nodeTracker.getParent();
        else
          return null;
      } else if (!singlePath.equals(".")) {

        if (nodeTracker.getChildByDirectoryName(singlePath) != null) {
          nodeTracker = nodeTracker.getChildByDirectoryName(singlePath);

        } else {

          return null;

        }
      }
    }
    return nodeTracker;
  }


  /**
   * createFileSystem ensures we only ever have a single FileSystem: if we
   * already have a fileSystem, returns it, if not, creates a new one and
   * returns that one
   * 
   * @return A new fileSystem if there fileSystem was null, otherwise,
   *         fileSystem
   */
  public static FileSystem createFileSystem() {
    if (fileSystem == null)
      fileSystem = new FileSystem();
    return fileSystem;
  }


}
