package edu.bu.super_intuitive.models.grading;

public interface IStudent extends IMember {
    /**
     * @return 该学生 Attend 的所有课程
     */
    ICourse[] getAttendingCourses();
    /**
     * 返回该学生的某一项考试的成绩
     * @param assignment 考试
     * @return 成绩
     */
    double getAssignmentScore(IAssignment assignment);
    /**
     * 设置该学生的某一项考试的成绩
     * @param assignment 考试
     * @param score 成绩
     */
    void setAssignmentScore(IAssignment assignment, double score);
}
