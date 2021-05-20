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

import java.util.List;
import java.util.ArrayList;

/**
 * Data type of the tree structure required for a FileSystem
 */
public class FileSystemNode implements java.io.Serializable {

  /**
   * A FileSystemNode has a Directory directory, an ArrayList of 
   * FileSystemNodes called children and a FileSystemNode parent
   */
  private Directory directory;
  private List<FileSystemNode> children = new ArrayList<FileSystemNode>();
  private FileSystemNode parent = null;

  /**
   * Constructor of FileSystemNode. It initializes the directory in this
   * FileSystemNode to the parameter directory
   * 
   * @param directory, an instance of a Directory
   */
  public FileSystemNode(Directory directory) {
    this.directory = directory;
  }

  /**
   * addChild adds a FileSystemnode child to the children of this 
   * FileSystemNode
   * 
   * @param child, an instance of a FileSystemNode
   */
  public void addChild(FileSystemNode child) {

    // Set the parent of this child to be this FileSystem
    child.setParent(this);

    // Add the child to the children of this FIleSystemNode
    this.children.add(child);

  }

  /**
   * getChildren returns the children of this FileSystemNode
   * 
   * @return The children of this FileSystemNode
   */
  public List<FileSystemNode> getChildren() {
    return this.children;
  }

  /**
   * setParent sets the parent of this FileSystemNode to the 
   * paramaeter parent
   * 
   * @param parent, an instance of a FileSystemNode
   */
  public void setParent(FileSystemNode parent) {
    this.parent = parent;
  }

  /**
   * FileSystemNode returns the parent of this FileSystemNode
   * 
   * @return The parent of this FileSystemNode
   */
  public FileSystemNode getParent() {
    return this.parent;
  }

  /**
   * addFiles adds file to the directory of this FileSystemNode
   * 
   * @param file, an instance of a File
   */
  public void addFile(File file) {
    this.directory.addFile(file);
  }

  /**
   * getDirectory returns the directory of this FileSystemNode
   * 
   * @return The directory of this FileSystemNode
   */
  public Directory getDirectory() {
    return this.directory;
  }

  /**
   * setDirectory sets the directory of this FileSystemNode to the given
   * directory
   * 
   * @param directory, an instance of a Directory
   */
  public void setDirectory(Directory directory) {
    this.directory = directory;
  }

  /**
   * getPath returns the path of this FileSystemNode
   * 
   * @return The path of this FileSystemNode which is a String
   */
  public String getPath() {

    String path = this.directory.getDirectoryName();

    FileSystemNode currentFileSystemNode = this.parent;

    // Traverse through the generations before this node until the root
    while (currentFileSystemNode != null) {

      // Check if the currentFileSystemNode has a parent
      if (currentFileSystemNode.getParent() != null) {

        // If it has a parent, add currentFileSystemNode's directory's
        // directoryName with a "/" to path
        path = currentFileSystemNode.directory.getDirectoryName() + "/" + path;

      } else {

        // If it deosn't have just add the currentFileSystemNode's
        // directory's directoryName to path
        path = currentFileSystemNode.directory.getDirectoryName() + path;
      }

      // Set the curretnFileSystemNode to be the parent
      currentFileSystemNode = currentFileSystemNode.getParent();
    }

    return path;
  }

  /**
   * isChildInsideByDirectoryName returns true if one of the children of this
   * FileSystemNodehas has as its directoryName the given directoryName,
   * otherwise, false
   * 
   * @param directoryName, a String that refers to a directoryName of a
   *        Directory
   * @return true if one of the children of this FileSystemNode has the given
   *         directoryName, otherwise, false
   */
  public boolean isChildInsideByDirectoryName(String directoryName) {
    for (FileSystemNode child : this.children) {
      if (child.getDirectory().getDirectoryName().equals(directoryName))
        return true;
    }
    return false;
  }


  /**
   * removeChildByDirectoryName removes the directory from the children 
   * that has its directoryName as directoryName, if there is none, do nothing.
   * 
   * @param directoryName, a String that refers to a directoryName of a
   *        Directory
   */
  public void removeChildByDirectoryName(String directoryName) {
    int index = 0;
    for (FileSystemNode child : this.getChildren()) {
      if (child.getDirectory().getDirectoryName().equals(directoryName)) {
        this.getChildren().remove(index);
        break;
      }
      index += 1;
    }
  }


  /**
   * getChildByDirectoryName gets a directory from the children that has
   * directoryName as its directoryName, if there are none, return null
   * 
   * @param directoryName, a String that refers to a directoryName of a
   *        Directory
   * @return the FIleSystemNode in children that has as its directoryName the
   *         given directoryName
   */
  public FileSystemNode getChildByDirectoryName(String directoryName) {
    for (FileSystemNode child : this.children) {
      if (child.getDirectory().getDirectoryName().equals(directoryName))
        return child;
    }
    return null;
  }

  /**
   * cloneFileSystemNodeInto clones this FileSystemNode and puts it in
   * toBeClonedFileSystemNode, if toBeClonedFileSystemNode is null, 
   * return null.
   * 
   * @param toBeClonedFileSystemNode, a FileSystemNode
   * @return tobeClonedFIleSystemNode the cloned version of this 
   *    FileSystemNode
   */
  public FileSystemNode cloneFileSystemNodeInto(
      FileSystemNode toBeClonedFileSystemNode) {

    if (toBeClonedFileSystemNode != null) {
      toBeClonedFileSystemNode.setDirectory(this.getDirectory());
      toBeClonedFileSystemNode.setParent(this.getParent());

      for (FileSystemNode child : this.getChildren()) {
        toBeClonedFileSystemNode.addChild(child.cloneFileSystemNodeInto(
            new FileSystemNode(new Directory("Dummy"))));
      }
    }
    return toBeClonedFileSystemNode;

  }

}
