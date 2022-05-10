package edu.bu.super_intuitive;
import org.junit.jupiter.api.Test;

import java.sql.*;


public class TestMysql {
    @Test
    public void jdbcTest() {
        Connection conn = null;
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://172.20.10.3:3306/", "root", "hou10ttr");

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM courseList";
            stmt.executeUpdate("USE CS611_Final;");
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            System.out.println(rs.getString(1));
        } catch (Exception se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } //Handle errors for Class.forName
        finally {
            // finally block used to close resources
            try {
                if (stmt!=null)
                    stmt.close();
            } catch (SQLException ignored){}
            try {
                if(conn!=null)
                    conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}
