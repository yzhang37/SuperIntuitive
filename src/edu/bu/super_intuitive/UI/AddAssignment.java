/**
 * @Author Hanyu Chen
 * @Description // Class for add assignment page
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.UI;
import edu.bu.super_intuitive.models.exception.OperationFailed;
import edu.bu.super_intuitive.models.grading.ICourse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class AddAssignment implements ActionListener {
    private final JFrame frame;
    private final JButton button;
    private final JPanel panel;
    private final JComboBox<String> comboBox = new JComboBox<>();
    private final JTextField textField1 = new JTextField();
    private final JTextField textField2 = new JTextField();
    private final JTextField textField3 = new JTextField();
    private final ICourse course;
    private final CourseView course_view;

    // Constructor
    public AddAssignment(ICourse courseObject, CourseView course_view) {
        this.course = courseObject;
        frame = new JFrame("Add Assignment");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        button = new JButton("Confirm");
        button.addActionListener(this);

        // Create panel and set configures
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(4, 2));

        setTextField(panel,"Assignment Name: ", textField1);
        setTextField(panel,"Assignment Score: ", textField2);
        setTextField(panel,"Assignment Weight: ", textField3);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        this.course_view = course_view;
    }

    // Set text field
    private void setTextField(JPanel jp, String str_label, JTextField curr_textField) {
        JLabel label = new JLabel(str_label);
        jp.add(label);
        jp.add(curr_textField);
    }

    // Action listener
    @Override
    public void actionPerformed(ActionEvent e) {
        String assignmentName = textField1.getText();
        int assignmentScore = Integer.parseInt(textField2.getText());
        int weight = Integer.parseInt(textField3.getText());

        try {
            course.addAssignment(assignmentName, assignmentScore, weight);
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(button, "Successfully added assignment!");

        if (course_view != null) {
            try {
                course_view.updateAssignmentDisplay();
            } catch (OperationFailed ex) {
                ex.printStackTrace();
            }
        }

        this.frame.dispose();
    }

}
