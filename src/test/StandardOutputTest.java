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

import driver.JShell;
import io.StandardOutput;
import commands.*;

/**
 * @author a
 *
 */
public class StandardOutputTest {

  private JShell shell = new JShell();
  private Command command;
  private String sysOut;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * no arrow
   * 
   * Test method for {@link io.StandardOutput#println(java.lang.String[], java.lang.String, driver.JShell, commands.Command)}.
   */
  @Test
  public final void testPrintlnStringArrayStringJShellCommand0() {
    command = new Echo();
    String[] tokens = {"hi", "myFriend"};
    sysOut = StandardOutput.println(tokens, "we go out", shell, command);
    assertEquals("we go out", sysOut);

    command = new PopDirectory();
    String[] tokens2 = {"popd"};
    sysOut = StandardOutput.println(tokens2, "hello kind TA", shell, command);
    assertEquals("hello kind TA", sysOut);

  }

  /**
   * arrow
   * 
   * Test method for {@link io.StandardOutput#println(java.lang.String[], java.lang.String, driver.JShell, commands.Command)}.
   */
  @Test
  public final void testPrintlnStringArrayStringJShellCommand1() {
    command = new Echo();
    String[] tokens = {">", "myFriend"};
    sysOut = StandardOutput.println(tokens, "we go out", shell, command);
    assertEquals(null, sysOut);

    command = new Concatenate();
    String[] tokens2 = {"cat", "c", ">>", "myFriend"};
    sysOut = StandardOutput.println(tokens2, "File stuff", shell, command);
    assertEquals(null, sysOut);

    command = new MakeDirectory();
    String[] tokens3 = {">"};
    sysOut = StandardOutput.println(tokens3, "you'll see me", shell, command);
    assertEquals("you'll see me", sysOut);

    command = new ChangeDirectory();
    String[] tokens4 = {"cd", ">", "s#"};
    sysOut = StandardOutput.println(tokens4, "what I want to see but never will", shell, command);
    assertEquals("redirect: \"s#\": Invalid file and/or directory name", sysOut);
  }

}
