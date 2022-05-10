/**
 * @Author Zhenghang Yin
 * @Description // Constructor for Assignment class
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
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

    // Constructor for Assignment class
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

    // Get current Assignment's id
    public int getAssignmentId() {
        return aid;
    }

    // Get current Assignment's course
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

    // Get current Assignment's due date
    @Override
    public int getWeight() throws OperationFailed {
        var failMessage = "";
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT weight FROM assignments WHERE aid = ?");
            stmt.setInt(1, aid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("weight");
            }
            failMessage = String.format("Database error, failed to get weight for Assignment aid=%d", this.aid);
        } catch (SQLException e) {
            e.printStackTrace();
            failMessage = e.getMessage();
        }
        throw new OperationFailed(failMessage);
    }

    // Get current Assignment's name
    public String getName() throws OperationFailed {
        var failMessage = "";
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT name FROM assignments WHERE aid = ?");
            stmt.setInt(1, aid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
            failMessage = String.format("Database error, failed to get name for Assignment aid=%d", this.aid);
        } catch (SQLException e) {
            e.printStackTrace();
            failMessage = e.getMessage();
        }
        throw new OperationFailed(failMessage);
    }

    // Setters
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
    public int getFullScore() throws OperationFailed  {
        var failMessage = "";
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT score FROM assignments WHERE aid = ?");
            stmt.setInt(1, aid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("score");
            }
            failMessage = String.format("Database error, failed to get full score for Assignment aid=%d", this.aid);
        } catch (SQLException e) {
            e.printStackTrace();
            failMessage = e.getMessage();
        }
        throw new OperationFailed(failMessage);
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
    public void setStudentScore(IStudent student, int score) throws OperationFailed {
        if (!hasStudentScore(student)) {
            try {
                var stmt = Database.getConnection().prepareStatement("INSERT INTO student_score (sid, aid, score) VALUES (?, ?, ?)");
                stmt.setString(1, student.getBUId());
                stmt.setInt(2, aid);
                stmt.setInt(3, score);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new OperationFailed(String.format(
                        "Failed to set student sid=%s score for assignment aid=%d", student.getBUId(), this.aid));
            }
        }
    }

    @Override
    public int getStudentScore(IStudent student) throws OperationFailed  {
        if (!hasStudentScore(student)) {
            throw new OperationFailed(String.format(
                "Student sid=%s has no score for assignment aid=%d", student.getBUId(), this.aid));
        }
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT score FROM student_score WHERE sid = ? AND aid = ?");
            stmt.setString(1, student.getBUId());
            stmt.setInt(2, aid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Returns true if the student has a score for this assignment
    @Override
    public boolean hasStudentScore(IStudent student) {
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT * FROM student_score WHERE aid = ? AND sid = ?");
            stmt.setInt(1, aid);
            stmt.setString(2, student.getBUId());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Remove the student's score for this assignment
    @Override
    public void removeStudentScore(IStudent student) throws OperationFailed {
        if (!hasStudentScore(student)) {
            throw new OperationFailed(String.format(
                    "Assignment aid=%d are not been done by student %s", this.aid, student.getBUId()));
        }
        try {
            var stmt = Database.getConnection().prepareStatement("DELETE FROM student_score WHERE aid = ? AND sid = ?");
            stmt.setInt(1, aid);
            stmt.setString(2, student.getBUId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OperationFailed(String.format(
                "Failed to remove student sid=%s score for assignment aid=%d", student.getBUId(), this.aid));
        }
    }
}
