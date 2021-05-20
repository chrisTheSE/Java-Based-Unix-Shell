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
public class SearchTest {

  private JShell shell;
  private Search search;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    search = new Search();
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

  private void fSystemSetup1() {
    MakeDirectory mk = new MakeDirectory();
    String[] tokens = {"mkdir", "b", "b/d", "b/a", "a", "a/e", "a/e/f"};
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
   * some dirs, search for file, empty fSystem
   * 
   * Test method for
   * {@link commands.Search#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun0() {
    String[] tokens = {"search", ".", "-type", "f", "-name", "\"hi\""};
    search.run(tokens, shell);
    assertEquals(".: does not contain file: \"hi\"", search.getOutput());
    assertEquals(null, search.getErrors());

    String[] tokens2 = {"search", "l", "-type", "f", "-name", "\"hi\""};
    search = new Search();
    search.run(tokens2, shell);
    assertEquals(null, search.getOutput());
    assertEquals("search: \"l\": No such file or directory",
        search.getErrors());
  }

  /**
   * one dir, search for dir, empty fSystem
   * 
   * Test method for
   * {@link commands.Search#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun1() {
    String[] tokens = {"search", ".", "-type", "d", "-name", "\"wowDir\""};
    search.run(tokens, shell);
    assertEquals(".: does not contain directory: \"wowDir\"",
        search.getOutput());
    assertEquals(null, search.getErrors());

    String[] tokens2 = {"search", "z", "-type", "d", "-name", "\"hi\""};
    search = new Search();
    search.run(tokens2, shell);
    assertEquals(null, search.getOutput());
    assertEquals("search: \"z\": No such file or directory",
        search.getErrors());
  }

  /**
   * one dir, search for file, non-empty fSystem
   * 
   * Test method for
   * {@link commands.Search#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun2() {
    fSystemSetup1();
    String[] tokens = {"search", ".", "-type", "f", "-name", "\"c\""};
    search.run(tokens, shell);
    assertEquals("./c\n./b/c\n./a/e/f/c", search.getOutput());
    assertEquals(null, search.getErrors());

    String[] tokens2 = {"search", "b/d", "-type", "f", "-name", "\"c\""};
    search = new Search();
    search.run(tokens2, shell);
    assertEquals("b/d: does not contain file: \"c\"", search.getOutput());
    assertEquals(null, search.getErrors());

    String[] tokens3 = {"search", "a", "-type", "f", "-name", "\"s\""};
    search = new Search();
    search.run(tokens3, shell);
    assertEquals("a: does not contain file: \"s\"", search.getOutput());
    assertEquals(null, search.getErrors());

    String[] tokens4 = {"search", "/a/l", "-type", "f", "-name", "\"c\""};
    search = new Search();
    search.run(tokens4, shell);
    assertEquals(null, search.getOutput());
    assertEquals("search: \"/a/l\": No such file or directory",
        search.getErrors());

  }

  /**
   * one dir, search for directory, non-empty fSystem
   * 
   * Test method for
   * {@link commands.Search#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun3() {
    fSystemSetup1();
    String[] tokens = {"search", ".", "-type", "d", "-name", "\"a\""};
    search.run(tokens, shell);
    assertEquals("./a\n./b/a", search.getOutput());
    assertEquals(null, search.getErrors());

    String[] tokens2 = {"search", "b", "-type", "d", "-name", "\"a\""};
    search = new Search();
    search.run(tokens2, shell);
    assertEquals("b/a", search.getOutput());
    assertEquals(null, search.getErrors());

    String[] tokens3 = {"search", "a", "-type", "d", "-name", "\"b\""};
    search = new Search();
    search.run(tokens3, shell);
    assertEquals("a: does not contain directory: \"b\"", search.getOutput());
    assertEquals(null, search.getErrors());

    String[] tokens4 = {"search", "x", "-type", "d", "-name", "\"b\""};
    search = new Search();
    search.run(tokens4, shell);
    assertEquals(null, search.getOutput());
    assertEquals("search: \"x\": No such file or directory",
        search.getErrors());

  }

  /**
   * some dirs, search for file, empty fSystem
   * 
   * Test method for
   * {@link commands.Search#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun4() {
    String[] tokens =
        {"search", "a", "b", "b/d", "-type", "f", "-name", "\"c\""};
    search.run(tokens, shell);
    assertEquals(null, search.getOutput());
    assertEquals("search: \"a\": No such file or directory",
        search.getErrors());
  }

  /**
   * some dirs, search for dir, empty fSystem
   * 
   * Test method for
   * {@link commands.Search#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun5() {
    String[] tokens = {"search", "w", ".", "-type", "f", "-name", "\"wowDir\""};
    search.run(tokens, shell);
    assertEquals(null, search.getOutput());
    assertEquals("search: \"w\": No such file or directory",
        search.getErrors());
  }

  /**
   * some dirs, search for file, non-empty fSystem
   * 
   * Test method for
   * {@link commands.Search#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun6() {
    fSystemSetup1();
    String[] tokens =
        {"search", "a", "b", "b/d", "-type", "f", "-name", "\"c\""};
    search.run(tokens, shell);
    assertEquals("a/e/f/c\nb/c\nb/d: does not contain file: \"c\"",
        search.getOutput());
    assertEquals(null, search.getErrors());

    String[] tokens2 = {"search", "a", "a", "a/e", "b/d", "b/a", "z", "a",
        "-type", "f", "-name", "\"c\""};
    search = new Search();
    search.run(tokens2, shell);
    assertEquals(
        "a/e/f/c\na/e/f/c\na/e/f/c\nb/d: does not contain file: \"c\"\nb/a:"
            + " does not contain file: \"c\"",
        search.getOutput());
    assertEquals("search: \"z\": No such file or directory",
        search.getErrors());

    String[] tokens3 = {"search", "a", "b/a", "-type", "f", "-name", "\"s\""};
    search = new Search();
    search.run(tokens3, shell);
    assertEquals(
        "a: does not contain file: \"s\"\nb/a: does not contain file: \"s\"",
        search.getOutput());
    assertEquals(null, search.getErrors());

  }

  /**
   * some dirs, search for directory, non-empty fSystem
   * 
   * Test method for
   * {@link commands.Search#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun7() {
    fSystemSetup1();
    String[] tokens =
        {"search", "a", "b", "b/d", "-type", "d", "-name", "\"a\""};
    search.run(tokens, shell);
    assertEquals(
        "a: does not contain directory: \"a\"\nb/a\nb/d: does not contain"
            + " directory: \"a\"",
        search.getOutput());
    assertEquals(null, search.getErrors());

    String[] tokens2 = {"search", "a", "a", "a/e", "b/d", "b/a", "x", "a",
        "-type", "d", "-name", "\"a\""};
    search = new Search();
    search.run(tokens2, shell);
    assertEquals(
        "a: does not contain directory: \"a\"\na: does not contain directory:"
            + " \"a\"\na/e: does not contain directory: \"a\"\nb/d: does not "
            + "contain directory: \"a\"\nb/a: does not contain directory:"
            + " \"a\"",
        search.getOutput());
    assertEquals("search: \"x\": No such file or directory",
        search.getErrors());

    String[] tokens3 = {"search", "a", "b/a", "-type", "d", "-name", "\"s\""};
    search = new Search();
    search.run(tokens3, shell);
    assertEquals(
        "a: does not contain directory: \"s\"\nb/a: does not contain directory:"
            + " \"s\"",
        search.getOutput());
    assertEquals(null, search.getErrors());

  }

  /**
   * bad input
   * 
   * Test method for
   * {@link commands.Search#run(java.lang.String[], driver.JShell)}.
   */
  @Test
  public final void testRun8() {
    fSystemSetup1();
    String[] tokens = {"search", "a", "-name", "\"a\"", "-type", "d"};
    search.run(tokens, shell);
    assertEquals(null, search.getOutput());
    assertEquals(
        "search: a -name \"a\" -type d : Invalid combination of arguments",
        search.getErrors());

    String[] tokens2 = {"search", "hi", "type", "d", "name", "\"a\""};
    search = new Search();
    search.run(tokens2, shell);
    assertEquals(null, search.getOutput());
    assertEquals(
        "search: hi type d name \"a\" : Invalid combination of arguments",
        search.getErrors());

    String[] tokens3 = {"search", "a", "b/a", "-", "f", "-S", "\"s\""};
    search = new Search();
    search.run(tokens3, shell);
    assertEquals(null, search.getOutput());
    assertEquals(
        "search: a b/a - f -S \"s\" : Invalid combination of arguments",
        search.getErrors());

  }


}
