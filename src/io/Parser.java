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

import java.util.ArrayList;
import runtime.ErrorHandler;

/**
 * Takes in a line of input from JShell and parses ( by spaces and quotation
 * marks " ") it in into an array of tokens.
 */
public class Parser {
  /**
   * Takes in a line as a String, separates it ( by spaces and quotation marks "
   * ") and then returns an array of tokens of type String
   * 
   * @param line, a string representing a line of entered text from the terminal
   * @return returns an array of string tokens which are command args
   */
  public String[] parse(String line) {
    // String temp = line.replaceAll(" +", " ").trim();
    String temp = line.trim();
    ArrayList<String> tokens = getTokens(new ArrayList<String>(), temp);

    String[] tokensArray = new String[tokens.size()];

    return tokens.toArray(tokensArray);
  }

  /**
   * Takes in a line of input and then returns a list of tokens of type String
   * 
   * @param tokens, ArrayList to store parsed tokens into
   * @param temp, line of input to be parsed into tokens
   * @return return an ArrayList of tokens
   */
  private ArrayList<String> getTokens(ArrayList<String> tokens, String temp) {
    String tempString = "";
    int i = 0, j = 0;
    mainLoop: while (i < temp.length()) {
      if (temp.charAt(i) == ' ' && i + 1 <= temp.length()
          && temp.charAt(i + 1) != ' ') { // ' ' marks the end of an argument
        tokens.add(tempString);
        tempString = "";
        i++;
      } else if (temp.charAt(i) == '\"') { // Start of a string (ignore spaces)
        if (i != 0 && temp.charAt(i - 1) != ' ') {
          tokens = failedParsing(tokens);
          break mainLoop;
        }
        j = i + 1;
        if (j < temp.length()) { // In case input is just "
          while (temp.charAt(j) != '\"') { // get whole string as single arg
            tempString += temp.charAt(j);
            j++;
            if (j >= temp.length()) { // No closing " for the string
              tokens = failedParsing(tokens);
              break mainLoop;
            }
          }
          if (j + 1 < temp.length() && temp.charAt(j + 1) != ' ') {
            tokens = failedParsing(tokens);
            break mainLoop;
          }
          tokens.add("\"" + tempString + "\""); // Add string to tokens
          i = j + 2; // Update counter to new position in input string; temp
          tempString = "";
        } else {
          tokens = failedParsing(tokens);
          break mainLoop;
        }
      } else { // Continue adding characters to build string argument
        if (temp.charAt(i) != ' ')
          tempString += temp.charAt(i);
        if (i + 1 == temp.length()) // Last character before end of input
          tokens.add(tempString);
        i++;
      }
    }
    return tokens;
  }

  /**
   * Clears tokens and sets tokens[0] to error message
   * 
   * @param tokens, ArrayList of tokens
   * @return returns ArrayList of tokens
   */
  private ArrayList<String> failedParsing(ArrayList<String> tokens) {
    StandardOutput.println(ErrorHandler.illegalString());
    tokens.clear();
    tokens.add("~FailedParsing~");
    return tokens;
  }
}
