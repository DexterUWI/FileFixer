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
 * This Handler interface is used to achieve abstraction. It is used to declare the methods that will be defined in our subclasses
 */
public interface Handler {

    /** 
     * findStudentData(File) searches for a student's data in the filename of a file and returns true if found and false otherwise
     * @param file Holds a file object that contains the filename to be processed
     * @return boolean value
     */
    public boolean findStudentData(File file);

    /** 
     * getHandlerName() gets the name the current handler
     * @return handlerName 
     */
    public String getHandlerName();

     /** 
     * getPositionInList() gets the location of a student in the students array
     * @return positionInList 
     */
    public int getPositionInList();

     /** 
     * getStudentData() gets a student's data that will used for processing
     * @return studentID 
     */
    public String getStudentData();
    
}
