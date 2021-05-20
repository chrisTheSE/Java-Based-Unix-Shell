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
package io;

import java.util.Scanner;

/**
 * Defines how the system receives input from the shell.
 */
public class StandardInput {

  // create new instance of Scanner to read user input in shell
  private Scanner scan = new Scanner(System.in);
  private String currentLine = "";

  /**
   * Read next line of the current scan.
   */
  public void nextLine() {
    currentLine = scan.nextLine(); // Awaits input
  }

  /**
   * Get current line
   * @return the currentline from scanner
   */
  public String getCurrentLine() {
    return currentLine;
  }

  /**
   * Close the scan.
   */
  public void close() {
    scan.close();
  }
}
