package edu.bu.super_intuitive.service.mysql.grading;
import edu.bu.super_intuitive.models.grading.IMember;
import edu.bu.super_intuitive.middleware.mysql.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Member implements IMember {
    private final String buId;

    /**
     * 根据 BUId 获取一个 Member 对象。因为没有提供额外信息，调用这个函数前提是，数据库中
     * 已经存在一个 BUId 为 buId 的用户。如果不存在，则会产生 IllegalArgumentException。
     * @param buId 数据库中已有的成员的 BUId。
     * @throws IllegalArgumentException 如果数据库中不存在 buId 的用户。
     */
    public Member(String buId) throws IllegalArgumentException {
        this.buId = buId;
        // 验证当前 id 的数据是否存在
        boolean fail = false;
        try {
            var stmt = Database.getConnection().createStatement();
            var sql = String.format("SELECT * FROM staffs WHERE sid = \"%s\"", buId);
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                fail = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail = true;
        }
        if (fail) {
            throw new IllegalArgumentException(
                    String.format("Member with BuId = \"%s\" not exist.", buId));
        }
    }

    /**
     * 使用 BUId 获取/创建一个 Member 对象。如果数据库中不存在 buId 的用户，则会创建一个新的用户，使用 name, email 字段。
     * 如果数据库中已经存在 buId 的用户，则不会创建新用户，但会更新已有数值。
     * @param buId 数据库中已有的成员的 BUId。
     * @param name 成员的姓名。
     * @param email 成员的邮箱。
     */
    public Member(String buId, String name, String email) throws IllegalArgumentException{
        this.buId = buId;
        // 首先先去数据库查询，是否已经有这个用户对象
        boolean alreadyExists = false;
        try {
            var stmt = Database.getConnection().createStatement();
            var sql = String.format("SELECT * FROM staffs WHERE sid = \"%s\"", buId);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                alreadyExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!alreadyExists) {
            // 如果没有，则创建一个新的用户
            try {
                var stmt = Database.getConnection().createStatement();
                var sql = String.format("INSERT INTO staffs (sid, name, email) VALUES (\"%s\", \"%s\", \"%s\")", buId, name, email);
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(
                        String.format("Failed to create a new member with BuId = \"%s\".", buId));
            }
        } else {
            // 如果已经有，则更新已有的用户
            try {
                var stmt = Database.getConnection().createStatement();
                var sql = String.format("UPDATE staffs SET name = \"%s\", email = \"%s\" WHERE sid = \"%s\"", name, email, buId);
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(
                        String.format("Failed to update member with BuId = \"%s\".", buId));
            }
        }
    }

    public String getBUId() {
        return buId;
    }

    @Override
    public String getName() {
        return getSingleValue(String.format("SELECT name FROM staffs WHERE sid = \"%s\"", this.buId), "name");
    }

    @Override
    public void setName(String name) {
//        try {
//            var stmt = Database.getConnection().createStatement();
//            var sql = String.format("UPDATE staffs SET name = \"%s\" WHERE sid = \"%s\"", name, this.buId);
//        }
    }

    @Override
    public String getEmail() {
        return getSingleValue(String.format("SELECT email FROM staffs WHERE sid = \"%s\"", this.buId), "email");
    }

    @Override
    public void setEmail(String email) {

    }

    protected static String getSingleValue(String sql, String columnName) {
        try {
            var stmt = Database.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(columnName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
