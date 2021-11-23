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

/**
 * This class exceutes the main method to run the application
 */
public class FileFixer {

    /**
     * Main method creates an object of the ProcessFiles class and then invokes
     * several relevant methods
     * @param args - Arguments supplied via terminal
     */
    public static void main(String[] args) {
        String origPath;
        String newPath;

        if(args.length>0){
            origPath = args[0];
            newPath = args[1];
        }else{
            origPath = "src/main/files/filesToRename";
            newPath = "src/main/files/filesToRename/renamedFiles";
        }

        ProcessFiles fileProcessor = new ProcessFiles(origPath, newPath);
        if (!fileProcessor.getFoldersExistance()) {
            System.out
                    .println("Error: " + origPath + " doesn't exits. Please create the directory and then try again.");
            return;
        }

        fileProcessor.extractDataFromCSV();
        fileProcessor.appendDataFilesList();

        fileProcessor.renameFiles();
        System.out.println(fileProcessor.generateListOfMissingFiles());
        fileProcessor.exportListOfMissingFiles();

    }

    /**
     * REFERENCES:
     * https://www.javacodegeeks.com/2015/09/chain-of-responsibility-design-pattern-2.html
     */

}
