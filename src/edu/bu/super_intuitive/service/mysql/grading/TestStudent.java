package edu.bu.super_intuitive.service.mysql.grading;

import org.junit.jupiter.api.Test;

public class TestStudent {
    @Test
    public void testStudent() {
        Student student = new Student("U82871437");
        System.out.println(student.getName());
        System.out.println(student.getEmail());
    }

    @Test
    public void testCreateNewUser() {
        Student student = new Student("U00000002", "a", "abc@bu.edu");
        System.out.println(student.getName());
        System.out.println(student.getEmail());
    }

    @Test
    public void testModifyUserProperty() {
        Student student = new Student("U00000002");
        student.setName("Test user name");
        student.setEmail("test@bu.edu");
    }
}
