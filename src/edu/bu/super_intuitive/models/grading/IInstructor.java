package edu.bu.super_intuitive.models.grading;

import edu.bu.super_intuitive.models.exception.OperationFailed;

public interface IInstructor extends IMember {
    public ICourse[] getOwnedCourses();
    public ICourse openCourse(String courseName,
                              String courseAlias,
                              String semester) throws InstantiationException;
    public void removeCourse(ICourse course) throws OperationFailed;
    public boolean hasOwnedCourse(ICourse course);
}
