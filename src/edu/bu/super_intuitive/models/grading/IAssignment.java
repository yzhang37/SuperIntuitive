package edu.bu.super_intuitive.models.grading;

import edu.bu.super_intuitive.models.exception.OperationFailed;

public interface IAssignment {
    public int getAssignmentId();
    public ICourse getCourse() throws InstantiationException;

    public int getWeight() throws OperationFailed;
    public void setWeight(int weight) throws OperationFailed;
    public String getName() throws OperationFailed;

    public int getFullScore() throws OperationFailed;
    public void setFullScore(int fullScore) throws OperationFailed;

    public void setStudentScore(IStudent student, int score) throws OperationFailed;
    public int getStudentScore(IStudent student) throws OperationFailed;
    public boolean hasStudentScore(IStudent student);
    public void removeStudentScore(IStudent student) throws OperationFailed;
}
