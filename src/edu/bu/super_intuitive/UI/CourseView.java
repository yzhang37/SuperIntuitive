/**
 * @Author Hanyu Chen
 * @Description // Class for the course view
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.UI;

import edu.bu.super_intuitive.models.exception.OperationFailed;
import edu.bu.super_intuitive.models.grading.IAssignment;
import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.models.grading.IInstructor;
import edu.bu.super_intuitive.models.grading.IStudent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CourseView extends JFrame {
    private final ICourse courseObject;
    private IInstructor instructorObject;

    // Local controls
    private JLabel headerLabel;
    private final JComponent[] page1Components;
    private final JComponent[] page2Components;
    private JTabbedPane page2Tabs;
    private DefaultTableModel assignment_table_model;
    private JScrollPane assignment_jScroll_panel;

    private DefaultTableModel students_table_model;
    private JScrollPane students_jScroll_panel;

    /**
     * Provide an ICourse object and create the corresponding window view based on the object.
     * @param course A Course object.
     */
    public CourseView(ICourse course) throws OperationFailed {
        super();

        // Get the basic data objects that need to be used in this window
        this.courseObject = course;
        try {
            this.instructorObject = course.getInstructor();
        } catch (InstantiationException ignored) {}

        // Setting the window components
        setSize(800, 600);
        this.setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        this.add(this.createUpperComponent(), BorderLayout.NORTH);
        JPanel workingPanel = new JPanel();
        workingPanel.setLayout(new BoxLayout(workingPanel, BoxLayout.PAGE_AXIS));
        this.add(workingPanel, BorderLayout.CENTER);
        this.page1Components = this.createPage1WorkingComponents();
        this.page2Components = this.createPage2WorkingComponents();
        // First, add all the components to the workingPanel
        for (var component: this.page1Components) {
            workingPanel.add(component);
        }
        for (var component: this.page2Components) {
            workingPanel.add(component);
        }
        this.setWorkingComponent(0);

        // Add the bottom component
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Add the back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> this.dispose());
        bottomPanel.add(backButton);

        // Set window content
        this.setTitle();

        // TODO: this default close window action should be
        //  removed in the final structure.
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Create the upper component
    private JComponent createUpperComponent() {
        var label = new JLabel();
        label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        this.headerLabel = label;
        return label;
    }

    // Create the page1 working components
    private JComponent[] createPage1WorkingComponents() {
        ArrayList<JComponent> components = new ArrayList<>();

        // Add the above message prompt text
        var instruction_label = new JLabel("<html>Please select an option from the menu below:</html>");
        var oldLabelFont = instruction_label.getFont();
        instruction_label.setFont(new Font(oldLabelFont.getFontName(), Font.PLAIN, oldLabelFont.getSize()));
        instruction_label.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        var instruction_label_panel = new JPanel();
        instruction_label_panel.setLayout(new BoxLayout(instruction_label_panel, BoxLayout.LINE_AXIS));
        instruction_label_panel.add(instruction_label);
        components.add(instruction_label_panel);

        // Function panel, listing all main functions and buttons
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

    // Set the header label
    private void setTitle() {
        var ui_user_name = this.instructorObject.getName();
        var ui_course_alias = this.courseObject.getAlias();
        var ui_course_name = this.courseObject.getName();
        this.setTitle(String.format("Course Management: [%s]\\%s", ui_user_name, ui_course_name));
        this.headerLabel.setText(String.format("<html>Hi %s, welcome to %s: %s</html>",
                ui_user_name, ui_course_alias, ui_course_name));
    }

    // Add the page1 buttons
    private JComponent[] addPage1Buttons() {
        var btnAssignmentView = new JButton("View Assignments");
        var btnStudentView = new JButton("View Registered Students");
        var btnStatistics = new JButton("Compute Statistics and Grades");
        var btnImport = new JButton("Import");
        var btnExport = new JButton("Export");

        btnAssignmentView.addActionListener(e -> {
            // Show second page
            setWorkingComponent(1);
            this.page2Tabs.setSelectedIndex(0);
        });

        btnStudentView.addActionListener(e -> {
            // Show second page
            setWorkingComponent(1);
            this.page2Tabs.setSelectedIndex(1);
        });

        btnStatistics.addActionListener(e -> new Statistics());

        btnImport.addActionListener(e -> new ImportCSV());

        btnExport.addActionListener(e -> new ExportCSV());

        return new JComponent[] {btnAssignmentView, btnStudentView, btnStatistics, btnImport, btnExport};
    }

    // Create the page2 working components
    private JComponent[] createPage2WorkingComponents() throws OperationFailed {
        ArrayList<JComponent> components = new ArrayList<>();

        // Add the main two views and use Tabbed to display the interface in between
        var page2Tabs = new JTabbedPane();
        this.page2Tabs = page2Tabs;

        components.add(page2Tabs);

        var assignment_panel = this.createPage2AssignmentView();
        page2Tabs.addTab("Assignments", null, assignment_panel, "View Assignments");
        var student_panel = this.createPage2StudentView();
        page2Tabs.addTab("Students", null, student_panel, "View Registered Students");

        var btnAdd = new JButton("Add");
        ActionListener button_listener1 = e -> {
            // assignment
            if (this.page2Tabs.getSelectedIndex() == 0) {
                try {
                    new AddAssignment(courseObject, this);
                    // Refresh the assignments view in the window
                    this.updateAssignmentDisplay();
                } catch (OperationFailed ex) {
                    throw new RuntimeException(ex);
                }
            } else if (this.page2Tabs.getSelectedIndex() == 1) {
                // student
                new AddNameToCourse(
                        new JTable(new DefaultTableModel(null, new Object[] {"Name", "ID", ""})),
                        new JTable(new DefaultTableModel(null, new Object[] {"Name", "ID", ""})),
                        new ArrayList<>(), courseObject, this);
            }
        };

        btnAdd.addActionListener(button_listener1);
        components.add(btnAdd);

        // Add delete button
        var btnDelete = new JButton("Delete");
        ActionListener button_listener2 = e -> {
            if (this.page2Tabs.getSelectedIndex() == 0) {
                try {
                    new DeleteAssignment(this);
                } catch (InstantiationException | OperationFailed ex) {
                    ex.printStackTrace();
                }
            } else if (this.page2Tabs.getSelectedIndex() == 1) {
                System.out.println("delete student");
            }
        };

        // Add action listener
        btnDelete.addActionListener(button_listener2);
        components.add(btnDelete);

        // Add back button
        var btnBackToPage1 = new JButton("Back");
        ActionListener button_listener3 = e -> this.setWorkingComponent(0);

        btnBackToPage1.addActionListener(button_listener3);
        components.add(btnBackToPage1);

        JComponent[] componentsArray = new JComponent[components.size()];
        components.toArray(componentsArray);
        return componentsArray;
    }

    // Add assignment to the course
    private JPanel createPage2AssignmentView() throws OperationFailed {
        var panel = new JPanel();
        String[] assign_view_header = { "Name", "Weights", "Full Score"};

        DefaultTableModel tableModel = new DefaultTableModel(assign_view_header, 0);
        this.assignment_table_model = tableModel;
        var assignment_view = new JTable(tableModel);
        JScrollPane assignment_jScroll_pane = new JScrollPane(assignment_view);
        this.assignment_jScroll_panel = assignment_jScroll_pane;
        panel.add(assignment_jScroll_pane);

        updateAssignmentDisplay();

        return panel;
    }

    // Update the assignment view
    public void updateAssignmentDisplay() throws OperationFailed {
        // Delete the original table content first
        this.assignment_table_model.setRowCount(0);

        IAssignment[] assignments = courseObject.getAssignments();
        for (var assignment: assignments) {
            String[] assignment_info = { assignment.getName(), String.valueOf(assignment.getWeight()), String.valueOf(assignment.getFullScore())};
            this.assignment_table_model.addRow(assignment_info);
        }
        this.assignment_jScroll_panel.revalidate();
    }

    // Create the student view
    private JPanel createPage2StudentView() throws OperationFailed {
        var panel = new JPanel();
        String[] student_view_header = { "ID", "Name", "Email" };
        DefaultTableModel tableModel = new DefaultTableModel(student_view_header, 0);
        this.students_table_model = tableModel;
        var student_view = new JTable(tableModel);
        JScrollPane student_jScroll_pane = new JScrollPane(student_view);
        this.students_jScroll_panel = student_jScroll_pane;
        panel.add(student_jScroll_pane);

        updateStudentDisplay();

        return panel;
    }

    // Update the student display
    public void updateStudentDisplay() {
        // Delete the original table content first
        this.students_table_model.setRowCount(0);

        IStudent[] students = courseObject.getRegisteredStudents();
        for(var student: students) {
            String[] student_info = {student.getBUId(), student.getName(), student.getEmail()};
            this.students_table_model.addRow(student_info);
        }
        this.students_jScroll_panel.revalidate();
    }

    /**
     * Set whether the current page is on the first or second page.
     * @param pageNum Set the current page number
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
