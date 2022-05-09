package edu.bu.super_intuitive.service.mysql.grading;

import edu.bu.super_intuitive.middleware.mysql.Database;

import java.sql.SQLException;

public class Student extends Member {
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
}
