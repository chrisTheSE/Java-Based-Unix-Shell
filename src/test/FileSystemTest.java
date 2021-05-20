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
import data.FileSystem;
import data.FileSystemNode;
import driver.JShell;

public class FileSystemTest {

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
    Field field = (shell.getfSystem().getClass()).
        getDeclaredField("fileSystem");
    field.setAccessible(true);
    field.set(null, null);
  }
  
  @Test
  public void getFileSystemNodeTest1() {
    String[] mkdirTokens = {"mkdir", "bramble", "kha", "yessir"};
    mkdir.run(mkdirTokens, shell);
    
    String[] cdTokens = {"cd", "kha"};
    cd.run(cdTokens, shell);
    
    FileSystemNode actualOutput = fSystem.getFileSystemNode("nani");
    
    assertEquals(null, actualOutput);
    
  }
  
  @Test
  public void getFileSystemNodeTest2() {
    String[] mkdirTokens = {"mkdir", "braaaaaaah", "nani", "omegalol"};
    mkdir.run(mkdirTokens, shell);
    
    FileSystemNode actualOutput = fSystem.getFileSystemNode("nani");
    
    FileSystemNode expectedOutput = fSystem.getCurrentDirectory().
        getChildByDirectoryName("nani");
    
    assertEquals(expectedOutput, actualOutput);
    
  }
  
  
  @Test
  public void getFileSystemNodeTest3() {
    String[] mkdirTokens = {"mkdir", "braaaaaaah", "nani", "kakashi", 
        "nani/tenia", "nani/banana", "nani/banana/supreme"};
    mkdir.run(mkdirTokens, shell);
    
    String[] cdTokens1 = {"cd", "nani"};
    cd.run(cdTokens1, shell);
    
    FileSystemNode actualOutput = fSystem.getFileSystemNode("banana/supreme");
    
    FileSystemNode expectedOutput = fSystem.getCurrentDirectory().
        getChildByDirectoryName("banana").getChildByDirectoryName("supreme");
    
    assertEquals(expectedOutput, actualOutput);
    
  }
  
  
  @Test
  public void getFileSystemNodeTest4() {
    String[] mkdirTokens = {"mkdir", "braaaaaaah", "nani", "kakashi", 
        "nani/tenia", "nani/banana", "nani/banana/supreme", "kakashi/weeb"};
    mkdir.run(mkdirTokens, shell);
    
    String[] cdTokens1 = {"cd", "nani"};
    cd.run(cdTokens1, shell);
    
    FileSystemNode actualOutput = fSystem.getFileSystemNode("/kakashi/weeb");
    
    FileSystemNode expectedOutput = fSystem.getRoot().
        getChildByDirectoryName("kakashi").getChildByDirectoryName("weeb");
    
    assertEquals(expectedOutput, actualOutput);
    
  }
  
  @Test
  public void getFileSystemNodeTest5() {
    String[] mkdirTokens = {"mkdir", "braaaaaaah", "nani", "kakashi", 
        "nani/tenia", "nani/banana", "nani/banana/supreme", "kakashi/weeb"};
    mkdir.run(mkdirTokens, shell);
    
    String[] cdTokens1 = {"cd", "nani"};
    cd.run(cdTokens1, shell);
    
    FileSystemNode actualOutput = fSystem.getFileSystemNode("./././..");
    
    FileSystemNode expectedOutput = fSystem.getRoot();
    
    assertEquals(expectedOutput, actualOutput);
    
  }
  
  
  @Test
  public void getSemiFileSystemNodeTest1() {
    String[] mkdirTokens = {"mkdir", "braaaaaaah", "nani", "kakashi", 
        "nani/tenia", "nani/banana", "nani/banana/supreme", "kakashi/weeb"};
    mkdir.run(mkdirTokens, shell);
    
    String[] cdTokens1 = {"cd", "nani"};
    cd.run(cdTokens1, shell);
    
    FileSystemNode actualOutput = fSystem.getSemiFileSystemNode("./././..");
    
    FileSystemNode expectedOutput = fSystem.getCurrentDirectory();
    
    assertEquals(expectedOutput, actualOutput);
    
  }
  
  
  @Test
  public void getSemiFileSystemNodeTest2() {
    String[] mkdirTokens = {"mkdir", "braaaaaaah", "nani", "kakashi", 
        "nani/tenia", "nani/banana", "nani/banana/supreme", "kakashi/weeb",
        "/nani/banana/surprise"};
    mkdir.run(mkdirTokens, shell);
    
    String[] cdTokens1 = {"cd", "nani"};
    cd.run(cdTokens1, shell);
    
    FileSystemNode actualOutput = fSystem.getSemiFileSystemNode(
        "banana/surprise");
    
    FileSystemNode expectedOutput = fSystem.getCurrentDirectory().
        getChildByDirectoryName("banana");
    
    assertEquals(expectedOutput, actualOutput);
    
  }
  
  @Test
  public void getSemiFileSystemNodeTest3() {
    String[] mkdirTokens = {"mkdir", "braaaaaaah", "nani", "kakashi", 
        "nani/tenia", "nani/banana", "nani/banana/supreme", "kakashi/weeb",
        "/nani/banana/surprise"};
    mkdir.run(mkdirTokens, shell);
    
    String[] cdTokens1 = {"cd", "nani"};
    cd.run(cdTokens1, shell);
    
    FileSystemNode actualOutput = fSystem.getSemiFileSystemNode(
        "banana/impossible/path");
    
    assertEquals(null, actualOutput);
  }

}
