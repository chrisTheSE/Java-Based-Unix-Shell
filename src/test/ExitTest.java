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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.Command;
import commands.Exit;
import driver.JShell;

/**
 * @author a
 *
 */
public class ExitTest {

  private JShell shell;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    shell = new JShell();
  }

  /**
   * Test method for {@link commands.Exit#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun() {
    Exit ex = new Exit();
    String[] tokens = {"exit"};
    Command terminate = ex.run(tokens, shell);
    assertEquals("exit", terminate.getIdentifier());
    assertEquals(null, ex.getErrors());
  }

}
