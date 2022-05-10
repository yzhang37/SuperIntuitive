package edu.bu.super_intuitive.service.mysql.grading;
import edu.bu.super_intuitive.models.exception.OperationFailed;
import edu.bu.super_intuitive.models.grading.IMember;
import edu.bu.super_intuitive.middleware.mysql.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Member implements IMember {
    private final String buId;

    /**
     * 根据 BUId 获取一个 Member 对象。因为没有提供额外信息，调用这个函数前提是，数据库中
     * 已经存在一个 BUId 为 buId 的用户。如果不存在，则会产生 IllegalArgumentException。
     * @param buId 数据库中已有的成员的 BUId。
     * @throws IllegalArgumentException 如果数据库中不存在 buId 的用户。
     */
    public Member(String buId) throws InstantiationException {
        this.buId = buId;
        // 验证当前 id 的数据是否存在
        boolean fail = false;
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT * FROM staffs WHERE sid = ?");
            stmt.setString(1, buId);
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
    public Member(String buId, String name, String email) throws InstantiationException{
        this.buId = buId;
        // 首先先去数据库查询，是否已经有这个用户对象
        boolean alreadyExists = false;
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT * FROM staffs WHERE sid = ?");
            stmt.setString(1, buId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                alreadyExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!alreadyExists) {
            // 如果没有，则创建一个新的用户
            try {
                var stmt = Database.getConnection().prepareStatement("INSERT INTO staffs (sid, name, email) VALUES (?, ?, ?)");
                stmt.setString(1, buId);
                stmt.setString(2, name);
                stmt.setString(3, email);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new InstantiationException(
                        String.format("Failed to create a new member with BuId = \"%s\".", buId));
            }
        } else {
            // 如果已经有，则更新已有的用户
            try {
                var stmt = Database.getConnection().prepareStatement("UPDATE staffs SET name = ?, email = ? WHERE sid = ?");
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, buId);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new InstantiationException(
                        String.format("Failed to update member with BuId = \"%s\".", buId));
            }
        }
    }

    public String getBUId() {
        return buId;
    }

    @Override
    public String getName() {
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT name FROM staffs WHERE sid = ?");
            stmt.setString(1, buId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setName(String name) throws OperationFailed {
        try {
            var stmt = Database.getConnection().prepareStatement("UPDATE staffs SET name = ? WHERE sid = ?");
            stmt.setString(1, name);
            stmt.setString(2, this.buId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OperationFailed(String.format(
                    "Failed to update member's name to \"%s\" with BuId = \"%s\".%n", name, this.buId));
        }
    }

    @Override
    public String getEmail() {
        try {
            var stmt = Database.getConnection().prepareStatement("SELECT email FROM staffs WHERE sid = ?");
            stmt.setString(1, buId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setEmail(String email) throws OperationFailed{
        try {
            var stmt = Database.getConnection().prepareStatement("UPDATE staffs SET email = ? WHERE sid = ?");
            stmt.setString(1, email);
            stmt.setString(2, this.buId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OperationFailed(String.format(
                    "Failed to update member's email to \"%s\" with BuId = \"%s\".%n", email, this.buId));
        }
    }
}
