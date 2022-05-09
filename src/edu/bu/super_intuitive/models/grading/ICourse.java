package edu.bu.super_intuitive.models.grading;

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
    public void addOneStudent(IStudent student);
    /**
     * Add many students to the course.
     * @param students Array of objects implementing IStudent interface
     */
    public void addManyStudents(IStudent[] students);
    /**
     * Remove a student from the course.
     * @param student An object implementing IStudent interface
     */
    public void removeOneStudent(IStudent student);
    /**
     * Remove many students from the course.
     * @param students Array of objects implementing IStudent interface
     */
    public void removeManyStudents(IStudent[] students);
    public default void removeAllStudent() {
        removeManyStudents(getRegisteredStudents());
    }
    /**
     * Check if a student is registered for the course.
     * @param student An object implementing IStudent interface
     * @return True if student is registered for the course, false otherwise
     */
    public boolean checkRegistered(IStudent student);

    public void addAssignment(IAssignment assignment);
    public void removeAssignment(IAssignment assignment);
    public boolean hasAssignment(IAssignment assignment);
    public IAssignment[] getAssignments();
}
