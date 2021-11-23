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

import java.io.File;

/**
 * This concrete handler contains the actual code for processing student search requests. 
 */
public class StudentNameHandler implements Handler {

    private String handlerName;
    private String studentName;
    private int positionInList;

    /**
     * Class constructor accepts the necessary data(A student's name and their position in the students list) for processing student data.
     * @param studentName This is the full name of a student.
     * @param positionInList This is the location of the student in the students array list.
     */
    public StudentNameHandler(String studentName, int positionInList) {
        this.studentName = studentName;
        this.positionInList = positionInList;
        handlerName = this.getClass().getSimpleName();
    }

     /** 
     * findStudentData(File) searches for a student's data in the filename of a file and returns true if found and false otherwise
     * @param file Holds a file object that contains the filename to be processed
     * @return boolean value
     */
    @Override
    public boolean findStudentData(File file) {
        String filename = file.getName().toLowerCase();
        String[] studentNamesArr = getStudentData().toLowerCase().split("-| ");

        boolean found = true;

        for (String s : studentNamesArr) {
            if (!filename.contains(s.replaceAll("\\s", ""))) {
                found = false;
                break;
            }
        }

        if (found) {
            System.out.println("Found student's data in filename by using the " + getHandlerName());
        }

        return found;
    }
  /** 
     * getHandlerName() gets the name the current handler
     * @return handlerName 
     */
    @Override
    public String getHandlerName() {
        return this.handlerName;
    }

     /** 
     * getPositionInList() gets the location of a student in the students array
     * @return positionInList 
     */
    @Override
    public int getPositionInList(){
        return this.positionInList;
    }

    /** 
     * getStudentData() gets a student's data that will used for processing
     * @return studentID 
     */
    @Override
    public String getStudentData(){
        return this.studentName;
    }

}
