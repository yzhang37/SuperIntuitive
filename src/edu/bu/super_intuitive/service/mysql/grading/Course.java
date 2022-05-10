package edu.bu.super_intuitive.service.mysql.grading;
import edu.bu.super_intuitive.middleware.mysql.Database;
import edu.bu.super_intuitive.models.exception.OperationFailed;
import edu.bu.super_intuitive.models.grading.IAssignment;
import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.models.grading.IInstructor;
import edu.bu.super_intuitive.models.grading.IStudent;

import java.sql.SQLException;
import java.util.Objects;

public class Course implements ICourse {
    private final int cid;

    public int getCourseId() {
        return this.cid;
    }

    /**
     * 根据 cid，从数据库中实例化一个已有的 Course 课程。
     * 如果没有找到对应的记录，则抛出一个 InstantiationException
     * @param cid 课程的 cid
     */
    public Course(int cid) throws InstantiationException {
        this.cid = cid;
        var fail = false;
        var failMessage = "";
        try {
            // 根据 cid，从数据库中获取一条记录
            var stmt = Database.getConnection().prepareStatement("SELECT * FROM courses WHERE cid = ?");
            stmt.setInt(1, cid);
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
            stmt.setInt(1, this.getCourseId());
            var rs = stmt.executeQuery();
            if (rs.next()) {
                var instructor_sid = rs.getString("instructor");
                return new Instructor(instructor_sid);
            }
            failMessage = String.format("Database error, no instructor for course %d", this.getCourseId());
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
            stmt.setInt(1, this.getCourseId());
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
            stmt.setInt(2, this.getCourseId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT name FROM courses WHERE cid = ?");
            stmt.setInt(1, this.getCourseId());
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
            stmt.setInt(2, this.getCourseId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getSemester() {
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT semester FROM courses WHERE cid = ?");
            stmt.setInt(1, this.getCourseId());
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
            stmt.setInt(2, this.getCourseId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IStudent[] getRegisteredStudents() {
        IStudent[] students = null;
        try {
            // 先获取有多少学生注册了
            var stmt = Database.getConnection().prepareStatement("SELECT COUNT(*) FROM student_reg WHERE cid = ?");
            stmt.setInt(1, this.getCourseId());
            var rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            students = new IStudent[count];
            // 依次获取每个学生的 sid
            var stmt2 = Database.getConnection().prepareStatement("SELECT sid FROM student_reg WHERE cid = ?");
            stmt2.setInt(1, this.getCourseId());
            rs = stmt2.executeQuery();
            int i = 0;
            while (rs.next()) {
                try {
                    students[i] = new Student(rs.getString("sid"));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNullElseGet(students, () -> new IStudent[0]);
    }

    @Override
    public void registerStudent(IStudent student) throws OperationFailed {
        try {
            // 首先检查是否已经注册了一个学生
            if (this.hasStudent(student)) {
                throw new OperationFailed(String.format(
                        "Student with BuId=%s has already registered in course with cid=%d", student.getBUId(), this.getCourseId()));
            }
            var stmt = Database.getConnection().prepareStatement("INSERT INTO student_reg (sid, cid) VALUES (?, ?)");
            stmt.setString(1, student.getBUId());
            stmt.setInt(2, this.getCourseId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OperationFailed("Failed to add student to course:\n " + e.getMessage());
        }
    }

    @Override
    public boolean hasStudent(IStudent student) {
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT * FROM student_reg WHERE cid = ? AND sid = ?");
            stmt.setInt(1, this.getCourseId());
            stmt.setString(2, student.getBUId());
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void dropStudent(IStudent student) throws OperationFailed {
        try {
            if (!this.hasStudent(student)) {
                throw new OperationFailed(String.format(
                        "Student with BuId=%s has not registered in course with cid=%d", student.getBUId(), this.getCourseId()));
            }
            var stmt = Database.getConnection().prepareStatement("DELETE FROM student_reg WHERE cid = ? AND sid = ?");
            stmt.setInt(1, this.getCourseId());
            stmt.setString(2, student.getBUId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OperationFailed("Failed to drop student from course:\n " + e.getMessage());
        }
    }

    @Override
    public IAssignment addAssignment(String assignmentName, int fullScore, int weight) throws InstantiationException {
        // 先创建一个新的作业，并设置它的 course_id 为自己的 cid
        try {
            // 先获取下一个空闲的 id
            var stmt1 = Database.getConnection().prepareStatement("SELECT MAX(aid) FROM assignments");
            var rs = stmt1.executeQuery();
            rs.next();
            var nextId = rs.getInt(1) + 1;

            var stmt2 = Database.getConnection().prepareStatement("INSERT INTO assignments (course_id, name, score, weight) VALUES (?, ?, ?, ?)");
            stmt2.setInt(1, this.getCourseId());
            stmt2.setString(2, assignmentName);
            stmt2.setInt(3, fullScore);
            stmt2.setInt(4, weight);
            stmt2.executeUpdate();

            return new Assignment(nextId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InstantiationException("Failed to add assignment to course:\n " + e.getMessage());
        }
    }

    @Override
    public void removeAssignment(IAssignment assignment) throws OperationFailed {
        try {
            if (!this.hasAssignment(assignment)) {
                throw new OperationFailed(String.format(
                    "Assignment with aid=%d is not created by course with cid=%d", assignment.getAssignmentId(), this.getCourseId()));
            }
            var stmt = Database.getConnection().prepareStatement("DELETE FROM assignments WHERE aid = ?");
            stmt.setInt(1, assignment.getAssignmentId());
            stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OperationFailed("Failed to remove assignment from course:\n " + e.getMessage());
        }
    }

    @Override
    public boolean hasAssignment(IAssignment assignment) {
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT course_id FROM assignments WHERE aid = ?");
            stmt.setInt(1, assignment.getAssignmentId());
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public IAssignment[] getAssignments() {
        // 查询 assignments 表格中所有 course_id = cid 的作业，然后返回列表
        IAssignment[] assignments = null;

        try {
            // 先获取总数
            var stmt = Database.getConnection().prepareStatement("SELECT COUNT(*) FROM assignments WHERE course_id = ?");
            stmt.setInt(1, this.getCourseId());
            var rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            // 再获取作业列表
            assignments = new IAssignment[count];
            var stmt2 = Database.getConnection().prepareStatement("SELECT aid FROM assignments WHERE course_id = ?");
            stmt2.setInt(1, this.getCourseId());
            rs = stmt2.executeQuery();

            int i = 0;
            while (rs.next()) {
                try {
                    assignments[i] = new Assignment(rs.getInt(1));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNullElseGet(assignments, () -> new IAssignment[0]);
    }
}
