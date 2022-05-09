package edu.bu.super_intuitive.models.grading;

public interface IStudent extends IMember {
    public ICourse[] getAttendingCourses();
    public IAssignment[] getTakingAssignments();
}
