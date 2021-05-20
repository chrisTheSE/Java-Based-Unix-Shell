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
import org.junit.Before;
import org.junit.Test;
import data.File;

public class FileTest {

  private File file;

  @Before
  public void setUp() throws Exception
  {
    file = new File("Hai");

  }
  
  @Test
  public void getFileNameTest() {
    String actualOutput = file.getFileName();
    
    assertEquals("Hai", actualOutput);
  }
  
  
  @Test
  public void setFileNameTest() {
    
    file.setFileName("YOLO");
    
    String checkOutput = file.getFileName();
    
    assertEquals("YOLO", checkOutput);
  }
  
  @Test
  public void getContentTest() {
    String actualOutput = file.getContent();
    
    assertEquals("", actualOutput);
  }
  
  
  @Test
  public void setContentTest() {
    
    file.setContent("Good Stuff");
    
    String checkOutput = file.getContent();
    
    assertEquals("Good Stuff", checkOutput);
  }
  
}
