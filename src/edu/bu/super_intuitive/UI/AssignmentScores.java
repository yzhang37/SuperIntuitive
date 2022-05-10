/**
 * @Author Hanyu Chen
 * @Description // AssignmentScores is a class that contains the scores of the assignments.
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A$
 **/
package edu.bu.super_intuitive.UI;

import edu.bu.super_intuitive.models.exception.OperationFailed;
import edu.bu.super_intuitive.models.grading.ICourse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AssignmentScores extends JFrame {

    public AssignmentScores(ICourse course) {
        super();
        String ui_user_name = "Hanyu Chen";
        String ui_course_name = "@temp CS611: Object-Oriented Programming of Java";
        setTitle(String.format("Course Management: [%s]\\%s", ui_user_name, ui_course_name));
        setSize(800, 600);

        // 整个窗口是一个 BorderLayout 的容器
        this.setLayout(new BorderLayout());
        // 整个窗口的顶部设置 Hi, welcome to class_name 的标题
        String assignment_name = "HW1";
        var title_label = new JLabel(String.format("Students' score of %s", assignment_name));
        title_label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        title_label.setBorder(BorderFactory.createEmptyBorder(20, 20, 30, 20));

        // 整个窗口中间放置主要 Working 部件
        var center_working_panel = new JPanel();
        this.add(title_label, BorderLayout.NORTH);

        // 添加主要的两个视图，中间需要用 Tabbed 来显示界面
        Object[] table_labels = {"Name", "BUId", "Email", "Score" };
        DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][]{
                                                        {"Hanyu Chen", "U88923596", "chenhy1", "90" }},
                                                        table_labels); // Create DefaultTableModel object
        JTable course_table = new JTable(defaultTableModel);
        course_table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        course_table.setRowHeight(30);
        course_table.setPreferredSize(new Dimension(600, 300));
        center_working_panel.add(course_table);


        this.add(center_working_panel, BorderLayout.CENTER);

        JButton button_1 = new JButton("back");
        ActionListener button_listener1 = e -> {
            CourseView frame = null;
            try {
                frame = new CourseView(course);
            } catch (OperationFailed ex) {
                ex.printStackTrace();
            }
            Objects.requireNonNull(frame).setVisible(true);
        };
        button_1.addActionListener(button_listener1);
        this.add(button_1, BorderLayout.SOUTH);


        // TODO: this default close window action should be
        //  removed in the final structure.
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}
