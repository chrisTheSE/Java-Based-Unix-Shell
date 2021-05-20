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
import commands.Command;
import data.FileSystem;
import driver.JShell;
import runtime.Execution;

public class ExecutionTest {

  private JShell shell;
  private Execution execute;

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

  // test if gives correct error when too manyarguments,
  @Test
  public void executeCommandTest1() {
    execute = new Execution();
    Command exeTest;

    String[] tooMany = {"rm", "\"blah\"", "blah", "blah", "blah"};
    exeTest = execute.executeCommand(tooMany, shell);
    assertEquals("Execution wrong msg/error",
        "rm: there must be exactly one parameter",
        exeTest.getOutput() != null ? exeTest.getOutput()
            : "" + exeTest.getErrors() != null ? exeTest.getErrors() : "");
  }

  // test if gives correct error when too little arguments
  @Test
  public void executeCommandTest2() {
    execute = new Execution();
    Command exeTest;

    String[] tooLittle = {"search"};
    exeTest = execute.executeCommand(tooLittle, shell);
    assertEquals("Execution wrong msg/error",
        "search: Missing required arguments",
        exeTest.getOutput() != null ? exeTest.getOutput()
            : "" + exeTest.getErrors() != null ? exeTest.getErrors() : "");

  }

  // test for correct error when fake command is entered
  @Test
  public void executeCommandTest3() {
    execute = new Execution();
    Command exeTest;

    String[] fakeCommand = {"sudoku", "12x12"};
    exeTest = execute.executeCommand(fakeCommand, shell);
    assertEquals("Execution wrong msg/error", "sudoku: Command not found",
        exeTest.getOutput() != null ? exeTest.getOutput()
            : "" + exeTest.getErrors() != null ? exeTest.getErrors() : "");
  }

  // test that execution properly calls commands
  @Test
  public void executeCommandTest4() {
    execute = new Execution();
    Command exeTest;

    String[] command = {"echo", "\"Hello JUnit testing\""};
    exeTest = execute.executeCommand(command, shell);
    assertEquals("Execution wrong msg/error", "Hello JUnit testing",
        exeTest.getOutput() != null ? exeTest.getOutput()
            : "" + exeTest.getErrors() != null ? exeTest.getErrors() : "");
  }

  // test that execution recognizes all legitimate commands
  @Test
  public void executeCommandTest5() {
    execute = new Execution();
    Command exeTest;

    String[] actual = {"man", "cd", "cat", "echo", "exit", "history", "ls",
        "mkdir", "popd", "pwd", "pushd", "rm", "tree", "saveJShell",
        "loadJShell", "curl", "mv", "search", "cp"};


    String[] command = {""};
    for (int i = 0; i < actual.length; i++) {
      command[0] = actual[i];
      exeTest = execute.executeCommand(command, shell);
      assertNotEquals("Execution wrong msg/error",
          actual[i] + ": Command not found",
          exeTest.getOutput() != null ? exeTest.getOutput()
              : "" + exeTest.getErrors() != null ? exeTest.getErrors() : "");
    }

  }
}
