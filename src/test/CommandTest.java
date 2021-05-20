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
public class CommandTest {

  private JShell shell;
  private Command command;
  private Command ret;
  
  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
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

  private void fSystemSetup1() {
    MakeDirectory mk = new MakeDirectory();
    String[] tokens = {"mkdir", "b", "b/d", "s", "s/b", "a", "a/e", "a/e/f"};
    mk.run(tokens, shell);

    Redirection redirect = new Redirection();
    String[] tokens2 = {"echo", "\"wow\"", ">", "c"};
    redirect.run(tokens2, shell);

    redirect = new Redirection();
    String[] tokens3 = {"echo", "\"let's go\"", ">", "b/c"};
    redirect.run(tokens3, shell);

    redirect = new Redirection();
    String[] tokens4 = {"echo", "\"oh ya\"", ">", "a/e/f/c"};
    redirect.run(tokens4, shell);
  }



  /**
   * Test method for {@link commands.Command#containsArrow(java.lang.String[])}.
   */
  @Test
  public final void testContainsArrow() {
    String[] tokens = {};
    assertFalse(command.containsArrow(tokens));

    String[] tokens0 = {""};
    assertFalse(command.containsArrow(tokens0));    

    String[] tokens1 = {"hi", "this", "was", "/path/like", "fun"};
    assertFalse(command.containsArrow(tokens1));

    String[] tokens2 = {">"};
    assertFalse(command.containsArrow(tokens2));

    String[] tokens3 = {">>"};
    assertFalse(command.containsArrow(tokens3));

    String[] tokens4 = {"like", ">", "so"};
    assertTrue(command.containsArrow(tokens4));

    String[] tokens5 = {">", "proper"};
    assertTrue(command.containsArrow(tokens5));

    String[] tokens6 = {">>", "good"};
    assertTrue(command.containsArrow(tokens6));

  }

  /**
   * empty
   * 
   * Test method for {@link commands.Command#checkRun(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testCheckRun0() {
    command = new Command();
    String[] tokens = {};
    ret = command.checkRun(tokens, shell);
    assertEquals(null, ret.getErrors());
    assertEquals(null, ret.getOutput());
  }

  /**
   * redirection
   * 
   * Test method for {@link commands.Command#checkRun(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testCheckRun1() {
    fSystemSetup1();

    command = new MakeDirectory();
    String[] tokens = {"mkdir", ">", "h"};
    ret = command.checkRun(tokens, shell);
    assertEquals(null, ret.getOutput());
    assertEquals(null, ret.getErrors());

    command = new Concatenate();
    String[] tokens1 = {"cat", "c", ">", "a"};
    ret = command.checkRun(tokens1, shell);
    assertEquals(null, ret.getOutput());
    assertEquals("The file/directory a already exists at /", ret.getErrors());

    command = new ChangeDirectory();
    String[] tokens2 = {"cd", "b", ">", "f"};
    ret = command.checkRun(tokens2, shell);
    assertEquals(null, ret.getOutput());
    assertEquals(null, ret.getErrors());

    command = new History();
    String[] tokens3 = {"history", "b", ">"};
    ret = command.checkRun(tokens3, shell);
    assertEquals(null, ret.getOutput());
    assertEquals("history: Operand must be a non-negative integer", ret.getErrors());

    command = new ListContents();
    String[] tokens4 = {"ls", ".", ">>", "a", "b"};
    ret = command.checkRun(tokens4, shell);
    assertEquals(".:\nd\nc", ret.getOutput());
    assertEquals("ls: Cannot access \">>\": No such file or directory", ret.getErrors());

    command = new ListContents();
    String[] tokens5 = {"ls", ".", ">>", "f"};
    ret = command.checkRun(tokens5, shell);
    assertEquals(".:\nd\nc", ret.getOutput());
    assertEquals(null, ret.getErrors());

  }

  /**
   * invalid name
   * 
   * Test method for {@link commands.Command#checkRun(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testCheckRun2() {
    fSystemSetup1();

    command = new Echo();
    String[] tokens = {"echo", "hi", ">", "h&"};
    ret = command.checkRun(tokens, shell);
    assertEquals(null, ret.getOutput());
    assertEquals("echo: \"h&\": Invalid file and/or directory name", ret.getErrors());
  }

  /**
   * valid path
   * 
   * Test method for {@link commands.Command#checkRun(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testCheckRun3() {
    fSystemSetup1();

    command = new PrintWorkingDirectory();
    String[] tokens = {"pwd", ">", "a/e/f/file"};
    ret = command.checkRun(tokens, shell);
    assertEquals("/", ret.getOutput());
    assertEquals(null, ret.getErrors());

    command = new Move();
    String[] tokens2 = {"mv", "a", "c", ">", "a/f/file"};
    ret = command.checkRun(tokens2, shell);
    assertEquals(null, ret.getOutput());
    assertEquals("mv: \"a/f/file\": No such file or directory", ret.getErrors());
  }

  /**
   * no redirection
   * 
   * Test method for {@link commands.Command#checkRun(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testCheckRun4() {
    command = new Echo();
    String[] tokens = {"echo", "\"hi\""};
    ret = command.checkRun(tokens, shell);
    assertEquals("hi", ret.getOutput());
    assertEquals(null, ret.getErrors());
  }

  /**
   * exiting file
   * 
   * Test method for {@link commands.Command#checkRun(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testCheckRun5() {
    fSystemSetup1();
    command = new History();
    String[] tokens = {"history", ">>", "c"};
    ret = command.checkRun(tokens, shell);
    assertEquals("", ret.getOutput());
    assertEquals(null, ret.getErrors());
  }



}
