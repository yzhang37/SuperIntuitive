package edu.bu.super_intuitive.models.grading;

public interface IAssignment {
    public int getAssignmentId();
    public ICourse getCourse() throws InstantiationException;

    public int getWeight();
    public void setWeight(int weight);

    public int getFullScore();
    public void setFullScore(int fullScore);

    public void setScore(IStudent student, int score);
    public int getScore(IStudent student);
    public void removeScore(IStudent student);
}
