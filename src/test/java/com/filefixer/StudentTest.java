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
import org.junit.jupiter.api.*;



/**
 * Unit tests for StudentTest class
 */
public class StudentTest 
{
    private Student student;

    @BeforeEach
    public void initialize(){
        student = new Student("Participant 112233", "Tom Hanks", "816001122");
    }

    @Test
    public void testGetFullName(){
        System.out.println("Testing getFullName() method in the Student class...");
        String expResult = "Tom Hanks";
        String actualResult = student.getFullName();
        assertEquals(expResult, actualResult);
    }
    
    @Test
    public void testGetIDNumber(){
        System.out.println("Testing getIDNumber() method in the Student class...");
        String expResult = "816001122";
        String actualResult = student.getIDNumber();
        assertEquals(expResult, actualResult);
    }

    @Test
    public void testGetIdentfier(){
        System.out.println("Testing getIdentfier() method in the Student class...");
        String expResult = "112233";
        String actualResult = student.getIdentfier();
        assertEquals(expResult, actualResult);
    }

    @Test
    public void testSetMissing(){
        System.out.println("Testing setMissing(boolean) method in the Student class...");
        boolean expResult = false;
        student.setMissing(false);
        boolean actualResult = student.getMissing();
        assertEquals(expResult, actualResult);
    }
}
