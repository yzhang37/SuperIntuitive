package edu.bu.super_intuitive.UI;

import edu.bu.super_intuitive.models.exception.OperationFailed;
import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.service.mysql.grading.Instructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import java.util.Arrays;
import java.util.List;

public class DeleteCourse implements ActionListener {

    private JFrame frame;
    private JButton button;
    private JPanel panel;
    private final JComboBox<String> comboBox = new JComboBox<>();
    private Instructor instructor = new Instructor("U00000000");
    private ICourse[] allCourses = instructor.getOwnedCourses();

    public DeleteCourse() throws InstantiationException {
        frame = new JFrame("Delete Course");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        button = new JButton("Confirm");
        button.addActionListener(this);

        // Create panel and set configures
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(4, 2));

        setComboBox(panel, comboBox, "Course to delete");

        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    private void setComboBox(JPanel curr_panel, JComboBox<String> comboBox, String curr_label) {
        for (ICourse course : allCourses) {
            comboBox.addItem(course.getCourseId() + "-" + course.getAlias() + " - "
                    + course.getName() + " - " + course.getSemester());
        }
        curr_panel.add(new Label(curr_label));
        curr_panel.add(comboBox);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int courseId = comboBox.getSelectedIndex();
        try {
            instructor.removeCourse(allCourses[courseId]);
        } catch (OperationFailed ex) {
            ex.printStackTrace();
        }

        this.frame.dispose();
        try {
            new InstructorPage();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        }
    }

}
