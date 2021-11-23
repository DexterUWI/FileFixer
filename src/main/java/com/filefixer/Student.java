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
 * This concrete class defines a student and attributes relevant to them
 */
public class Student {
    private boolean isMissing = true;
    private String identifier;
    private String fullName;
    private String idNumber;

    /**
     * Default Student constructor
     * This constructor will be called when we want to instantiate a student object
     * 3 parameters are needed: Identifier, Full name, and ID number
     * @param identifier Participant identifier for a student
     * @param fullName   The full name of a student
     * @param idNumber   The student ID for a student
     */
    public Student(String identifier, String fullName, String idNumber) {

        // perform check for null, empty or a identifier string that containing only
        // spaces
        if (identifier != null && !identifier.trim().isEmpty()) {
            // splitting the identifier text imported from CSV file (column #1) based on a
            // space delimeter
            String[] tempIdentifier = identifier.trim().split("\\s+");
            this.identifier = tempIdentifier[1]; // use the portion of text after the space for the identifier
        } else {
            this.identifier = identifier;
        }

        // continue to assign parameters to local variables acccordingly
        this.fullName = fullName;
        this.idNumber = idNumber;
    }

    // Accessor methods - getters
    /** 
     * getIdentfier() 
     * @return identifier 
     */
    public String getIdentfier() {
        return this.identifier;
    }

    /** 
     * getFullName() 
     * @return fullName
     */
    public String getFullName() {
        return this.fullName;
    }

    /** 
     * getIDNumber() 
     * @return idNumber 
     */
    public String getIDNumber() {
        return this.idNumber;
    }

    /** 
     * getMissing() 
     * @return isMissing 
     */
    public boolean getMissing() {
        return this.isMissing;
    }

    /** 
     * Setter method for the isMissing boolean variable
     * @param isMissing This is the boolean value supplied to set value of the isMissing boolean object
     */
    public void setMissing(boolean isMissing) {
        this.isMissing = isMissing;
    }

}
