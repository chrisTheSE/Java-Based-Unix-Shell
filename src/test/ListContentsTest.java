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
/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.*;
import data.FileSystem;
import driver.JShell;

/**
 * @author a
 *
 */
public class ListContentsTest {

  private JShell shell;
  private ListContents ls;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    ls = new ListContents();
    shell = new JShell();
    shell.setfSystem(FileSystem.createFileSystem()); 
  }
  
  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    Field field = (shell.getfSystem().getClass()).getDeclaredField("fileSystem");
    field.setAccessible(true);
    field.set(null, null);
  }

  // TODO should I leave this or just put it in setup
  private void fSystemSetup1() {
    MakeDirectory mk = new MakeDirectory();
    String[] tokens = {"mkdir", "a", "a/d", "/as", "as/we", "ya"};
    mk.run(tokens, shell);
  }

  /**
   * empty
   * 
   * Test method for {@link commands.ListContents#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun0() {
    String[] tokens = {"ls"};
    ls.run(tokens, shell);
    assertEquals(null, ls.getOutput());
    assertEquals(null, ls.getErrors());

    String[] tokens1 = {"ls", "-R"};
    ls = new ListContents();
    ls.run(tokens1, shell);
    assertEquals(":", ls.getOutput());
    assertEquals(null, ls.getErrors());
  }

  /**
   * no parameters
   * 
   * Test method for {@link commands.ListContents#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun1() {
    fSystemSetup1();
    String[] tokens2 = {"ls"};
    ls.run(tokens2, shell);
    assertEquals("a\nas\nya", ls.getOutput());
    assertEquals(null, ls.getErrors());
  }

  
  /**
   * one parameter
   * 
   * Test method for {@link commands.ListContents#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun2() {
    fSystemSetup1();
    String[] tokens = {"ls", "-R"};
    ls.run(tokens, shell);
    assertEquals(":\na\nas\nya\n\na:\nd\n\na/d:\n\nas:\nwe\n\nas/we:\n\nya:", ls.getOutput());
    assertEquals(null, ls.getErrors());

    String[] tokens2 = {"ls", "a"};
    ls = new ListContents();
    ls.run(tokens2, shell);
    assertEquals("a:\nd", ls.getOutput());
    assertEquals(null, ls.getErrors());

    String[] tokens3 = {"ls", "notAdir"};
    ls = new ListContents();
    ls.run(tokens3, shell);
    assertEquals("", ls.getOutput());
    assertEquals("ls: Cannot access \"notAdir\": No such file or directory", ls.getErrors());
  }

  /**
   * multiple parameters
   * 
   * Test method for {@link commands.ListContents#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun3() {
    fSystemSetup1();
    String[] tokens = {"ls", "-R", "a", "as"};
    ls = new ListContents();
    ls.run(tokens, shell);
    assertEquals("a:\nd\n\na/d:\n\nas:\nwe\n\nas/we:", ls.getOutput());
    assertEquals(null, ls.getErrors());

    String[] tokens2 = {"ls", "a", "s", "as"};
    ls = new ListContents();
    ls.run(tokens2, shell);
    assertEquals("a:\nd", ls.getOutput());
    assertEquals("ls: Cannot access \"s\": No such file or directory", ls.getErrors());

    String[] tokens3 = {"ls", "-R", "junk"};
    ls = new ListContents();
    ls.run(tokens3, shell);
    assertEquals("", ls.getOutput());
    assertEquals("ls: Cannot access \"junk\": No such file or directory", ls.getErrors());
  }


  /**
   * different current directory, test . and .., amd recursive
   * 
   * Test method for {@link commands.ListContents#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun5() {
    fSystemSetup1();
    ChangeDirectory cd = new ChangeDirectory();
    String[] tokens = {"cd", "a"};
    cd.run(tokens, shell);

    String[] tokens2 = {"ls"};
    ls = new ListContents();
    ls.run(tokens2, shell);
    assertEquals("d", ls.getOutput());
    assertEquals(null, ls.getErrors());
    
    String[] tokens3 = {"ls", "/as", ".", "..", "hi", "d"};
    ls = new ListContents();
    ls.run(tokens3, shell);
    assertEquals("/as:\nwe\n\n.:\nd\n\n..:\na\nas\nya", ls.getOutput());
    assertEquals("ls: Cannot access \"hi\": No such file or directory", ls.getErrors());

    String[] tokens4 = {"ls", "-R"};
    ls = new ListContents();
    ls.run(tokens4, shell);
    assertEquals(":\nd\n\nd:", ls.getOutput());
    assertEquals(null, ls.getErrors());

  }

}
