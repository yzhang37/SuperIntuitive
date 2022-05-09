package edu.bu.super_intuitive.service.mysql.grading;

import org.junit.jupiter.api.Test;

public class TestStudent {
    @Test
    public void testStudent() {
        try {
            Student student = new Student("U82871437");
            System.out.println(student.getName());
            System.out.println(student.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateNewUser() {
        try {
            Student student = new Student("U00000002", "a", "abc@bu.edu");
            System.out.println(student.getName());
            System.out.println(student.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testModifyUserProperty() {
        try {
            Student student = new Student("U00000002");
            student.setName("Test user name");
            student.setEmail("test@bu.edu");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
