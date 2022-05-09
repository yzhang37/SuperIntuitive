package edu.bu.super_intuitive.models.grading;

public interface IInstructor extends IMember {
    public ICourse[] getOwnedCourses();
    public ICourse openCourse(String courseName,
                              String courseAlias,
                              String semester) throws InstantiationException;
    public void removeCourse(ICourse course);
    public boolean hasOwnedCourse(ICourse course);
}
