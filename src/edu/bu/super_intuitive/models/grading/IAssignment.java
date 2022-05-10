package edu.bu.super_intuitive.models.grading;

import edu.bu.super_intuitive.models.exception.OperationFailed;

public interface IAssignment {
    int getAssignmentId();
    ICourse getCourse() throws InstantiationException;

    int getWeight() throws OperationFailed;
    void setWeight(int weight) throws OperationFailed;
    String getName() throws OperationFailed;

    int getFullScore() throws OperationFailed;
    void setFullScore(int fullScore) throws OperationFailed;

    void setStudentScore(IStudent student, int score) throws OperationFailed;
    int getStudentScore(IStudent student) throws OperationFailed;
    boolean hasStudentScore(IStudent student);
    void removeStudentScore(IStudent student) throws OperationFailed;
}
