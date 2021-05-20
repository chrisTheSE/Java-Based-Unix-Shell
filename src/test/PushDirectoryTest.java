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

import driver.JShell;
import commands.*;
import data.FileSystem;

/**
 * @author a
 *
 */
public class PushDirectoryTest {

  private JShell shell;
  private PushDirectory pushd;
  
  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    pushd = new PushDirectory();
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
   * empty string (would never actually be able to be called)
   * 
   * Test method for {@link commands.PushDirectory#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun0() {
    String[] tokens = {"pushd", ""};
    pushd.run(tokens, shell);
    assertEquals(null, pushd.getOutput());
    assertEquals(null, pushd.getErrors());
    assertEquals("/", shell.getfSystem().getCurrentDirectory().getPath());
    assertEquals("/", shell.getCache().popDirectoryStack());
  }

  /**
   * push one dir
   * 
   * Test method for {@link commands.PushDirectory#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun1() {
    MakeDirectory mkdir = new MakeDirectory();
    String[] tokens = {"mkdir", "/s", "/a", "a/d", "a/d/f"};
    mkdir.run(tokens, shell);

    String[] tokens2 = {"pushd", "a/d/f"};
    pushd.run(tokens2, shell);
    assertEquals(null, pushd.getOutput());
    assertEquals(null, pushd.getErrors());
    assertEquals("/a/d/f", shell.getfSystem().getCurrentDirectory().getPath());
    assertEquals("/", shell.getCache().popDirectoryStack());
    assertEquals(null, shell.getCache().popDirectoryStack());
  }

  /**
   * push some dirs, and change directory
   * 
   * Test method for {@link commands.PushDirectory#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun2() {
    MakeDirectory mkdir = new MakeDirectory();
    String[] tokens = {"mkdir", "/w", "/qwsa", "w/s", "w/s/d"};
    mkdir.run(tokens, shell);

    String[] tokens2 = {"pushd", "w/s/d"};
    pushd.run(tokens2, shell);
    assertEquals(null, pushd.getOutput());
    assertEquals(null, pushd.getErrors());
    assertEquals("/w/s/d", shell.getfSystem().getCurrentDirectory().getPath());

    ChangeDirectory cd = new ChangeDirectory();
    String[] tokens3 = {"cd", "qwsa"};
    cd.run(tokens3, shell);
    
    pushd = new PushDirectory();
    String[] tokens4 = {"pushd", "/w/s"};
    pushd.run(tokens4, shell);
    assertEquals(null, pushd.getOutput());
    assertEquals(null, pushd.getErrors());
    assertEquals("/w/s", shell.getfSystem().getCurrentDirectory().getPath());

    pushd = new PushDirectory();
    String[] tokens5 = {"pushd", "."};
    pushd.run(tokens5, shell);
    assertEquals(null, pushd.getOutput());
    assertEquals(null, pushd.getErrors());
    assertEquals("/w/s", shell.getfSystem().getCurrentDirectory().getPath());

    pushd = new PushDirectory();
    String[] tokens6 = {"pushd", ".."};
    pushd.run(tokens6, shell);
    assertEquals(null, pushd.getOutput());
    assertEquals(null, pushd.getErrors());
    assertEquals("/w", shell.getfSystem().getCurrentDirectory().getPath());

    assertEquals("/w/s", shell.getCache().popDirectoryStack());
    assertEquals("/w/s", shell.getCache().popDirectoryStack());
    assertEquals("/w/s/d", shell.getCache().popDirectoryStack());
    assertEquals("/", shell.getCache().popDirectoryStack());
    assertEquals(null, shell.getCache().popDirectoryStack());
  }

  /**
   * bad input
   * 
   * Test method for {@link commands.PushDirectory#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun3() {
    String[] tokens = {"pushd", "as"};
    pushd.run(tokens, shell);
    assertEquals(null, pushd.getOutput());
    assertEquals("pushd: \"as\": No such file or directory", pushd.getErrors());
    assertEquals("/", shell.getfSystem().getCurrentDirectory().getPath());
    assertEquals(null, shell.getCache().popDirectoryStack());
  }


}
