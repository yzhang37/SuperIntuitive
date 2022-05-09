package edu.bu.super_intuitive.models.grading;

public interface IInstructor extends IMember {
    public ICourse[] getOwnedCourses();
    public void openCourse(ICourse course);
    public void removeCourse(ICourse course);
    public boolean hasOwnedCourse(ICourse course);
}
