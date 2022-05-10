package edu.bu.super_intuitive.middleware.mysql;
import java.sql.*;


public class Database {
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DOMAIN = "localhost";
    private static final int PORT = 3306;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root1234";

    // Create an object of Database
    private static Connection conn = null;

    // Make the constructor private so that the class will not be instantiated
    private Database() {}

    /**
     * Get the database connection. If it is Null, a RuntimeException will be thrown
     * @return Connection
     */
    public static Connection getConnection() throws RuntimeException {
        if (conn == null) {
            throw new RuntimeException("Database connection is not initialized, you should first call initConnection()");
        }
        return conn;
    }

    public static void initConnection() throws SQLException{
        try {
            if (conn == null) {
                //STEP 2: Register JDBC driver
                Class.forName(DRIVER_NAME);

                //STEP 3: Open a connection
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection(
                        String.format("jdbc:mysql://%s:%d/", DOMAIN, PORT),
                        USERNAME, PASSWORD);

                var stmt = conn.createStatement();
                stmt.executeUpdate("use GradingSystem;");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            super.finalize();
        }
    }

    static {
        try {
            initConnection();
        } catch (SQLException e) {
            System.out.println("Cannot connect to database:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}