/** 
 * COMP 3607 Object Oriented Programming II
 * 2021/2022 Semester 1
 * Project
 *
 * Team Members:
 * @author Chelsea Joyeau: 816020515
 * @author Videsh Jagai: 816014860
 * @author Dexter Cain: 816021817
 * @author Satash Rampersad: 816020134
 * @version 1.0 Nov 11, 2021
 */

package com.filefixer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for ProcessFiles class
 */
public class ProcessFilesTest {
    String origPath = "src/main/files/filesToRename";
        String newPath = "src/main/files/filesToRename/renamedFiles";

    private ProcessFiles fileProcessor = new ProcessFiles(origPath, newPath);
    
    @Test
    public void testgetFoldersExistance(){
        System.out.println("Testing getFoldersExistance() method in the ProcessFiles.java class...");
        assertTrue(fileProcessor.getFoldersExistance());
    }

    @Test
    public void testGetCSVFeedbackFile(){
        System.out.println("Testing getCSVFeedbackFile() method in the ProcessFiles.java class...");
        assertNotNull(fileProcessor.getCSVFeedbackFile());
    }

    @Test
    public void testGetRenamedFiles(){
        System.out.println("Testing getRenamedFiles() method in the ProcessFiles.java class...");
        assertNotNull(fileProcessor.getRenamedFiles());
    }

    @Test
    public void testGetFilesToRename(){
        System.out.println("Testing getFilesToRename() method in the ProcessFiles.java class...");
        assertNotNull(fileProcessor.getFilesToRename());
    }

    @Test
    public void testRenameFile(){
        System.out.println("Testing renameFile(File, Student) method in the ProcessFiles.java class...");
        fileProcessor.extractDataFromCSV();
        fileProcessor.appendDataFilesList();

        File f = new File("testing", ".pdf");
        Student student = new Student("Participant 112233", "Tom Hanks", "816001122");
        String expResult = "Tom Hanks_112233_assignsubmission_file_" + f.getName();;
        String actualResult = fileProcessor.renameFile(f, student);
        assertEquals(expResult, actualResult);
    }

    @Test
    public void testGetFileExtension(){
        System.out.println("Testing getFileExtension(File) method in the ProcessFiles.java class...");
        File f = new File("testing", ".pdf");
        String expResult = "pdf";
        String actualResult = fileProcessor.getFileExtension(f);
        assertEquals(expResult, actualResult);
    }
}
