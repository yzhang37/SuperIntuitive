package edu.bu.super_intuitive.service.mysql.grading;

import edu.bu.super_intuitive.middleware.mysql.Database;
import edu.bu.super_intuitive.models.grading.IAssignment;
import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.models.grading.IStudent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Assignment implements IAssignment {
    private final int aid;

    public Assignment(int aid) throws InstantiationException {
        this.aid = aid;
        // 验证当前 id 的数据是否存在
        boolean fail = false;
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT * FROM assignments WHERE aid = ?");
            stmt.setInt(1, aid);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                fail = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail = true;
        }
        if (fail) {
            throw new InstantiationException(
                    String.format("Assignment with aid = \"%d\" not exist.", aid));
        }
    }

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
