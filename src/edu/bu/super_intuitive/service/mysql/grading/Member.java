/**
 * @Author Zhenghang Yin
 * @Description // Class for member
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.service.mysql.grading;
import edu.bu.super_intuitive.models.exception.OperationFailed;
import edu.bu.super_intuitive.models.grading.IMember;
import edu.bu.super_intuitive.middleware.mysql.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Member implements IMember {
    private final String buId;

    /**
     * Gets a Member object based on BUId. Since no additional information is
     * provided, calling this function assumes that a user with a BUId of buId
     * already exists in the database. If it does not exist, an IllegalArgumentException
     * is thrown.
     * @param buId BUId of the members already in the database.
     * @throws IllegalArgumentException If the user with buId does not exist in the database.
     */
    public Member(String buId) throws InstantiationException {
        this.buId = buId;
        // Verify that the data for the current id exists
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
     * Use BUId to get/create a Member object. If the user with buId does not exist in the database,
     * a new user will be created, using the name, email fields. If the user with buId already exists
     * in the database, no new user will be created, but the existing values will be updated.
     *
     * @param buId BUId of the members already in the database.
     * @param name The name of the member.
     * @param email Members' emails.
     */
    public Member(String buId, String name, String email) throws InstantiationException{
        this.buId = buId;
        // First go to the database and check if the user object already exists
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
            // If not, create a new user
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
            // Update existing users if they already exist
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
