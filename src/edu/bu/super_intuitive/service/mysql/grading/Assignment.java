package edu.bu.super_intuitive.service.mysql.grading;

import edu.bu.super_intuitive.middleware.mysql.Database;
import edu.bu.super_intuitive.models.exception.OperationFailed;
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
    public ICourse getCourse() throws InstantiationException {
        var failMessage = "";
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT course_id FROM assignments WHERE aid = ?");
            stmt.setInt(1, aid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Course(rs.getInt("course_id"));
            }
            failMessage = String.format("Database error, failed to get Course for Assignment aid=%d", this.aid);
            // 然后用其创建一个 Instructor 对象
        } catch (SQLException e) {
            e.printStackTrace();
            failMessage = e.getMessage();
        }
        throw new InstantiationException(failMessage);
    }

    @Override
    public int getWeight() {
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT weight FROM assignments WHERE aid = ?");
            stmt.setInt(1, aid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("weight");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void setWeight(int weight) throws OperationFailed {
        try {
            var stmt = Database.getConnection().prepareStatement("UPDATE assignments SET weight = ? WHERE aid = ?");
            stmt.setInt(1, weight);
            stmt.setInt(2, aid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OperationFailed(
                    String.format("Failed to update weight for assignment aid=%d", this.aid));
        }
    }

    @Override
    public int getFullScore() {
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT score FROM assignments WHERE aid = ?");
            stmt.setInt(1, aid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void setFullScore(int fullScore) throws OperationFailed {
        try {
            var stmt = Database.getConnection().prepareStatement("UPDATE assignments SET score = ? WHERE aid = ?");
            stmt.setInt(1, fullScore);
            stmt.setInt(2, aid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OperationFailed(
                    String.format("Failed to update full score for assignment aid=%d", this.aid));
        }
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
