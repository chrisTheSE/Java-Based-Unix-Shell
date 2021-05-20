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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import io.Parser;

public class ParserTest {
  private Parser parse;

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void parseTest1() {
    parse = new Parser();
    // Parser should treat any number of whitespaces as a single whitespace
    // except when considering a string.
    String[] expected = {"echo", "\"         f     s \""};
    Assert.assertArrayEquals(
        "Parse must ignore extra whitespaces between arguments", expected,
        parse.parse("     echo     \"         f     s \"    "));
    Assert.assertArrayEquals("Parse must keep extra whitespaces within strings",
        expected, parse.parse("echo \"         f     s \""));
  }
 
  @Test
  public void parseTest2() {
    parse = new Parser();
    /*
     * Parser should take strings as an individual parameter. If the string
     * entered is invalid then return String[] tokens = {"~FailedParsing~"}
     */
    String[] expected = {"~FailedParsing~"};
    Assert.assertArrayEquals("Parser must return error when unclosed string",
        expected, parse.parse("\""));
    Assert.assertArrayEquals("Parser must return error when unclosed string",
        expected, parse.parse("echo \"aa "));
    Assert.assertArrayEquals("Parser must return error when unclosed string",
        expected, parse.parse("echo aa\" "));
    Assert.assertArrayEquals("Parser must return error when unclosed string",
        expected, parse.parse("echo\" "));
    Assert.assertArrayEquals(
        "Parser must return error when string is not formatted correctly",
        expected, parse.parse("echo -\"fdf\"a"));
  }

  @Test
  public void parseTest3() {
    parse = new Parser();
    /*
     * Parser should correctly separate a command line into correct arguments
     */
    String[] expected = {"ls"};
    Assert.assertArrayEquals("Parser should return just {\"ls\"}", expected,
        parse.parse("ls"));
    String[] expected2 = {"cat", "hi", "ds", "d", "s", "c"};
    Assert.assertArrayEquals("Parser must break command into correct tokens",
        expected2, parse.parse("cat hi ds d s c"));
    String[] expected3 =
        {"search", "dir1", "/dir1/dir2", "-type", "d", "-name", "\"dir4\""};
    Assert.assertArrayEquals("Parser must break command into correct tokens",
        expected3,
        parse.parse("search dir1 /dir1/dir2 -type d -name \"dir4\""));
  }



}
