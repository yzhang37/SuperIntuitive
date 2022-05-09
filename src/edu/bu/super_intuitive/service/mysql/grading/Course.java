package edu.bu.super_intuitive.service.mysql.grading;
import edu.bu.super_intuitive.middleware.mysql.Database;
import edu.bu.super_intuitive.models.grading.IAssignment;
import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.models.grading.IInstructor;
import edu.bu.super_intuitive.models.grading.IStudent;

import java.sql.SQLException;

public class Course implements ICourse {
    private String cid;
    public String getCourseId() {
        return this.cid;
    }

    /**
     * 根据 cid，从数据库中实例化一个已有的 Course 课程。
     * 如果没有找到对应的记录，则抛出一个 InstantiationException
     * @param cid 课程的 cid
     */
    public Course(String cid) throws InstantiationException {
        var fail = false;
        var failMessage = "";
        try {
            // 根据 cid，从数据库中获取一条记录
            var stmt = Database.getConnection().prepareStatement("SELECT * FROM courses WHERE cid = ?");
            stmt.setString(1, cid);
            var rs = stmt.executeQuery();
            if (!rs.next()) {
                fail = true;
                failMessage = String.format(
                        "No course with cid = \"%s\", cannot instantiate.", cid);
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
    public IInstructor getInstructor() throws InstantiationException {
        var failMessage = "";
        // 首先获取表中对应的 instructor 字段
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT instructor FROM courses WHERE cid = ?");
            stmt.setString(1, cid);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                var instructor_sid = rs.getString("instructor");
                return new Instructor(instructor_sid);
            }
            failMessage = String.format("Database error, no instructor for course %s", cid);
            // 然后用其创建一个 Instructor 对象
        } catch (SQLException e) {
            e.printStackTrace();
            failMessage = e.getMessage();
        }
        throw new InstantiationException(failMessage);
    }

    @Override
    public String getAlias() {
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT alias FROM courses WHERE cid = ?");
            stmt.setString(1, cid);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("alias");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setAlias(String alias) {
        try {
            var stmt = Database.getConnection().prepareStatement("UPDATE courses SET alias = ? WHERE cid = ?");
            stmt.setString(1, alias);
            stmt.setString(2, cid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT name FROM courses WHERE cid = ?");
            stmt.setString(1, cid);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setName(String name) {
        try {
            var stmt = Database.getConnection().prepareStatement("UPDATE courses SET name = ? WHERE cid = ?");
            stmt.setString(1, name);
            stmt.setString(2, cid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getSemester() {
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT semester FROM courses WHERE cid = ?");
            stmt.setString(1, cid);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("semester");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setSemester(String semester) {
        try {
            var stmt = Database.getConnection().prepareStatement("UPDATE courses SET semester = ? WHERE cid = ?");
            stmt.setString(1, semester);
            stmt.setString(2, cid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IStudent[] getRegisteredStudents() {
        return new IStudent[0];
    }

    @Override
    public void addOneStudent(IStudent student) {

    }

    @Override
    public void addManyStudents(IStudent[] students) {

    }

    @Override
    public void removeOneStudent(IStudent student) {

    }

    @Override
    public void removeManyStudents(IStudent[] students) {

    }

    @Override
    public boolean checkRegistered(IStudent student) {
        return false;
    }

    @Override
    public void addAssignment(IAssignment assignment) {

    }

    @Override
    public void removeAssignment(IAssignment assignment) {

    }

    @Override
    public boolean hasAssignment(IAssignment assignment) {
        return false;
    }

    @Override
    public IAssignment[] getAssignments() {
        return new IAssignment[0];
    }
    // get all assignments for the course

}
