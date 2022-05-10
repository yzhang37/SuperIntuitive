package edu.bu.super_intuitive.factory.grading;

import edu.bu.super_intuitive.middleware.mysql.Database;
import edu.bu.super_intuitive.models.grading.IStudent;
import edu.bu.super_intuitive.service.mysql.grading.Student;

import java.sql.SQLException;
import java.util.Objects;

public class Students {
    /**
     * 获取全局的所有学生对象
     * @return 学生对象集合, A list of Student 对象
     */
    public static IStudent[] getAllStudents() {
        IStudent[] students = null;
        try {
            // 先获取一共有多少个学生
            var stmt = Database.getConnection().prepareStatement("SELECT COUNT(*) FROM staffs WHERE isInstructor IS NOT NULL");
            var rs = stmt.executeQuery();
            rs.next();
            var count = rs.getInt(1);

            // 再获取全部学生
            students = new IStudent[count];
            var stmt2 = Database.getConnection().prepareStatement("SELECT sid FROM staffs WHERE isInstructor IS NOT NULL");
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
        return Objects.requireNonNullElse(students, new IStudent[0]);
    }
}