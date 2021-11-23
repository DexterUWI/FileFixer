/*
COMP 3607 Object Oriented Programming II
2021/2022 Semester 1
Project

Team Members:
- Chelsea Joyeau: 816020515
- Videsh Jagai: 816014860
- Dexter Cain: 816021817
- Satash Rampersad: 816020134
*/

package com.filefixer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

/**
 * This class contains all methods related to the processing of the files to be
 * fixed
 */
public class ProcessFiles {

    private ArrayList<File> dataFiles;
    private ArrayList<Handler> dataHandlers;
    private ArrayList<Student> students;

    private File filesToRename = null;
    private File renamedFiles = null;

    private File csvFeedbackFile = null;

    private boolean foldersExistance = false;

    /**
     * Class constructor accepts the file paths(origin and destination) for the
     * files to be processed
     * 
     * @param origPath This is the path to the source folder where all of the PDF
     *                 files are located
     * @param newPath  This is the path to where the corrected files will be stored
     */
    public ProcessFiles(String origPath, String newPath) {
        dataFiles = new ArrayList<File>();
        dataHandlers = new ArrayList<Handler>();
        students = new ArrayList<Student>();

        filesToRename = new File(origPath);
        renamedFiles = new File(newPath);

        // Making sure it's a directory...
        // If the directory doesn't exist, the isDirectory() method will return false.
        if (filesToRename.isDirectory()) { //
            // int counter = 0;
            File[] allFiles = filesToRename.listFiles();

            System.out.println("\nThe CSV file in directory \"" + filesToRename + "\" is about to be processed...\n");

            // Loop through the directory and get the files
            for (final File f : allFiles) {
                try {
                    // execute operations only if file is of type csv
                    if (getFileExtension(f).equals("csv")) {
                        this.csvFeedbackFile = f;
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (csvFeedbackFile == null) {
                System.out.println("Error: CSV file does not exist. Please place CSV file in this directory: \n"
                        + filesToRename.getName());
                System.out.println("Terminating application now...\n");
                System.exit(-1);
            }

            if (!renamedFiles.exists()) {
                // create the \"renamedFiles\" folder if it was never created
                renamedFiles.mkdirs();
            }

            foldersExistance = true;
        } else {
            foldersExistance = false;
            return;
        }

    }

    /**
     * This class extracts all of the student data stored in the CSV data file
     */
    public void extractDataFromCSV() {
        Scanner inputFile = null;

        try {
            inputFile = new Scanner(csvFeedbackFile);

            inputFile.nextLine(); // skip the coloumn titles line

            while (inputFile.hasNextLine()) {
                String line = inputFile.nextLine();

                // here the string is splitted using regex based on a comma
                // The limit (-1) means that the result string array should consists of all
                // possible splits
                String[] studentInfo = line.split(",", -1);

                // supply any missing data that the data file did not provide
                if (studentInfo[0].length() == 0) {
                    studentInfo[0] = "[MISSING: IDENTIFIER]";
                }
                if (studentInfo[1].length() == 0) {
                    studentInfo[1] = "[MISSING: FULL NAME]";
                }
                if (studentInfo[2].length() == 0) {
                    studentInfo[2] = "[MISSING: ID]";
                }

                // create a new student object with the relevant details passed via parameters
                Student student = new Student(studentInfo[0], studentInfo[1], studentInfo[2]);

                if (student.getIDNumber().length() != 0) {
                    dataHandlers.add(new StudentIDHandler(student.getIDNumber(), students.size()));

                }
                if (student.getFullName().length() != 0) {
                    dataHandlers.add(new StudentNameHandler(student.getFullName(), students.size()));
                }

                // add the new student to the students array
                students.add(student);
            }

            // close input scanner
            inputFile.close();
        } catch (FileNotFoundException e) {
            System.out.println(e + "\nError: CSV file does not exist. Please place CSV file in this directory: \n"
                    + filesToRename.getName());
            System.out.println("Terminating application now...\n");
            System.exit(-1);
        }
    }

    /**
     * This class adds all of the data files from the folder to an arrayList for
     * easy traversing
     */
    public void appendDataFilesList() {
        for (File f : filesToRename.listFiles()) {
            if (!getFileExtension(f).equals("csv")) {
                if (getFileExtension(f).equals("zip")) {
                    unZip(f.getPath(), filesToRename.getAbsolutePath());
                }
                dataFiles.add(f);
            }
        }
    }

    /**
     * This method renames a single file, supplied via parameter, to the filename
     * required by myElearning. Since renameTo() returns a bool, it also checks to
     * make sure that each file as been renamed sucessfully.
     * 
     * @param file    This is a file object for the current file that is being
     *                processed
     * @param student This is a student object for the student whose information
     *                we're currently processing
     * @return filename of the file that we just renamed
     */
    public String renameFile(File file, Student student) {
        String filename = "";

        filename += student.getFullName() + "_" + student.getIdentfier() + "_" + "assignsubmission_file_"
                + file.getName();

        try {
            FileUtils.copyFileToDirectory(file, renamedFiles);

            System.out.println("\nCOPIED SUCESSFULLY : " + file.getName() + " has been copied to "
                    + renamedFiles.getName() + " folder.");

        } catch (IOException e) {

            e.printStackTrace();

        }

        File renamedFile = new File(renamedFiles.getAbsolutePath() + "/" + filename);
        File copiedFile = new File(renamedFiles + "/" + file.getName());
        boolean success = copiedFile.renameTo(renamedFile);
        if (!success) {
            System.out.println(
                    "\nCould not rename file \"" + copiedFile.getName() + "\" to \"" + renamedFile.getName() + "\".");
        } else {
            System.out.println(
                    "\nThe " + copiedFile.getName() + " file has been renamed to " + renamedFile.getName() + ".");
        }

        return filename;
    }

    /**
     * This method renames all files store in the dataFiles arrayList
     */
    public void renameFiles() {
        System.out.println(
                "Each file will be copied to the folder below and then renamed: " + renamedFiles.getAbsolutePath());

        for (File f : dataFiles) {
            for (Handler dataHandler : dataHandlers) {
                if (dataHandler.findStudentData(f) && students.get(dataHandler.getPositionInList()).getMissing()) {
                    students.get(dataHandler.getPositionInList()).setMissing(false); // file is not missing
                    Student student = students.get(dataHandler.getPositionInList());
                    renameFile(f, student);
                }
            }
        }
        System.out.println(
                "\nPLEASE NOTE: If the copy operation fails for any file, that file may already be present in the \""
                        + renamedFiles.getName() + "\" folder.");

    }

    /**
     * This method generates a list of missing submission files, accounted for in
     * the CSV data file, and stores the list in a string variable
     * 
     * @return The list of students with missing submission files
     */
    public String generateListOfMissingFiles() {
        String details = "\nList of students with missing submission files...\n";
        int lineNo = 1;
        for (Student s : students) {
            if (s.getMissing()) {
                details += lineNo + ". " + s.getFullName() + " : " + s.getIDNumber() + "\n";
                lineNo++;
            }
        }

        return details;
    }

    /**
     * This method exports the generated list of missing submission files. The list
     * is written to a file in the missingFilesList folder.
     * 
     */
    public void exportListOfMissingFiles() {
        String details = generateListOfMissingFiles();
        String fileExtension = ".txt";
        String currentDateTime = java.time.LocalDateTime.now().toString();
        String filePath = "src/main/files/filesToRename/missingFilesLists/";
        String filename = "missingFiles_" + currentDateTime + fileExtension;
        try {
            File fileObj = new File(filePath + filename);
            if (fileObj.createNewFile()) {
                System.out.println("File created: " + fileObj.getName());
                FileWriter fileWriter = new FileWriter(fileObj.getAbsolutePath());
                fileWriter.write(details);
                fileWriter.close();
                System.out.println("Successfully wrote the list of missing submission files to a file.");

            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * This method exports the generated list of missing submission files. The list
     * is written to a file in the missingFilesList folder.
     * 
     * @param zipFilePath This is the path of the zip file to be extracted
     * @param destDir     This is the destination/location to extract the zip file
     *                    contents to
     */
    private void unZip(String zipFilePath, String destDir) {
        File dir = new File(destDir);

        // create output directory if it doesn't exist
        if (!dir.exists()) {
            dir.mkdirs();
        }

        FileInputStream fis; // declare a variable for the FileInputStream object

        // buffer for read and write data to file
        byte[] buffer = new byte[2048];

        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to " + newFile.getAbsolutePath());

                // create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();

                // close this zipEntry
                zis.closeEntry();
                zipEntry = zis.getNextEntry();
            }

            // close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * getFileExtension() the extension of a file object that is passed via
     * parameter
     * 
     * @param file This is a file object for the current file that is being
     *             processed
     * @return The extension of the file object supplied
     */
    public String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * getFoldersExistance() gets the boolean value of the foldersExistance
     * variable. This variable is used to track the existance of the various folders
     * created
     * 
     * @return foldersExistance
     */
    public boolean getFoldersExistance() {
        return foldersExistance;
    }

    /**
     * getCSVFeedbackFile() gets the file object that was created for the
     * csvFeedbackFile file
     * 
     * @return csvFeedbackFile
     */
    public File getCSVFeedbackFile() {
        return csvFeedbackFile;
    }

    /**
     * getRenamedFiles() gets the file object that was created for the renamedFiles
     * folder
     * 
     * @return renamedFiles
     */
    public File getRenamedFiles() {
        return renamedFiles;
    }

    /**
     * getFilesToRename() gets the file object that was created for the fileToRename
     * folder
     * 
     * @return filesToRename
     */
    public File getFilesToRename() {
        return filesToRename;
    }

    /*
     * REFERENCES: https://stackabuse.com/java-check-if-a-file-or-directory-exists/
     * https://www.baeldung.com/java-last-modified-file
     * https://www.baeldung.com/java-file-directory-exists
     * https://www.javastring.net/java/string/split-method
     * https://www.javatpoint.com/java-get-current-date
     * https://www.w3schools.com/java/java_files_create.asp
     * https://www.journaldev.com/960/java-unzip-file-example
     * https://www.javacodegeeks.com/2015/09/chain-of-responsibility-design-pattern-
     * 2.html#google_vignette
     * https://refactoring.guru/design-patterns/chain-of-responsibility/java/example
     * https://www.delftstack.com/howto/java/java-split-string-by-space/#split-a-
     * string-using-the-split-method-in-java
     */

}
