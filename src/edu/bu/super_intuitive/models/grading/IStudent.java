/**
 * @Author Zhenghang Yin
 * @Description // IMember is the interface for the member class.
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.models.grading;

public interface IStudent extends IMember {
    /**
     * @return Return all courses that the student Attended
     */
    ICourse[] getAttendingCourses();
    /**
     * Returns the student's score for a particular test
     * @param assignment assignment object
     * @return score
     */
    double getAssignmentScore(IAssignment assignment);
    /**
     * Set the student's score for a particular exam
     * @param assignment assignment object
     * @param score score object
     */
    void setAssignmentScore(IAssignment assignment, double score);
}
