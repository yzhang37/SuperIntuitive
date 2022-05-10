/**
 * @Author Hanyu Chen
 * @Description // Class for login page
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
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
        if(loginName.equalsIgnoreCase("CPK") && password.length() > 3){
            JOptionPane.showMessageDialog(button, "You have successfully logged in");
            try {
                new InstructorPage();
                this.frame.dispose();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(button, "Invalid login credentials");
        }
    }
}