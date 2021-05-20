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
import commands.Echo;
import data.FileSystem;
import driver.JShell;
import runtime.ErrorHandler;

public class ErrorHandlerTest {

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

  @Test
  public void invalidComboOfParamsTest() {
    Echo echo = new Echo();
    String[] actual = {"echo", "John", "Marry", "Thomas"};
    assertEquals("Wrong error returned",
        "echo: John Marry Thomas : Invalid combination of arguments",
        ErrorHandler.invalidComboOfParams(echo, actual));
  }

  @Test
  public void missingStringTest() {
    Echo echo = new Echo();
    String[] actual = {"echo", "John"};
    assertEquals("Wrong error returned",
        "echo: John: No string found, format string as \"string\"",
        ErrorHandler.missingString(echo, actual));
  }
}
