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
package driver;

import io.*;
import runtime.*;
import commands.Command;
import data.*;

/**
 * The JShell program is a Java implementation of a bash shell. The following
 * commands are included: exit, mkdir, cd, ls, pwd, pushd, popd, history, cat,
 * echo, man. The goal of this project is to make smart design choices about
 * when to use: inheritance, composition, polymorphism, generics, reflection
 * and/or interfaces. Further, it was to practice developing in an Agile
 * environment; writing dailyScrum meeting logs and meeting with our team to
 * write sprint backlogs and a product backlog. Finally, we also created an 
 * auto tester using JUnit and assert and then generated documentation using
 * Javadocs. Logistics: Collaborated and commited code using SVN
 * 
 * @author Christopher Suh
 * @author Christian Chen Liu
 * @author Andrew D'Amario
 * @version 173
 * @since 2020/11/10
 */
public class JShell implements java.io.Serializable {

  // private boolean exit;
  private transient Command run;
  private transient Parser parse;
  private transient Execution execute;
  private transient StandardInput input;
  private FileSystem fSystem;
  private Cache cache;

  /**
   * Constructor for JShell class. It initializes run, identifier for run,
   * parse, execute, input, fSystem, and cache.
   */
  public JShell() {
    this.run = new Command();
    this.run.setIdentifier("command");
    this.parse = new Parser();
    this.execute = new Execution();
    this.input = new StandardInput();
    this.fSystem = FileSystem.createFileSystem();
    this.cache = new Cache();
  }

  /**
   * Main while loop of program to get continuous input. Send input to 
   * execution to be executed. After execution, prints out the returned 
   * output and or errors from execution.
   */
  private void runShell() {
    // Main program loop
    while (!run.getIdentifier().equals("exit")) {
      StandardOutput
          .print(fSystem.getCurrentDirectory().getDirectory()
              .getDirectoryName()
              + " #: ");
      // StandardOutput.println("/#: "); //Shows beginning of a line
      input.nextLine();
      // add line to history
      cache.addHistoryLine(input.getCurrentLine());
      // Parses input into tokens and then executes the command
      String[] tokens = parse.parse(input.getCurrentLine());
      if (!(tokens.length == 1 && tokens[0].equals("~FailedParsing~"))) {
        run = execute.executeCommand(tokens, this);
        if (run.getOutput() != null && !run.getOutput().equals("")) {
          StandardOutput.println(tokens, run.getOutput(), this, run);
        }
        if (run.getErrors() != null && !run.getErrors().equals("")) {
          StandardOutput.println(run.getErrors());
        }
      }
    }
    input.close();
  }

  /**
   * Getter for the JShell file system
   * 
   * @return the JShell FileSystem
   */
  public FileSystem getfSystem() {
    return fSystem;
  }

  /**
   * Setter for the JShell file system
   * 
   * @param fSystem file system to be set
   */
  public void setfSystem(FileSystem fSystem) {
    this.fSystem = fSystem;
  }

  /**
   * Getter for cache
   * 
   * @return returns JShell cache
   */
  public Cache getCache() {
    return cache;
  }

  /**
   * Setter for cache
   * 
   * @param cache cache to be set
   */
  public void setCache(Cache cache) {
    this.cache = cache;
  }

  /**
   * Run main to run the java shell.
   * 
   * @param args, required arguments
   */
  public static void main(String[] args) {
    JShell shell = new JShell();
    shell.runShell();
  }
}
