package edu.bu.super_intuitive.UI;

import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.models.grading.IInstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CourseView extends JFrame {
    private final ICourse courseObject;
    private IInstructor instructorObject;

    // 本地的控件
    private JLabel headerLabel;
    private final JComponent[] page1Components;
    private final JComponent[] page2Components;
    private JTabbedPane page2Tabs;

    /**
     * 提供一个 ICourse 对象，根据对象创建对应的窗口视图。
     * @param course 一个 Course 对象。
     */
    public CourseView(ICourse course) {
        super();

        // 获取本窗口需要使用的基本数据对象
        this.courseObject = course;
        try {
            this.instructorObject = course.getInstructor();
        } catch (InstantiationException e) {}

        // 设置窗口元件
        setSize(800, 600);
        this.setLayout(new BorderLayout());

        this.add(this.createUpperComponent(), BorderLayout.NORTH);
        JPanel workingPanel = new JPanel();
        workingPanel.setLayout(new BoxLayout(workingPanel, BoxLayout.PAGE_AXIS));
        this.add(workingPanel, BorderLayout.CENTER);
        this.page1Components = this.createPage1WorkingComponents();
        this.page2Components = this.createPage2WorkingComponents();
        // 先把元件全斌说添加到 workingPanel 中
        for (var component: this.page1Components) {
            workingPanel.add(component);
        }
        for (var component: this.page2Components) {
            workingPanel.add(component);
        }
        this.setWorkingComponent(0);

        this.add(this.createLowerComponent(), BorderLayout.SOUTH);

        // 设置窗口内容
        this.setTitle();

        // TODO: this default close window action should be
        //  removed in the final structure.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JComponent createUpperComponent() {
        var label = new JLabel();
        label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        this.headerLabel = label;
        return label;
    }

    private JComponent[] createPage1WorkingComponents() {
        ArrayList<JComponent> components = new ArrayList<>();

        // 添加上面的信息提示文本
        var instruction_label = new JLabel("<html>Please select an option from the menu below:</html>");
        var oldLabelFont = instruction_label.getFont();
        instruction_label.setFont(new Font(oldLabelFont.getFontName(), Font.PLAIN, oldLabelFont.getSize()));
        instruction_label.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        var instruction_label_panel = new JPanel();
        instruction_label_panel.setLayout(new BoxLayout(instruction_label_panel, BoxLayout.LINE_AXIS));
        instruction_label_panel.add(instruction_label);
        components.add(instruction_label_panel);

        // 功能面板，列出所有的主要功能和按钮
        var function_choose_panel = new JPanel();
        function_choose_panel.setLayout(new GridLayout(2, 3));
        function_choose_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        for (var button: this.addPage1Buttons()) {
            function_choose_panel.add(button);
        }
        components.add(function_choose_panel);

        JComponent[] componentsArray = new JComponent[components.size()];
        components.toArray(componentsArray);
        return componentsArray;
    }

    private JComponent createLowerComponent() {
        // TODO: 我希望把这个可以做成一个状态栏，但是现在只是简单弄了一个 JLabel
        return new JLabel("Stub Label");
    }

    private void setTitle() {
        var ui_user_name = this.instructorObject.getName();
        var ui_course_alias = this.courseObject.getAlias();
        var ui_course_name = this.courseObject.getName();
        this.setTitle(String.format("Course Management: [%s]\\%s", ui_user_name, ui_course_name));
        this.headerLabel.setText(String.format("<html>Hi %s, welcome to %s: %s</html>",
                ui_user_name, ui_course_alias, ui_course_name));
    }

    private JComponent[] addPage1Buttons() {
        var btnAssignmentView = new JButton("View Assignments");
        var btnStudentView = new JButton("View Registered Students");
        var btnStatistics = new JButton("Compute Statistics and Grades");
        var btnImport = new JButton("Import");
        var btnExport = new JButton("Export");
        var btnBack = new JButton("Back");

        btnAssignmentView.addActionListener(e -> {
            // 显示第二页
            setWorkingComponent(1);
            this.page2Tabs.setSelectedIndex(0);
        });

        btnStudentView.addActionListener(e -> {
            // 显示第二页
            setWorkingComponent(1);
            this.page2Tabs.setSelectedIndex(1);
        });

        btnStatistics.addActionListener(e -> {
            new Statistics();
        });

        btnImport.addActionListener(e -> {
            new ImportCSV();
        });

        btnExport.addActionListener(e -> {
            new ExportCSV();
        });

        btnBack.addActionListener(e -> {
            this.dispose();
        });

        return new JComponent[] {btnAssignmentView, btnStudentView, btnStatistics, btnImport, btnExport, btnBack};
    }

    private JComponent[] createPage2WorkingComponents() {
        ArrayList<JComponent> components = new ArrayList<>();

        // 添加主要的两个视图，中间需要用 Tabbed 来显示界面
        var page2Tabs = new JTabbedPane();
        this.page2Tabs = page2Tabs;

        components.add(page2Tabs);

        var assignment_panel = this.createPage2AssignmentView();
        page2Tabs.addTab("Assignments", null, assignment_panel, "View Assignments");
        var student_panel = this.createPage2StudentView();
        page2Tabs.addTab("Students", null, student_panel, "View Registered Students");

        var btnBackToPage1 = new JButton("Back");
        ActionListener button_listener1 = e -> {
            // 返回到第一页
            this.setWorkingComponent(0);
        };

        btnBackToPage1.addActionListener(button_listener1);
        components.add(btnBackToPage1);

        JComponent[] componentsArray = new JComponent[components.size()];
        components.toArray(componentsArray);
        return componentsArray;
    }

    private JPanel createPage2AssignmentView() {
        var panel = new JPanel();
        String[] assign_view_header = { "Name", "Weights", "Full Score", "Comment" };
        var assignment_view = new JTable(new String[][]{
                {"Assignment 1", "20%", "100", "Good"},
        }, assign_view_header);
        JScrollPane assignment_jscroll_pane = new JScrollPane(assignment_view);
        panel.add(assignment_jscroll_pane);
        return panel;
    }

    private JPanel createPage2StudentView() {
        var panel = new JPanel();
        String[] student_view_header = { "ID", "Name", "Email" };
        var student_view = new JTable(new String[][]{
                {"U12345678", "John Smith", "JSmith@bu.edu"},
        }, student_view_header);
        JScrollPane student_jscroll_pane = new JScrollPane(student_view);
        panel.add(student_jscroll_pane);
        return panel;
    }

    /**
     * 设置当前页面是在第一页还是第二页。
     * @param pageNum 设置当前的页码
     */
    private void setWorkingComponent(int pageNum) {
        if (pageNum == 0) {
            for (var component: this.page1Components) {
                component.setVisible(true);
            }
            for (var component: this.page2Components) {
                component.setVisible(false);
            }
        } else {
            for (var component: this.page1Components) {
                component.setVisible(false);
            }
            for (var component: this.page2Components) {
                component.setVisible(true);
            }
        }
    }
}
