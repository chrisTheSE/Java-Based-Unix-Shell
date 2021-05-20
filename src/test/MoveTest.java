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
import commands.Command;
import commands.Concatenate;
import commands.MakeDirectory;
import commands.Move;
import commands.Redirection;
import commands.Tree;
import data.FileSystem;
import driver.JShell;

public class MoveTest {

  private JShell shell;
  private MakeDirectory mkdir = new MakeDirectory();
  private Tree tree = new Tree();
  private ChangeDirectory cd = new ChangeDirectory();
  private Redirection redirection = new Redirection();
  private Move mv = new Move();
  private Concatenate cat = new Concatenate();
  
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
    String[] cpTokens = {"mv", "/", "/"};
    Command theResultingCommand = mv.run(cpTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals("mv: could not perform action, / is inside /", actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/",actualOutput);
  }
  
  @Test
  public void runTest2() {
    String[] mkdirTokens = {"mkdir", "dir1", "dir2", "dir3"};
    mkdir.run(mkdirTokens, shell);
    
    String[] redirectionTokens1 = {"redirect", "\"yoko\"", ">", "bomb"};
    redirection.run(redirectionTokens1, shell);
    
    String[] redirectionTokens2 = {"redirect", "\"trantaran\"", ">", "hhh"};
    redirection.run(redirectionTokens2, shell);
    
    String[] mvTokens = {"mv", "dir3", "dir2"};
    Command theResultingCommand = mv.run(mvTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals(null, actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  dir1\n  dir2\n    dir3\n  bomb\n  hhh", 
        actualOutput);
  }
  
  
  @Test
  public void runTest3() {
    String[] mkdirTokens = {"mkdir", "dir1", "dir2", "dir3", "dir1/banana", 
       "dir2/orange"};
    mkdir.run(mkdirTokens, shell);
    
    String[] redirectionTokens1 = {"redirect", "\"banana\"", ">", "ooga"};
    redirection.run(redirectionTokens1, shell);
    
    String[] redirectionTokens2 = {"redirect", "\"heyoah\"", ">", "dir1/aaa"};
    redirection.run(redirectionTokens2, shell);
    
    String[] mvTokens = {"mv", "dir1", "dir2"};
    Command theResultingCommand = mv.run(mvTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals(null, actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  dir2\n    orange\n    dir1\n      banana\n      "
        + "aaa\n  dir3\n  ooga", actualOutput);
  }
  
  
  @Test
  public void runTest4() {
    String[] mkdirTokens = {"mkdir", "dir1", "dir2", "dir3", "dir1/banana", 
       "dir2/orange"};
    mkdir.run(mkdirTokens, shell);
    
    String[] redirectionTokens1 = {"redirect", "\"banana\"", ">", "ooga"};
    redirection.run(redirectionTokens1, shell);
    
    String[] redirectionTokens2 = {"redirect", "\"heyoah\"", ">", "dir1/ooga"};
    redirection.run(redirectionTokens2, shell);
    
    String[] mvTokens = {"mv", "/ooga", "/dir1"};
    Command theResultingCommand = mv.run(mvTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals(null, actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    String[] catTokens = {"cat", "/dir1/ooga"};
    Command theCheckFileCommand = cat.run(catTokens, shell);
    String theFileContent = theCheckFileCommand.getOutput();
    
    assertEquals("/\n  dir1\n    banana\n    ooga\n  dir2\n    "
        + "orange\n  dir3", actualOutput);
    assertEquals("banana", theFileContent);
  }
  
  
  @Test
  public void runTest5() {
    String[] mkdirTokens = {"mkdir", "dir1", "dir2", "dir3", "dir1/banana", 
       "dir2/orange"};
    mkdir.run(mkdirTokens, shell);
    
    String[] redirectionTokens1 = {"redirect", "\"banana\"", ">", "ooga"};
    redirection.run(redirectionTokens1, shell);
    
    String[] redirectionTokens2 = {"redirect", "\"heyoah\"", ">", "dir1/ooga"};
    redirection.run(redirectionTokens2, shell);
    
    String[] cdTokens = {"cd", "dir3"};
    cd.run(cdTokens, shell);
    
    String[] mvTokens = {"mv", "/ooga", "/dir1/risa"};
    Command theResultingCommand = mv.run(mvTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals(null, actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    String[] catTokens = {"cat", "/dir1/risa"};
    Command theCheckFileCommand = cat.run(catTokens, shell);
    String theFileContent = theCheckFileCommand.getOutput();
    
    assertEquals("/\n  dir1\n    banana\n    ooga\n    risa\n  "
        + "dir2\n    orange\n  dir3", actualOutput);
    assertEquals("banana", theFileContent);
  }
  
  
  @Test
  public void runTest6() {
    String[] mkdirTokens = {"mkdir", "dir1", "dir2", "dir3", "dir1/banana", 
       "dir2/orange"};
    mkdir.run(mkdirTokens, shell);
    
    String[] redirectionTokens1 = {"redirect", "\"banana\"", ">", "ooga"};
    redirection.run(redirectionTokens1, shell);
    
    String[] redirectionTokens2 = {"redirect", "\"heyoah\"", ">", "dir1/ooga"};
    redirection.run(redirectionTokens2, shell);
    
    String[] cdTokens = {"cd", "dir3"};
    cd.run(cdTokens, shell);
    
    String[] mvTokens = {"mv", "/wtf/is/this", "/dir1"};
    Command theResultingCommand = mv.run(mvTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals("mv: \"/wtf/is/this\": No such file or directory", 
        actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  dir1\n    banana\n    ooga\n  "
        + "dir2\n    orange\n  dir3\n  ooga", actualOutput);
  }
  
  
  @Test
  public void runTest7() {
    String[] mkdirTokens = {"mkdir", "dir1", "dir2", "dir3", "dir1/banana", 
       "dir2/orange"};
    mkdir.run(mkdirTokens, shell);

    String[] redirectionTokens2 = {"redirect", "\"heyoah\"", ">", "dir1/ooga"};
    redirection.run(redirectionTokens2, shell);
    
    String[] cdTokens = {"cd", "dir3"};
    cd.run(cdTokens, shell);
    
    String[] mvTokens = {"mv", "/dir1", "/wtf"};
    Command theResultingCommand = mv.run(mvTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals(null, 
        actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  dir2\n    orange\n  dir3\n  wtf\n    banana\n    ooga", 
        actualOutput);
  }
  
  
  @Test
  public void runTest8() {
    String[] mkdirTokens = {"mkdir", "dir1", "dir2", "dir3", "dir1/banana", 
       "dir2/orange"};
    mkdir.run(mkdirTokens, shell);
    
    String[] redirectionTokens1 = {"redirect", "\"banana\"", ">", "ooga"};
    redirection.run(redirectionTokens1, shell);
    
    String[] redirectionTokens2 = {"redirect", "\"heyoah\"", ">", "dir1/ooga"};
    redirection.run(redirectionTokens2, shell);
    
    String[] cdTokens = {"cd", "dir3"};
    cd.run(cdTokens, shell);
    
    String[] mvTokens = {"mv", "/dir1", "/ooga"};
    Command theResultingCommand = mv.run(mvTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals("mv: cannot move directory at /dir1 because /ooga "
        + "refers to a file", actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  dir1\n    banana\n    ooga\n  "
        + "dir2\n    orange\n  dir3\n  ooga", actualOutput);
  }
}
