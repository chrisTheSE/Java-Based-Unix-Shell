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
package test;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.ChangeDirectory;
import commands.MakeDirectory;
import data.Directory;
import data.FileSystem;
import data.FileSystemNode;
import driver.JShell;

public class FileSystemNodeTest {

  private JShell shell;
  private MakeDirectory mkdir = new MakeDirectory();
  private ChangeDirectory cd = new ChangeDirectory();
  FileSystem fSystem;
  
  @Before
  public void setUp() throws Exception
  {
    shell = new JShell();
    shell.setfSystem(FileSystem.createFileSystem()); 
    fSystem = shell.getfSystem();
  }
  
  @After
  public void tearDown() throws Exception
  {
    Field field = (shell.getfSystem().getClass()).getDeclaredField(
        "fileSystem");
    field.setAccessible(true);
    field.set(null, null);
  }

  @Test
  public void getPathTest1() {
    
    String actualOutput = fSystem.getRoot().getPath();
    
    assertEquals("/", actualOutput);
    
  }
  
  
  @Test
  public void getPathTest2() {
    String[] mkdirTokens = {"mkdir", "bokay", "nani", "hai"};
    mkdir.run(mkdirTokens, shell);
    
    String[] cdTokens1 = {"cd", "nani"};
    cd.run(cdTokens1, shell);
    
    String actualOutput = fSystem.getCurrentDirectory().getPath();

    assertEquals("/nani", actualOutput);
    
  }
  
  
  @Test
  public void getPathTest3() {
    String[] mkdirTokens = {"mkdir", "braaaaaaah", "nani", "kakashi", 
        "nani/tenia", "nani/banana", "nani/banana/supreme", "kakashi/weeb",
        "/nani/banana/surprise"};
    mkdir.run(mkdirTokens, shell);
    
    String[] cdTokens1 = {"cd", "nani/banana/supreme"};
    cd.run(cdTokens1, shell);
    
    String actualOutput = fSystem.getCurrentDirectory().getPath();
    
    assertEquals("/nani/banana/supreme", actualOutput);
  }
  
  
  @Test
  public void cloneFileSystemNodeTest1() {
    
    FileSystemNode actualOutput = new FileSystemNode(new Directory("Dummy"));
    actualOutput = fSystem.getRoot().cloneFileSystemNodeInto(actualOutput);
    
    assertEquals("/", actualOutput.getDirectory().getDirectoryName());
    assertEquals(null, actualOutput.getParent());
  }
  
  
  @Test
  public void cloneFileSystemNodeTest2() {
    String[] mkdirTokens = {"mkdir", "braaaaaaah", "nani", "kakashi", 
        "nani/tenia", "nani/banana", "nani/tenia/tango"};
    mkdir.run(mkdirTokens, shell);
    
    FileSystemNode actualOutput = new FileSystemNode(new Directory("Dummy"));
    actualOutput = fSystem.getRoot().getChildByDirectoryName("nani").
        cloneFileSystemNodeInto(actualOutput);
    
    assertEquals("nani", actualOutput.getDirectory().getDirectoryName());
    assertEquals(true, actualOutput.isChildInsideByDirectoryName("tenia"));
    assertEquals(true, actualOutput.isChildInsideByDirectoryName("banana"));
    assertEquals(true, actualOutput.getChildByDirectoryName("tenia").
        isChildInsideByDirectoryName("tango"));
  }
}
