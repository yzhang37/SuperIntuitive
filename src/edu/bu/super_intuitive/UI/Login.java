package edu.bu.super_intuitive.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Login implements ActionListener {
    private JFrame frame;
    private JButton button;
    private JLabel label1, label2;
    private JPanel panel;
    private JTextField textField1, textField2;

    public Login(){
        frame = new JFrame("Login");
        button = new JButton("Confirm");
        label1 = new JLabel("Login Name:");
        label2 = new JLabel("Password:");
        panel = new JPanel();
        textField1 = new JTextField();
        textField2 = new JTextField();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(2, 2));
        button.addActionListener(this);
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String loginName = textField1.getText();
        String password = textField2.getText();
        if(loginName.equals("CPK")){
            JOptionPane.showMessageDialog(button, "You have successfully logged in");
            frame.dispose();
            try {
                new InstructorPage();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(button, "Invalid login credentials");
        }
//        Connection conn = null;
//        PreparedStatement st = null;
//        try {
//            //Open a connection
//            System.out.println("Connecting to database...");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GradingSystem",
//                    "root", "root1234");
//
//            st = (PreparedStatement) conn
//                    .prepareStatement("Select id, password from student where id = ? and password=?");
//
//            st.setString(1, userID);
//            st.setString(2, password);
//            ResultSet rs = st.executeQuery();
//
//            //STEP 4: Execute a query
//            System.out.println("Creating statement...");
//            if (rs.next()) {
//                JOptionPane.showMessageDialog(button, "You have successfully logged in");
//                new InstructorPage();
//                frame.dispose();
//            } else {
//                JOptionPane.showMessageDialog(button, "Wrong StudentID or Password");
//            }
//        } catch (SQLException sqlException) {
//            sqlException.printStackTrace();
//        }
//        finally {
//            //finally block used to close resources
//            try{
//                if(st!=null)
//                    st.close();
//            } catch (SQLException ignored){}
//            try{
//                if(conn!=null)
//                    conn.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }//end finally try
//        }
    }
}