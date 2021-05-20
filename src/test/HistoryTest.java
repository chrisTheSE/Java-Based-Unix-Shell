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

import javax.print.attribute.standard.SheetCollate;

import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.History;
import driver.JShell;
import data.*;

/**
 * @author a
 *
 */
public class HistoryTest {

  private History hist;
  private JShell shell;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    hist = new History();
    shell = new JShell();
    shell.setfSystem(FileSystem.createFileSystem());	

  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    Field field =
        (shell.getfSystem().getClass()).getDeclaredField("fileSystem");
    field.setAccessible(true);
    field.set(null, null);
  }

  private void cacheSetup() {
    shell.getCache().addHistoryLine("echo \"a\" >> file");
    shell.getCache().addHistoryLine("junk");
    shell.getCache().addHistoryLine("");
    shell.getCache().addHistoryLine(" file          space");
    shell.getCache().addHistoryLine("ls -R     ");
    shell.getCache().addHistoryLine("mkdir asd/asd/asd/a");
    shell.getCache().addHistoryLine("^&s");
  }

  /**
   * Test method for {@link commands.History#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun0() {
    String[] tokens = {"history"};
    hist.run(tokens, shell);
    assertEquals("", hist.getOutput());
    assertEquals(null, hist.getErrors());
  }

  /**
   * no parameter
   * 
   * Test method for {@link commands.History#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun1() {
    cacheSetup();
    String[] tokens = {"history"};
    hist.run(tokens, shell);
    assertEquals("1. echo \"a\" >> file\n"
      +"2. junk\n"
      +"3. \n"
      +"4.  file          space\n"
      +"5. ls -R     \n"
      +"6. mkdir asd/asd/asd/a\n"
      +"7. ^&s", hist.getOutput());
    assertEquals(null, hist.getErrors());
  }

  /**
   * greater than list size
   * 
   * Test method for {@link commands.History#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun2() {
    cacheSetup();
    String[] tokens1 = {"history", "12"};
    hist.run(tokens1, shell);
    assertEquals("1. echo \"a\" >> file\n"
      +"2. junk\n"
      +"3. \n"
      +"4.  file          space\n"
      +"5. ls -R     \n"
      +"6. mkdir asd/asd/asd/a\n"
      +"7. ^&s", hist.getOutput());
    assertEquals(null, hist.getErrors());
  }

  /**
   * less than list size
   * 
   * Test method for {@link commands.History#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun3() {
    cacheSetup();
    String[] tokens2 = {"history", "3"};
    hist.run(tokens2, shell);
    assertEquals("5. ls -R     \n"
    +"6. mkdir asd/asd/asd/a\n"
    +"7. ^&s", hist.getOutput());
    assertEquals(null, hist.getErrors());
  }

  /**
   * get 0
   * 
   * Test method for {@link commands.History#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun4() {
    cacheSetup();
    String[] tokens3 = {"history", "0"};
    hist.run(tokens3, shell);
    assertEquals("", hist.getOutput());
    assertEquals(null, hist.getErrors());
  }

  /**
   * get negative
   * 
   * Test method for {@link commands.History#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun5() {
    cacheSetup();
    String[] tokens4 = {"history", "-2"};
    hist.run(tokens4, shell);
    assertEquals(null, hist.getOutput());
    assertEquals("history: Operand must be a non-negative integer", hist.getErrors());
  }

  /**
   * bad input
   * 
   * Test method for {@link commands.History#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun6() {
    cacheSetup();
    String[] tokens = {"history", "hi"};
    hist.run(tokens, shell);
    assertEquals(null, hist.getOutput());
    assertEquals("history: Operand must be a non-negative integer", hist.getErrors());
    
  }


}
