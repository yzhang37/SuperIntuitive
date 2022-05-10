/**
 * @Author Hanyu Chen
 * @Description // Class for deleting an assignment
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.UI;

import edu.bu.super_intuitive.models.exception.OperationFailed;
import edu.bu.super_intuitive.models.grading.IAssignment;
import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.service.mysql.grading.Instructor;

import javax.swing.*;
import java.awt.*;

/**
 * @Author Hanyu Chen
 * @Description //TODO $
 * @Date $ 05.05.2022$
 * @Param $
 * @return $
 **/
public class DeleteAssignment {

    private final JFrame frame;
    private final JComboBox<String> comboBox = new JComboBox<>();
    private final Instructor instructor = new Instructor("U00000000");
    private final ICourse course = instructor.getOwnedCourses()[0];
    private final CourseView courseView;

    public DeleteAssignment(CourseView course_view) throws InstantiationException, OperationFailed {
        frame = new JFrame("Delete Assignment");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.courseView = course_view;

        JButton button = new JButton("Confirm");
        button.addActionListener(e -> {
            int assignmentId = comboBox.getSelectedIndex();
            try {
                course.removeAssignment(course.getAssignments()[assignmentId]);
                courseView.updateAssignmentDisplay();
            } catch (OperationFailed ex) {
                ex.printStackTrace();
            }
            frame.dispose();
        });

        // Create panel and set configures
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(4, 2));

        setComboBox(panel, comboBox);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void setComboBox(JPanel curr_panel, JComboBox<String> comboBox) throws OperationFailed {
        for (IAssignment assign : course.getAssignments()) {
            comboBox.addItem(assign.getName());
        }
        curr_panel.add(new Label("Assignment to delete"));
        curr_panel.add(comboBox);
    }

}
