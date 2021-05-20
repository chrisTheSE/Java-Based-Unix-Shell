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
import driver.JShell;
import data.FileSystem;

/**
 * @author a
 *
 */
public class PopDirectoryTest {

  private JShell shell;
  private PopDirectory popd;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    popd = new PopDirectory();
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

  /**
   * empty
   * 
   * Test method for {@link commands.PopDirectory#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun0() {
    String[] tokens = {"popd"};
    popd.run(tokens, shell);
    assertEquals(null, popd.getOutput());
    assertEquals("popd: Directory stack is empty", popd.getErrors());
    assertEquals("/", shell.getfSystem().getCurrentDirectory().getPath());
  }

  /**
   * one
   * 
   * Test method for {@link commands.PopDirectory#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun1() {
    MakeDirectory mkdir = new MakeDirectory();
    String[] tokens = {"mkdir", "a", "a/b"};
    mkdir.run(tokens, shell);
    shell.getCache().pushDirectoryStack("a/b");

    String[] tokens1 = {"popd"};
    popd.run(tokens1, shell);
    assertEquals(null, popd.getOutput());
    assertEquals(null, popd.getErrors());
    assertEquals("/a/b", shell.getfSystem().getCurrentDirectory().getPath());

    String[] tokens2 = {"popd"};
    popd = new PopDirectory();
    popd.run(tokens1, shell);
    assertEquals(null, popd.getOutput());
    assertEquals("popd: Directory stack is empty", popd.getErrors());
    assertEquals("/a/b", shell.getfSystem().getCurrentDirectory().getPath());
  }

}
