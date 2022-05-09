package edu.bu.super_intuitive.service.mysql.grading;

import edu.bu.super_intuitive.middleware.mysql.Database;
import edu.bu.super_intuitive.models.grading.IAssignment;
import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.models.grading.IStudent;

import java.sql.SQLException;
import java.util.Objects;

public class Student extends Member implements IStudent {
    public Student(String id) throws InstantiationException {
        super(id);
        checkAndUpdate(id);
    }

    public Student(String id, String name, String email) throws InstantiationException{
        super(id, name, email);
        checkAndUpdate(id);
    }

    private void checkAndUpdate(String id) throws InstantiationException {
        // 检查是否 isInstructor 为非 true
        boolean fail = false;
        String failMessage = "";
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT isInstructor FROM staffs WHERE sid = ?");
            stmt.setString(1, id);
            var rs = stmt.executeQuery();
            if (!rs.next()) {
                fail = true;
                failMessage = String.format("No such member with BuId = \"%s\"", id);
            } else {
                if (!rs.wasNull() && rs.getBoolean("isInstructor")) {
                    fail = true;
                    failMessage = String.format("Member with BuId = \"%s\" is an instructor. Cannot instantiate as a student.", id);
                } else if (rs.wasNull()) {
                    var stmt2 = Database.getConnection().prepareStatement("UPDATE staffs SET isInstructor = false WHERE sid = ?");
                    stmt2.setString(1, id);
                    stmt2.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail = true;
            failMessage = e.getMessage();
        }

        if (fail) {
            throw new InstantiationException(failMessage);
        }
    }

    @Override
    public ICourse[] getAttendingCourses() {
        ICourse[] courses = null;
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT COUNT(cid) FROM student_reg WHERE sid = ?");
            stmt.setString(1, getBUId());
            var rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            courses = new ICourse[count];

            var stmt2 = Database.getConnection().prepareStatement("SELECT cid FROM student_reg WHERE sid = ?");
            stmt2.setString(1, getBUId());
            var rs2 = stmt2.executeQuery();
            int i = 0;
            while (rs2.next()) {
                try {
                    courses[i] = new Course(rs2.getString("cid"));
                } catch (InstantiationException e) { e.printStackTrace();}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNullElseGet(courses, () -> new ICourse[0]);
    }

    @Override
    public double getAssignmentScore(IAssignment assignment) {
        try {
            var assign_id = assignment.getAssignmentId();
            var stmt = Database.getConnection().prepareStatement("SELECT score FROM student_score WHERE sid = ? AND aid = ?");
            stmt.setString(1, getBUId());
            stmt.setInt(2, assign_id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public void setAssignmentScore(IAssignment assignment, double score) {
        try {
            var assign_id = assignment.getAssignmentId();
            var stmt = Database.getConnection().prepareStatement("UPDATE student_score SET score = ? WHERE sid = ? AND aid = ?");
            stmt.setDouble(1, score);
            stmt.setString(2, getBUId());
            stmt.setInt(3, assign_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
