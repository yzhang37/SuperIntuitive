package edu.bu.super_intuitive.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import java.util.Arrays;
import java.util.List;

public class AddCourse implements ActionListener {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://172.20.10.3/GradingSystem";
    static final String USER = "root";
    static final String PASS = "hou10ttr";
    private JFrame frame;
    private JButton button;
    private JPanel panel;
    private final JComboBox<String> comboBox = new JComboBox<>();
    private final JTextField textField1 = new JTextField();
    private final JTextField textField2 = new JTextField();
    private final JTextField textField3 = new JTextField();

    public AddCourse() {
        frame = new JFrame("Add Course");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button = new JButton("Confirm");
        button.addActionListener(this);

        String[] semester = {"Spring 2022", "Fall 2022"};

        // Create panel and set configures
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(4, 2));

        setTextField(panel,"Course Code: ", textField1);
        setTextField(panel,"Course Name: ", textField2);
        setTextField(panel,"Instructor: ", textField3);
        setComboBox(panel, Arrays.stream(semester).toList(), comboBox, "Semester: ");

        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    private void setComboBox(JPanel curr_panel, List<String> str_labels, JComboBox<String> comboBox, String curr_label) {
        for (String s : str_labels) {
            comboBox.addItem(s);
        }
        curr_panel.add(new Label(curr_label));
        curr_panel.add(comboBox);
    }

    private void setTextField(JPanel jp, String str_label, JTextField curr_textField) {
        JLabel label = new JLabel(str_label);
        jp.add(label);
        jp.add(curr_textField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String courseCode = textField1.getText();
        String courseName = textField2.getText();
        String instructor = textField3.getText();
        String semester = (String) comboBox.getSelectedItem();

        Connection conn = null;
        PreparedStatement st = null;

        try {
            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            st = (PreparedStatement) conn
                    .prepareStatement("INSERT INTO courses (cid, name, instructor, semester)" +
                            " VALUES (?, ?, ?, ?)");

            System.out.println("Creating statement...");
            st.setString(1, courseCode);
            st.setString(2, courseName);
            st.setString(3, instructor);
            st.setString(4, semester);
            st.execute();

            JOptionPane.showMessageDialog(button, "Successfully added course!");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
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
        this.frame.dispose();
        new InstructorPage();
    }

}
