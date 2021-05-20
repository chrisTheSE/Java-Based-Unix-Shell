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
import commands.Concatenate;
import commands.Echo;
import data.FileSystem;
import driver.JShell;

public class EchoTest {

  private Echo echo;
  private JShell shell;

  @Before
  public void setUp() throws Exception {
    shell = new JShell();
    shell.setfSystem(FileSystem.createFileSystem());

  }

  @After
  public void tearDown() throws Exception {
    Field field =
        (shell.getfSystem().getClass()).getDeclaredField("fileSystem");
    field.setAccessible(true);
    field.set(null, null);
  }
  //Note: No need to handle invalid strings, since Parser takes care of them
  // Correctly prints the given string and handles bad input
  @Test
  public void runTest1() throws Exception {
    setUp();
    echo = new Echo();

    String[] actual = {"echo", "\"  Hello the world, test the world.  \""};
    assertEquals("echo did not correctly print string",
        "  Hello the world, test the world.  ",
        echo.checkRun(actual, shell).getOutput());
    String[] actual2 =
        {"echo", "dssaasdsd", "\"  Hello the world, test the world.  \""};
    assertEquals("echo did not correctly print string",
        "echo: dssaasdsd: No string found, format string as \"string\"",
        echo.checkRun(actual2, shell).getErrors());
    String[] actual3 =
      {"echo", "testering"};
  assertEquals("echo did not correctly print string",
      "echo: testering: No string found, format string as \"string\"",
      echo.checkRun(actual3, shell).getErrors());


    tearDown();
  }

}
