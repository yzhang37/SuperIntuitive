/**
 * @Author Zhenghang Yin
 * @Description // IAssignment is the interface for the assignment.
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.models.grading;

import edu.bu.super_intuitive.models.exception.OperationFailed;

public interface IAssignment {
    int getAssignmentId();
    ICourse getCourse() throws InstantiationException;

    /**
     * @Author Zhenghang Yin
     * @return the weight of an assignment.
     * @throws OperationFailed
     */
    int getWeight() throws OperationFailed;

    /**
     * Set the score of an assignment for a student.
     * @param weight the weight of the assignment.
     * @throws OperationFailed
     */
    void setWeight(int weight) throws OperationFailed;

    /**
     * @Author Zhenghang Yin
     * @return the name of the assignment.
     * @throws OperationFailed
     */
    String getName() throws OperationFailed;
    /**
     * @Author Zhenghang Yin
     * @return the full score of the assignment.
     * @throws OperationFailed
     */
    int getFullScore() throws OperationFailed;

    /**
     * Set the full score of the assignment.
     * @Author Zhenghang Yin
     * @param fullScore the full score of the assignment.
     * @throws OperationFailed
     */
    void setFullScore(int fullScore) throws OperationFailed;

    /**
     * Set the score of an assignment for a student.
     * @param student the student.
     * @param score the score of the assignment.
     * @throws OperationFailed
     */
    void setStudentScore(IStudent student, int score) throws OperationFailed;

    /**
     * @Author Zhenghang Yin
     * @param student the student.
     * @return the score of the assignment for a student.
     * @throws OperationFailed
     */
    int getStudentScore(IStudent student) throws OperationFailed;

    /**
     * @Author Zhenghang Yin
     * @param student the student.
     * @return the score of the assignment for a student.
     */
    boolean hasStudentScore(IStudent student);

    /**
     * Remove the score of an assignment for a student.
     * @Author Zhenghang Yin
     * @param student the student.
     * @throws OperationFailed
     */
    void removeStudentScore(IStudent student) throws OperationFailed;
}
