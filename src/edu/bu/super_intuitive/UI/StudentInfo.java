/**
 * @Author Hanyu Chen
 * @Description // Class for student information
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentInfo implements ActionListener {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://172.20.10.3/GradingSystem";
    static final String USER = "root";
    static final String PASS = "hou10ttr";
    private final JFrame frame;
    private final JLabel label1;
    private final JLabel label2;
    private final JLabel label3;
    private final JPanel panel;
    private final JPanel panel2;
    private final JTextArea textArea1;
    private final JTextArea textArea2;
    private final JTextArea textArea3;
    private final JTable table;
    private Object[][] data;
    private String name, email;

    // Constructor
    public StudentInfo(String studentId, String courseCode){
        frame = new JFrame("Student Information");
        label1 = new JLabel("Name:");
        label2 = new JLabel("BU ID:");
        label3 = new JLabel("Email:");
        panel = new JPanel();
        panel2 = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new FlowLayout());
        String[] columnNames = {"Assignments", "Grades"};

        textArea1 = new JTextArea(name);
        textArea2 = new JTextArea(studentId);
        textArea3 = new JTextArea(email);
        panel.add(label1);
        panel.add(textArea1);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(label2);
        panel.add(textArea2);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(label3);
        panel.add(textArea3);
        table = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(table);
        panel2.add(sp);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // Connect to database
    public void connectToDB(String courseCode, String studentId){
        Connection conn = null;
        PreparedStatement st = null;
        try {
            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            st = (PreparedStatement) conn
                    .prepareStatement("Select * from " + courseCode + ", student " +
                            "where student.id = ? and student.id = " + courseCode + ".id");
            System.out.println("Creating statement...");
            st.setString(1, studentId);
            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                data = new Object[][]{
                        {"TicTacToe", rs.getInt("TicTacToe")},
                        {"Legends", rs.getInt("Legends")},
                        {"Midterm", rs.getInt("Midterm")},
                        {"Current Grades", "80"}
                };
                name = rs.getString("name");
                email = rs.getString("email");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        // Close the connection
        finally {
            try{
                if(st!=null)
                    st.close();
            } catch (SQLException ignored){}
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    // Add action listeners
    @Override
    public void actionPerformed(ActionEvent e) {
    }

}
