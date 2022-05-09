package edu.bu.super_intuitive.service.mysql.grading;
import edu.bu.super_intuitive.models.grading.IAssignment;
import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.models.grading.IInstructor;
import edu.bu.super_intuitive.models.grading.IStudent;

public class Course implements ICourse {
    @Override
    public IInstructor getInstructor() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getSemester() {
        return null;
    }

    @Override
    public void setSemester(String semester) {

    }

    @Override
    public IStudent[] getRegisteredStudents() {
        return new IStudent[0];
    }

    @Override
    public void addOneStudent(IStudent student) {

    }

    @Override
    public void addManyStudents(IStudent[] students) {

    }

    @Override
    public void removeOneStudent(IStudent student) {

    }

    @Override
    public void removeManyStudents(IStudent[] students) {

    }

    @Override
    public boolean checkRegistered(IStudent student) {
        return false;
    }

    @Override
    public void addAssignment(IAssignment assignment) {

    }

    @Override
    public void removeAssignment(IAssignment assignment) {

    }

    @Override
    public boolean hasAssignment(IAssignment assignment) {
        return false;
    }

    @Override
    public IAssignment[] getAssignments() {
        return new IAssignment[0];
    }
    // get all assignments for the course

}
