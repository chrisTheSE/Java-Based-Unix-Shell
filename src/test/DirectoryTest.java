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
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import data.Directory;
import data.File;

public class DirectoryTest {
  
  private Directory directory;

  @Before
  public void setUp() throws Exception
  {
    directory = new Directory("Nani");

  }
  
  @Test
  public void getFileTest1() {
    
    File file1 = new File("yes");
    file1.setContent("AGHA");
    File file2 = new File("no");
    file2.setContent("IAGAGAGA ASGAG@%%");
    File file3 = new File("okay");
    file3.setContent("iahgfhasf 23562536 @#%@#%");
    directory.addFile(file1);
    directory.addFile(file2);
    directory.addFile(file3);

    
    File actualOutput = directory.getFileByFileName("no");
    
    assertEquals(file2, actualOutput);
  }
  
  @Test
  public void getFileTest2() {
    
    File actualOutput = directory.getFileByFileName("iDontExist");
    
    assertEquals(null, actualOutput);
  }
  
  
  @Test
  public void removeFileTest1() {
    
    File file1 = new File("insane");
    file1.setContent("Atestsss");
    File file2 = new File("lit");
    file2.setContent("PogChamp");
    File file3 = new File("cannotSee");
    file3.setContent("dokay");
    
    directory.addFile(file1);
    directory.addFile(file2);
    directory.addFile(file3);
    
    directory.removeFileByFileName(file3.getFileName());
    
    File actuaOutput = directory.getFileByFileName("okay");
    
    assertEquals(null, actuaOutput);
  }
  
  
  @Test
  public void removeFileTest2() {
    
    File file1 = new File("Bonjour");
    file1.setContent("Petit");
    File file2 = new File("Bono");
    file2.setContent("CHICHIHUA");
    
    directory.addFile(file1);
    directory.addFile(file2);
    
    directory.removeFileByFileName("nonExistantFileName");
    
    List<File> expectedFiles = new ArrayList<File>();
    expectedFiles.add(file1);
    expectedFiles.add(file2);
    
    List<File> actualFiles = directory.getFiles();
    
    assertEquals(expectedFiles, actualFiles);
  }
  
  
  @Test
  public void removeFileTest3() {
    
    List<File> expectedFiles = directory.getFiles();
    
    directory.removeFileByFileName("nonExistantFileName");
    
    List<File> actualFiles = directory.getFiles();
    
    assertEquals(expectedFiles, actualFiles);
  }
  
  
  @Test
  public void isFileInsideByFileNameTest1() {
    
    File file1 = new File("Haimont");
    file1.setContent("PBRO");
    File file2 = new File("CHINGong");
    file2.setContent("asghagh");
    
    directory.addFile(file1);
    directory.addFile(file2);
    
    boolean actualOutput = directory.isFileInsideByFileName(
        file2.getFileName());
    
    assertEquals(true, actualOutput);
  }
  
  
  @Test
  public void isFileInsideByFileNameTest2() {
    
    File file1 = new File("agfagag");
    file1.setContent("oka");
    File file2 = new File("asgt");
    file2.setContent("Cggg");
    
    directory.addFile(file1);
    directory.addFile(file2);
    
    boolean actualOutput = directory.isFileInsideByFileName(
        "nonExistantFileName");
    
    assertEquals(false, actualOutput);
  }
  
  
  @Test
  public void isFileInsideByFileNameTest3() {
    
    boolean actualOutput = directory.isFileInsideByFileName(
        "nonExistantFileName");
    
    assertEquals(false, actualOutput);
  }
  
}
