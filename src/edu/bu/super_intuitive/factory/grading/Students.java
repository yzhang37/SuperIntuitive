package edu.bu.super_intuitive.factory.grading;

import edu.bu.super_intuitive.middleware.mysql.Database;
import edu.bu.super_intuitive.models.grading.IStudent;
import edu.bu.super_intuitive.service.mysql.grading.Student;

import java.sql.SQLException;
import java.util.ArrayList;


public class Students {
    /**
     * Get all student objects globally
     * @return Student object collection, A list of Student objects
     */
    public static IStudent[] getAllStudents() {
        ArrayList<IStudent> students = new ArrayList<>();
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT sid FROM staffs WHERE isInstructor <> TRUE");
            var rs = stmt.executeQuery();
            while (rs.next()) {
                try {
                    students.add(new Student(rs.getString("sid")));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        IStudent[] studentsArray = new IStudent[students.size()];
        students.toArray(studentsArray);
        return studentsArray;
    }

    /**
     * Fuzzy search using the student's username to display the student object.
     * @param name Part of student names
     * @return all student objects that satisfy the match.
     */
    public static IStudent[] getStudentsByFuzzySearchName(String name) {
        ArrayList<IStudent> students = new ArrayList<>();
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT sid FROM staffs WHERE isInstructor IS NOT TRUE AND name LIKE ?");
            stmt.setString(1, "%" + name + "%");
            var rs = stmt.executeQuery();
            while (rs.next()) {
                try {
                    students.add(new Student(rs.getString("sid")));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        IStudent[] studentsArray = new IStudent[students.size()];
        students.toArray(studentsArray);
        return studentsArray;
    }
}