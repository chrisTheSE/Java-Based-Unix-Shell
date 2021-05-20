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
import commands.PrintWorkingDirectory;
import data.FileSystem;
import driver.JShell;

public class PrintWorkingDirectoryTest {

  private JShell shell;
  private MakeDirectory mkdir = new MakeDirectory();
  private ChangeDirectory cd = new ChangeDirectory();
  private PrintWorkingDirectory pwd = new PrintWorkingDirectory();
  
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
    String[] pwdTokens = {"pwd"};
    Command theResultingCommand = pwd.run(pwdTokens, shell);
    String actualOutput = theResultingCommand.getOutput();
    String actualErrors = theResultingCommand.getErrors();
    
    assertEquals("/", actualOutput);
    assertEquals(null, actualErrors);
  }
  
  @Test
  public void runTest2() {
    
    String[] mkdirTokens = {"mkdir", "bruh", "kha", "hai"};
    mkdir.run(mkdirTokens, shell);
    
    String[] cdTokens = {"cd", "bruh"};
    cd.run(cdTokens, shell);
    
    String[] pwdTokens = {"pwd"};
    Command theResultingCommand = pwd.run(pwdTokens, shell);
    String actualOutput = theResultingCommand.getOutput();
    String actualErrors = theResultingCommand.getErrors();
    
    assertEquals("/bruh", actualOutput);
    assertEquals(null, actualErrors);
  }
  
  
  @Test
  public void runTest3() {
    
    String[] mkdirTokens = {"mkdir", "okay", "no", "yes", 
        "yes/maybe", "yes/ok", "idk", "idk/wtf", "yes/maybe/ha"};
    mkdir.run(mkdirTokens, shell);
    
    String[] cdTokens = {"cd", "yes/maybe/ha"};
    cd.run(cdTokens, shell);
    
    String[] pwdTokens = {"pwd"};
    Command theResultingCommand = pwd.run(pwdTokens, shell);
    String actualOutput = theResultingCommand.getOutput();
    String actualErrors = theResultingCommand.getErrors();
    
    assertEquals("/yes/maybe/ha", actualOutput);
    assertEquals(null, actualErrors);
  }
  
}
