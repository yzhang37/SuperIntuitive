package edu.bu.super_intuitive.service.mysql.grading;

import edu.bu.super_intuitive.models.grading.IAssignment;
import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.models.grading.IStudent;

public class Assignment implements IAssignment {
    private int aid;

    public int getAssignmentId() {
        return aid;
    }

    @Override
    public ICourse getCourse() {
        return null;
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public void setWeight(int weight) {

    }

    @Override
    public int getFullScore() {
        return 0;
    }

    @Override
    public void setFullScore(int fullScore) {

    }

    @Override
    public void setScore(IStudent student, int score) {

    }

    @Override
    public int getScore(IStudent student) {
        return 0;
    }

    @Override
    public void removeScore(IStudent student) {

    }
}
