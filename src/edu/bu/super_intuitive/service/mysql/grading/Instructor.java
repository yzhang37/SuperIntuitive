/**
 * @Author Zhenghang Yin
 * @Description // Constructor for Instructor class
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.service.mysql.grading;

import edu.bu.super_intuitive.middleware.mysql.Database;
import edu.bu.super_intuitive.models.exception.OperationFailed;
import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.models.grading.IInstructor;

import java.sql.SQLException;
import java.util.Objects;

public class Instructor extends Member implements IInstructor {

    // Constructor for Instructor class
    public Instructor(String id) throws InstantiationException {
        super(id);
        checkAndUpdate(id);
    }

    public Instructor(String id, String name, String email) throws InstantiationException {
        super(id, name, email);
        checkAndUpdate(id);
    }

    // Check if the instructor has the course
    private void checkAndUpdate(String id) throws InstantiationException {
        // Check if isInstructor is not false
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
                if (!rs.wasNull() && !rs.getBoolean("isInstructor")) {
                    fail = true;
                    failMessage = String.format("Member with BuId = \"%s\" is a student. Cannot be instantiated as a teacher.", id);
                } else if (rs.wasNull()) {
                    var stmt2 = Database.getConnection().prepareStatement("UPDATE staffs SET isInstructor = true WHERE sid = ?");
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

    // Get the courses taught by the instructor
    @Override
    public ICourse[] getOwnedCourses() {
        // Find courses.instructor = sid in courses based on the existing sid
        ICourse[] courses = null;
        try {
            // Execute a pre-query first to get the total number of
            var stmt = Database.getConnection().prepareStatement("SELECT COUNT(*) FROM courses WHERE instructor = ?");
            stmt.setString(1, this.getBUId());
            var rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            courses = new ICourse[count];

            var stmt2 = Database.getConnection().prepareStatement("SELECT cid FROM courses WHERE instructor = ?");
            stmt2.setString(1, this.getBUId());
            rs = stmt2.executeQuery();

            int i = 0;
            while (rs.next()) {
                try {
                    courses[i] = new Course(rs.getInt("cid"));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNullElseGet(courses, () -> new ICourse[0]);
    }

    // Open a course for the instructor
    @Override
    public ICourse openCourse(String courseName,
                              String courseAlias,
                              String semester) throws InstantiationException {
        // First create a new course and set its instructor to your own sid
        try {
            // Get the next id that is free first
            var stmt1 = Database.getConnection().prepareStatement("SELECT MAX(cid) FROM courses");
            var rs = stmt1.executeQuery();
            rs.next();
            int nextId = rs.getInt(1) + 1;

            var stmt = Database.getConnection().prepareStatement(
                    "INSERT INTO courses (cid, name, alias, semester, instructor) VALUES (?, ?, ?, ?, ?);");
            stmt.setInt(1, nextId);
            stmt.setString(2, courseName);
            stmt.setString(3, courseAlias);
            stmt.setString(4, semester);
            stmt.setString(5, this.getBUId());
            stmt.executeUpdate();

            return new Course(nextId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InstantiationException("Cannot create a new course");
        }
    }

    // Remove a course from the instructor's list
    @Override
    public void removeCourse(ICourse course) throws OperationFailed {
        if (!hasOwnedCourse(course)) {
            throw new OperationFailed(String.format(
                "Instructor sid=%s does not own course cid=%d, so cannot drop it.", this.getBUId(), course.getCourseId()));
        }
        try {
            var stmt = Database.getConnection().prepareStatement("DELETE FROM courses WHERE cid = ? AND instructor = ?");
            stmt.setInt(1, course.getCourseId());
            stmt.setString(2, this.getBUId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OperationFailed("Cannot drop course");
        }
    }


    // Return the list of students in a course
    @Override
    public boolean hasOwnedCourse(ICourse course) {
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT COUNT(*) FROM courses WHERE instructor = ? AND cid = ?");
            stmt.setString(1, this.getBUId());
            stmt.setInt(2, course.getCourseId());
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
