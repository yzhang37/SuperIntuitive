package edu.bu.super_intuitive.service.mysql.grading;

public class Student extends Member {
    public Student(String id) {
        super(id);
    }

    public Student(String id, String name, String email) {
        super(id, name, email);
    }
}
