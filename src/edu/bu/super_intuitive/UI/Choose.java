/**
 * @Author Hanyu Chen
 * @Description // Class for choosing the language
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

public class Choose implements ActionListener{
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://172.20.10.3/GradingSystem";
    static final String USER = "root";
    static final String PASS = "hou10ttr";
    private final JFrame frame;
    private final JButton button;
    private final JLabel label1;
    private final JLabel label2;
    private final JPanel panel;
    private final JTextField textField1;
    private JTextField textField2;
    private final JComboBox list;
    private final ArrayList<String> courseCode = new ArrayList<String>();

    // Constructor
    public Choose(){
        frame = new JFrame("Choose Student and Course");
        button = new JButton("Confirm");
        label1 = new JLabel("Student ID: ");
        label2 = new JLabel("Course Code: ");
        panel = new JPanel();
        textField1 = new JTextField();
        textField1.setSize(100,30);
        connectToDB();
        list = new JComboBox(courseCode.toArray());

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(2, 2));
        //panel.setLayout(new FlowLayout());
        button.addActionListener(this);
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        JScrollPane sp = new JScrollPane(list);
        panel.add(sp);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // Connect to the database
    public void connectToDB(){
        Connection conn = null;
        PreparedStatement st = null;
        try {
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            st = (PreparedStatement) conn
                    .prepareStatement("select * from course");

            System.out.println("Creating statement...");

            // Execute SQL query
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                courseCode.add(rs.getString("id"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        // Close connection
        finally {
            try{
                if(st!=null)
                    st.close();
            } catch (SQLException ignored){}
            try{
                if(conn!=null)
                    conn.close();
            } catch (SQLException se){
                se.printStackTrace();
            }
        }
    }

    // Action listener
    @Override
    public void actionPerformed(ActionEvent e) {
        String studentId = textField1.getText();
        String courseCode = (String) list.getSelectedItem();
        new StudentInfo(studentId, courseCode);
    }
}
