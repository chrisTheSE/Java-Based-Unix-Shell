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
import commands.Echo;
import commands.History;
import commands.LoadJShell;
import commands.MakeDirectory;
import commands.Redirection;
import commands.SaveJShell;
import commands.Tree;
import data.Cache;
import data.FileSystem;
import driver.JShell;

public class LoadJShellTest {
  private LoadJShell loadShell;
  private JShell shell;
  private JShell testShell;

  @Before
  public void setUp() throws Exception {
    shell = new JShell();
    shell.setfSystem(FileSystem.createFileSystem());
    testShell = new JShell();
    testShell.setfSystem(FileSystem.createFileSystem());
    testShell.setCache(new Cache());
  }

  @After
  public void tearDown() throws Exception {
    Field field =
        (shell.getfSystem().getClass()).getDeclaredField("fileSystem");
    field.setAccessible(true);
    field.set(null, null);

    Field field2 =
        (testShell.getfSystem().getClass()).getDeclaredField("fileSystem");
    field2.setAccessible(true);
    field2.set(null, null);
  }

  // Checks that history and fileSystem are correctly loaded
  // Note: Loads from file called testload saved in svn
  @Test
  public void testRun() throws Exception {
    MakeDirectory make = new MakeDirectory();
    Echo echo = new Echo();
    String[] createDir1 =
        {"mkdir", "dir1", "dir1/a", "dir1/b", "dir2", "dir2/c", "dir2/c/g"};
    String[] echoOut = {"echo", "\"fdss\"", ">", "dir1/a/file1"};
    make.checkRun(createDir1, shell);
    echo.checkRun(echoOut, shell);
    shell.getCache()
        .addHistoryLine("mkdir dir1 dir1/a dir1/b dir2 dir2/c dir2/c/g");
    shell.getCache().addHistoryLine("echo \"fdss\" > dir1/a/file1");
    shell.getCache().addHistoryLine("ls");
    shell.getCache().addHistoryLine("cd dir1/a");
    shell.getCache().addHistoryLine("ls");
    shell.getCache().addHistoryLine("cat file1");
    shell.getCache().addHistoryLine("history");
    shell.getCache().addHistoryLine("history");
    shell.getCache().addHistoryLine("history 1");
    shell.getCache().addHistoryLine("history 100");

    SaveJShell save = new SaveJShell();
    String[] tokens = {"saveJShell", "testerLoad"};
    save.run(tokens, shell);

    loadShell = new LoadJShell();
    String[] actual = {"loadJShell", "testerLoad"};
    testShell.setCache(new Cache());
    loadShell.run(actual, testShell);

    History his = new History();
    History his2 = new History();
    String[] setHis = {"history"};

    assertEquals("loadJShell did not correctly load history",
        his.run(setHis, shell).getOutput(),
        his2.run(setHis, testShell).getOutput());
    assertEquals("loadJShell did not correctly load current directory",
        shell.getfSystem().getCurrentDirectory().getDirectory()
            .getDirectoryName(),
        testShell.getfSystem().getCurrentDirectory().getDirectory()
            .getDirectoryName());
    Tree tree = new Tree();
    String[] setTree = {"tree"};
    assertEquals("loadJShell did not correctly load filesystem",
        tree.checkRun(setTree, shell).getOutput(),
        tree.checkRun(setTree, testShell).getOutput());
    
    assertEquals("loadJShell did return expected error msg",
        "loadJShell: Non-empty JShell",
        loadShell.run(actual, testShell).getErrors());
  }

  @Test
  public void testRun2() throws Exception {
    loadShell = new LoadJShell();
    String[] actual = {"loadJShell", "fakename"};

    LoadJShell loader = (LoadJShell) loadShell.run(actual, testShell);

    assertEquals("loadJShell did return expected error msg",
        "loadJShell: \"fakename\": No such file or directory",
        loader.getErrors());  
  }
}
