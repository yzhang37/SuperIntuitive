package edu.bu.super_intuitive.UI;
import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.service.mysql.grading.Instructor;

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
    private Instructor instructor = new Instructor("U00000000");

    public AddCourse() throws InstantiationException {
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
        String semester = (String) comboBox.getSelectedItem();

        try {
            instructor.openCourse(courseName, courseCode, semester);
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        }

        JOptionPane.showMessageDialog(button, "Successfully added course!");


        this.frame.dispose();
        try {
            new InstructorPage();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        }
    }

}
