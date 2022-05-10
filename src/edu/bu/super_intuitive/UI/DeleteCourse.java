/**
 * @Author Hanyu Chen
 * @Description // Course delete page
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.UI;

import edu.bu.super_intuitive.models.exception.OperationFailed;
import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.service.mysql.grading.Instructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeleteCourse implements ActionListener {

    private final JFrame frame;
    private final JButton button;
    private final JPanel panel;
    private final JComboBox<String> comboBox = new JComboBox<>();
    private final Instructor instructor = new Instructor("U00000000");
    private final ICourse[] allCourses = instructor.getOwnedCourses();

    // Constructor
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

    // Set combo box
    private void setComboBox(JPanel curr_panel, JComboBox<String> comboBox, String curr_label) {
        for (ICourse course : allCourses) {
            comboBox.addItem(course.getCourseId() + "-" + course.getAlias() + " - "
                    + course.getName() + " - " + course.getSemester());
        }
        curr_panel.add(new Label(curr_label));
        curr_panel.add(comboBox);
    }

    // Action listener
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
