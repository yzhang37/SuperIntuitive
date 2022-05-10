package edu.bu.super_intuitive.factory.grading;

import edu.bu.super_intuitive.middleware.mysql.Database;
import edu.bu.super_intuitive.models.grading.IStudent;
import edu.bu.super_intuitive.service.mysql.grading.Student;

import java.sql.SQLException;
import java.util.ArrayList;


public class Students {
    /**
     * 获取全局的所有学生对象
     * @return 学生对象集合, A list of Student 对象
     */
    public static IStudent[] getAllStudents() {
        ArrayList<IStudent> students = new ArrayList<>();
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT sid FROM staffs WHERE isInstructor <> TRUE");
            var rs = stmt.executeQuery();
            while (rs.next()) {
                try {
                    students.add(new Student(rs.getString("sid")));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        IStudent[] studentsArray = new IStudent[students.size()];
        students.toArray(studentsArray);
        return studentsArray;
    }

    /**
     * 使用学生的用户名进行模糊搜索，显示学生对象。
     * @param name 部分学生名
     * @return 返回所有满足匹配的学生对象。
     */
    public static IStudent[] getStudentsByFuzzySearchName(String name) {
        ArrayList<IStudent> students = new ArrayList<>();
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT sid FROM staffs WHERE isInstructor <> TRUE AND name LIKE ?");
            stmt.setString(1, "%" + name + "%");
            var rs = stmt.executeQuery();
            while (rs.next()) {
                try {
                    students.add(new Student(rs.getString("sid")));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        IStudent[] studentsArray = new IStudent[students.size()];
        students.toArray(studentsArray);
        return studentsArray;
    }
}