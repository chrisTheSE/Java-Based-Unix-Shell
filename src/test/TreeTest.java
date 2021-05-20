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

import java.lang.reflect.Field;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Command;
import commands.MakeDirectory;
import commands.Redirection;
import commands.Tree;
import data.FileSystem;
import driver.JShell;

public class TreeTest {
  
  private JShell shell;
  private MakeDirectory mkdir = new MakeDirectory();
  private Tree tree = new Tree();
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
  public void runTest1() throws Exception {
    String[] mkdirTokens = {"mkdir", "a", "b", "c"};
    mkdir.run(mkdirTokens, shell);

    String[] treeTokens = {"tree"};
    Command theResultingCommand = tree.run(treeTokens, shell);
    String actualOutput = theResultingCommand.getOutput();
    String actualErrors = theResultingCommand.getErrors();
    
    assertEquals("/\n  a\n  b\n  c",actualOutput);
    assertEquals(null, actualErrors);
  }
  
  
  @Test
  public void runTest2() throws Exception {
    
    String[] mkdirTokens = {"mkdir", "kha", "bruh", "c", "bruh/banana", 
        "/bruh/apple"};
    mkdir.run(mkdirTokens, shell);
    
    String[] treeTokens = {"tree"};
    Command theResultingCommand = tree.run(treeTokens, shell);
    String actualOutput = theResultingCommand.getOutput();
    String actualErrors = theResultingCommand.getErrors();
    
    assertEquals("/\n  kha\n  bruh\n    banana\n    apple\n  c", 
        actualOutput);
    assertEquals(null, actualErrors);
  }
  
  
  @Test
  public void runTest3() throws Exception {

    String[] mkdirTokens = {"mkdir", "a", "b", "c", "b/banana", "/c/apple"};
    mkdir.run(mkdirTokens, shell);
    
    Redirection redirectionCommand = new Redirection();
    String[] redirectionTokens = {"redirect", "\"banana\"", ">", "ooga"};
    redirectionCommand.run(redirectionTokens, shell);

    String[] treeTokens = {"tree"};
    Command theResultingCommand = tree.run(treeTokens, shell);
    String actualOutput = theResultingCommand.getOutput();
    String actualErrors = theResultingCommand.getErrors();
    
    assertEquals("/\n  a\n  b\n    banana\n  c\n    apple\n  ooga", 
        actualOutput);
    assertEquals(null, actualErrors);
  }
  
  @Test
  public void runTest4() throws Exception {

    String[] mkdirTokens = {"mkdir", "a", "b", "c", "b/banana", "/c/apple"};
    mkdir.run(mkdirTokens, shell);
    
    
    String[] redirectionTokens1 = {"redirect", "\"banana\"", ">", "ooga"};
    redirection.run(redirectionTokens1, shell);

    String[] redirectionTokens2 = {"redirect", "\"apple\"", ">", "b/hue"};
    redirection.run(redirectionTokens2, shell);
    
    String[] treeTokens = {"tree"};
    Command theResultingCommand = tree.run(treeTokens, shell);
    String actualOutput = theResultingCommand.getOutput();
    String actualErrors = theResultingCommand.getErrors();
    
    assertEquals("/\n  a\n  b\n    banana\n    hue\n  c\n    apple\n  ooga", 
        actualOutput);
    assertEquals(null, actualErrors);
  }
  
  
  @Test
  public void runTest5() throws Exception {

    String[] treeTokens = {"tree"};
    Command theResultingCommand = tree.run(treeTokens, shell);
    String actualOutput = theResultingCommand.getOutput();
    String actualErrors = theResultingCommand.getErrors();
    
    assertEquals("/",actualOutput);
    assertEquals(null, actualErrors);
  }
  
}
