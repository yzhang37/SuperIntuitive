package edu.bu.super_intuitive.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Login implements ActionListener {
    private final JFrame frame;
    private final JButton button;
    private final JLabel labelUserName;
    private final JLabel labelPassword;
    private final JPanel panel;
    private final JTextField textFieldUserName;
    private final JTextField textFieldPassword;

    public Login(){
        frame = new JFrame("Login");
        button = new JButton("Confirm");

        labelUserName = new JLabel("Login Name:");
        textFieldUserName = new JTextField();
        labelUserName.setLabelFor(textFieldUserName);

        labelPassword = new JLabel("Password:");
        textFieldPassword = new JPasswordField();
        labelPassword.setLabelFor(textFieldPassword);

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(2, 2));
        button.addActionListener(this);
        panel.add(labelUserName);
        panel.add(textFieldUserName);
        panel.add(labelPassword);
        panel.add(textFieldPassword);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // 窗口居中显示
        frame.setLocationRelativeTo(null);
        // 禁用缩放
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String loginName = textFieldUserName.getText();
        String password = textFieldPassword.getText();
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