package edu.bu.super_intuitive.models.grading;

import edu.bu.super_intuitive.models.exception.OperationFailed;

public interface ICourse {
    int getCourseId();
    /**
     * Return the IInstructor object for the course.
     * @return Object implementing IInstructor interface
     */
    IInstructor getInstructor() throws InstantiationException;

    /**
     * @return 课程的别名 (短名称)
     */
    String getAlias();
    /**
     * Set the short name of the course
     * @param alias short name of the class
     */
    void setAlias(String alias);

    /**
     * @return 课程的完整名称
     */
    String getName();
    /**
     * 设置课程的完整名称。
     * @param name Full name of the class
     */
    void setName(String name);

    /**
     * @return 课程的学期字符串
     */
    String getSemester();
    /**
     * 设置课程的学期字符串
     * @param semester The semester string
     */
    void setSemester(String semester);

    /**
     * Return the list of all students registered for the course.
     * @return Array of objects implementing IStudent interface.
     */
    IStudent[] getRegisteredStudents();
    /**
     * Add a student to the course.
     * @param student An object implementing IStudent interface
     */
    void registerStudent(IStudent student) throws OperationFailed;
    /**
     * Check if a student has been registered for the course.
     * @param student An object implementing IStudent interface
     * @return True if student is registered for the course, false otherwise
     */
    boolean hasStudent(IStudent student);
    /**
     * Remove a student from the course.
     * @param student An object implementing IStudent interface
     */
    void dropStudent(IStudent student) throws OperationFailed ;

    IAssignment addAssignment(String assignmentName, int fullScore, int weight) throws InstantiationException;
    void removeAssignment(IAssignment assignment) throws OperationFailed;
    boolean hasAssignment(IAssignment assignment);
    IAssignment[] getAssignments();
}
