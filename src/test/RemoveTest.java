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
import commands.MakeDirectory;
import commands.Redirection;
import commands.Remove;
import commands.Tree;
import data.FileSystem;
import driver.JShell;

public class RemoveTest {
  
  private JShell shell;
  private MakeDirectory mkdir = new MakeDirectory();
  private Tree tree = new Tree();
  private ChangeDirectory cd = new ChangeDirectory();
  private Redirection redirection = new Redirection();
  private Remove rm = new Remove();
  
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
    mkdir.run(mkdirTokens, shell);
    
    String[] rmTokens = {"rm", "dir2"};
    Command theResultingCommand = rm.run(rmTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals(null, actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  dir1\n  dir3", actualOutput);
  }
  
  
  @Test
  public void runTest2() {
    String[] mkdirTokens = {"mkdir", "dir1", "dir2", "dir3"};
    mkdir.run(mkdirTokens, shell);
    
    String[] redirectionTokens1 = {"redirect", "\"banana\"", ">", "ooga"};
    redirection.run(redirectionTokens1, shell);
    
    String[] redirectionTokens2 = {"redirect", "\"heyheyhey\"", ">", "ka"};
    redirection.run(redirectionTokens2, shell);
    
    String[] rmTokens = {"rm", "ka"};
    Command theResultingCommand = rm.run(rmTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals(null, actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  dir1\n  dir2\n  dir3\n  ooga", actualOutput);
  }
  
  
  @Test
  public void runTest3() {
    String[] mkdirTokens = {"mkdir", "dir1", "dir2", "dir3", "dir1/heyo", 
        "dir2/lala", "dir1/heyo/kola" , "dir1/nani"};
    mkdir.run(mkdirTokens, shell);
    
    String[] redirectionTokens1 = {"redirect", "\"banana\"", ">", "ooga"};
    redirection.run(redirectionTokens1, shell);
    
    String[] redirectionTokens2 = {"redirect", "\"heyheyhey\"", ">", 
        "dir1/nihao"};
    redirection.run(redirectionTokens2, shell);
    
    String[] redirectionTokens3 = {"redirect", "\"zyayafgafg\"", ">", 
    "dir1/heyo/bomb"};
    redirection.run(redirectionTokens3, shell);
    
    String[] rmTokens = {"rm", "dir1/heyo"};
    Command theResultingCommand = rm.run(rmTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals(null, actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  dir1\n    nani\n    nihao\n  dir2\n    lala\n  "
        + "dir3\n  ooga",  actualOutput);
  }
  
  @Test
  public void runTest4() {
    String[] mkdirTokens = {"mkdir", "dir1", "dir2", "dir3", "dir1/heyo", 
        "dir2/lala"};
    mkdir.run(mkdirTokens, shell);
    
    String[] redirectionTokens1 = {"redirect", "\"banana\"", ">", "ooga"};
    redirection.run(redirectionTokens1, shell);
    
    String[] redirectionTokens2 = {"redirect", "\"zyayafgafg\"", ">", 
    "dir1/heyo/bomb"};
    redirection.run(redirectionTokens2, shell);
    
    String[] rmTokens = {"rm", "dir/heyo/wrong/path"};
    Command theResultingCommand = rm.run(rmTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals("rm: \"dir/heyo/wrong/path\": No such file or directory", 
        actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  dir1\n    heyo\n      bomb\n  dir2\n    lala\n"
        + "  dir3\n  ooga",  actualOutput);
  }
  
  
  @Test
  public void runTest5() {
    String[] mkdirTokens = {"mkdir", "dir1", "dir2", "dir3", "dir1/heyo", 
        "dir2/lala"};
    mkdir.run(mkdirTokens, shell);
    
    String[] redirectionTokens1 = {"redirect", "\"banana\"", ">", "ooga"};
    redirection.run(redirectionTokens1, shell);
    
    String[] cdTokens = {"cd", "dir1/heyo"};
    cd.run(cdTokens, shell);
    
    String[] rmTokens = {"rm", "/dir1"};
    Command theResultingCommand = rm.run(rmTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals("The given path: /dir1 cannot be removed because it is a "
        + "subpath to the current directory", actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/\n  dir1\n    heyo\n  dir2\n    lala\n  dir3\n  ooga",  
        actualOutput);
  }
  
  @Test
  public void runTest6() {
    
    String[] rmTokens = {"rm", "/"};
    Command theResultingCommand = rm.run(rmTokens, shell);
    String actualErrors = theResultingCommand.getErrors();
    assertEquals("The given path: / cannot be removed because it is a "
        + "subpath to the current directory", actualErrors);

    String[] treeTokens = {"tree"};
    Command theCheckCommand = tree.run(treeTokens, shell);
    String actualOutput = theCheckCommand.getOutput();
    
    assertEquals("/",  actualOutput);
  }
}
