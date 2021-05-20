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

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.ChangeDirectory;
import commands.Command;
import commands.MakeDirectory;
import commands.Redirection;
import commands.Tree;
import data.FileSystem;
import driver.JShell;

public class MakeDirectoryTest {
  
  private JShell shell;
  private MakeDirectory mkdir = new MakeDirectory();
  private Tree tree = new Tree();
  private ChangeDirectory cd = new ChangeDirectory();
  private Redirection redirection = new Redirection();
  
  @Before
  public void setUp() throws Exception
  {
    shell = new JShell();
    shell.setfSystem(FileSystem.createFileSystem()); 
  }
  
  
  @After
  public void tearDown() throws Exception
  {
    Field field = (shell.getfSystem().getClass())
        .getDeclaredField("fileSystem");
    field.setAccessible(true);
    field.set(null, null);
  }
  
  
  @Test
  public void runTest1() {
    String[] mkdirTokens = {"mkdir", "dir1", "dir2", "dir3"};
    Command theResultingCommand = mkdir.run(mkdirTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals(null, actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  dir1\n  dir2\n  dir3", actualOutput);
  }
  
  @Test
  public void runTest2() {
    String[] mkdirTokens1 = {"mkdir", "a", "b", "c"};
    Command theResultingCommand1 = mkdir.run(mkdirTokens1, shell);
    String actualErrors1 = theResultingCommand1.getErrors();
    assertEquals(null, actualErrors1);
    
    String[] cdTokens = {"cd", "a"};
    cd.run(cdTokens, shell);
    
    String[] mkdirTokens2 = {"mkdir", "a", "b", "c"};
    Command theResultingCommand2 = mkdir.run(mkdirTokens2, shell);
    String actualErrors2 = theResultingCommand2.getErrors();
    assertEquals(null, actualErrors2);
    
    String[] treeTokens = {"tree"};
    Command theResultingCommand = tree.run(treeTokens, shell);
    String actualOutput = theResultingCommand.getOutput();
    
    assertEquals("/\n  a\n    a\n    b\n    c\n  b\n  c", actualOutput);
  }
  
  
  @Test
  public void runTest3() {
    String[] mkdirTokens = {"mkdir", "/a", "/b", "/c", "/a/banana", 
        "/b/apple", "/a/banana/split", "/a/orange"};
    Command theResultingCommand = mkdir.run(mkdirTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals(null, actualErrors);
    
    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  a\n    banana\n      split\n    orange\n  b\n    "
        + "apple\n  c", actualOutput);
  }
  
  
  @Test
  public void runTest4() {
    String[] mkdirTokens1 = {"mkdir", "a", "b", "c"};
    Command theResultingCommand1 = mkdir.run(mkdirTokens1, shell);
    String actualErrors1 = theResultingCommand1.getErrors();
    assertEquals(null, actualErrors1);
    
    String[] cdTokens = {"cd", "a"};
    cd.run(cdTokens, shell);
    
    String[] mkdirTokens2 = {"mkdir", "/a/banana", "/d", "hue", "banana/yolo", 
        "/d/hai"};
    Command theResultingCommand2 = mkdir.run(mkdirTokens2, shell);
    String actualErrors2 = theResultingCommand2.getErrors();
    assertEquals(null, actualErrors2);
    
    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  a\n    banana\n      yolo\n    hue\n  b\n  c\n  "
        + "d\n    hai", actualOutput);
  }
  
  
  @Test
  public void runTest5() {
    String[] mkdirTokens1 = {"mkdir", "a", "b", "a"};
    Command theResultingCommand = mkdir.run(mkdirTokens1, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals("The file/directory " + "a" + " already exists at "
        + "/", actualErrors);
    
    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  a\n  b", actualOutput);
  }
  
  
  @Test
  public void runTest6() {
    String[] mkdirTokens1 = {"mkdir", "kha", "kha/k", "jh" ,
        "non/Existant/path", "bomp"};
    Command theResultingCommand = mkdir.run(mkdirTokens1, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals("mkdir" + ": \"" + "non/Existant/path" 
        + "\": No such file or directory", actualErrors);
    
    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  kha\n    k\n  jh", actualOutput);
  }
  
  
  @Test
  public void runTest7() {
    String[] mkdirTokens1 = {"mkdir", "jue", "illegal%^&path", "noice"};
    Command theResultingCommand = mkdir.run(mkdirTokens1, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals("mkdir" + ": " + "illegal%^&path" 
        + " contains illicit characters", actualErrors);
    
    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  jue", actualOutput);
  }
  
  
  @Test
  public void runTest8() {
    String[] mkdirTokens1 = {"mkdir", "kul", "jue/bad%&Name", "didIT"};
    Command theResultingCommand = mkdir.run(mkdirTokens1, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals("mkdir" + ": " + "jue/bad%&Name" 
        + " contains illicit characters", actualErrors);
    
    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  kul", actualOutput);
  }
  
  
  @Test
  public void runTest9() {
    
    String[] redirectionTokens1 = {"redirect", "\"damnnnnlet\"", ">", "sugoi"};
    redirection.run(redirectionTokens1, shell);
    
    String[] mkdirTokens1 = {"mkdir", "clean", "clean/sugoi", "sugoi"};
    Command theResultingCommand = mkdir.run(mkdirTokens1, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals("The file/directory " + "sugoi" + " already exists at "
        + "/", actualErrors);
    
    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  clean\n    sugoi\n  sugoi", actualOutput);
  }
  
}
