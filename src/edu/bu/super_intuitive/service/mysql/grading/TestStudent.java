package edu.bu.super_intuitive.service.mysql.grading;

import org.junit.jupiter.api.Test;

public class TestStudent {
    @Test
    public void testStudent() {
        Student student = new Student("U82871437");
        System.out.println(student.getName());
        System.out.println(student.getEmail());
    }
}
