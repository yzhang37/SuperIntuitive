package edu.bu.super_intuitive.models.grading;

import edu.bu.super_intuitive.models.exception.OperationFailed;

public interface ICourse {
    public int getCourseId();
    /**
     * Return the IInstructor object for the course.
     * @return Object implementing IInstructor interface
     */
    public IInstructor getInstructor() throws InstantiationException;

    /**
     * @return 课程的别名 (短名称)
     */
    public String getAlias();
    /**
     * 设置课程的短名称
     * @param alias short name of the class
     */
    public void setAlias(String alias);

    /**
     * @return 课程的完整名称
     */
    public String getName();
    /**
     * 设置课程的完整名称。
     * @param name Full name of the class
     */
    public void setName(String name);

    /**
     * @return 课程的学期字符串
     */
    public String getSemester();
    /**
     * 设置课程的学期字符串
     * @param semester The semester string
     */
    public void setSemester(String semester);

    /**
     * Return the list of all students registered for the course.
     * @return Array of objects implementing IStudent interface.
     */
    public IStudent[] getRegisteredStudents();
    /**
     * Add a student to the course.
     * @param student An object implementing IStudent interface
     */
    public void registerStudent(IStudent student) throws OperationFailed;
    /**
     * Check if a student has been registered for the course.
     * @param student An object implementing IStudent interface
     * @return True if student is registered for the course, false otherwise
     */
    public boolean hasStudent(IStudent student);
    /**
     * Remove a student from the course.
     * @param student An object implementing IStudent interface
     */
    public void dropStudent(IStudent student) throws OperationFailed ;

    public IAssignment addAssignment(String assignmentName, int fullScore, int weight) throws InstantiationException;
    public void removeAssignment(IAssignment assignment) throws OperationFailed;
    public boolean hasAssignment(IAssignment assignment);
    public IAssignment[] getAssignments();
}
