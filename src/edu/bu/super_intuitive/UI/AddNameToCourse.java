package edu.bu.super_intuitive.UI;

import edu.bu.super_intuitive.factory.grading.Students;
import edu.bu.super_intuitive.models.exception.OperationFailed;
import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.models.grading.IStudent;
import edu.bu.super_intuitive.service.mysql.grading.Student;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.awt.event.*;
import javax.swing.table.*;

/**
 * @Author Hanyu Chen
 * @Description //TODO $
 * @Date $ 05.05.2022$
 * @Param $
 * @return $
 **/
public class AddNameToCourse extends JFrame {

    private final JTable searching_table;
    private final JTable pending_table;
    private final List<String> added_students;
    private final ICourse course;
    private boolean isConfirmed;
    private final CourseView course_view;

    public AddNameToCourse(JTable st,
                           JTable pt,
                           List<String> added_students,
                           ICourse course,
                           CourseView course_view) {
//        curr_frame = new JFrame();
        this.setTitle("Add name to course");   // Set window title
        this.setSize(620,400);    // Set window size
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    // Set window closeable
        this.setLayout(new BorderLayout());
        this.searching_table = st;
        this.pending_table = pt;
        this.course = course;
        this.added_students = added_students;
        this.course_view = course_view;

        setTopPanel(this);
        setCenterPanel(this);
        setBottomPanel(this);

        this.setVisible(true);    // Set window to be visible

        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
            if (isConfirmed) {
                for (String student : added_students) {
                    try {
                        course.registerStudent(new Student(student));
                    } catch (OperationFailed | InstantiationException ex) {
                        ex.printStackTrace();
                    }
                }
                if (course_view != null) {
                    course_view.updateStudentDisplay();
                }
            }
            }
        });
    }

    private void setTopPanel(JFrame curr_frame) {
        // Create panel
        JPanel top_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel top_panel_left = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 20));
        JPanel top_panel_right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 40, 20));

        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(120, 35));
        JButton search_btn = new JButton();
        search_btn.setPreferredSize(new Dimension(80, 35));
        JLabel btn_label = new JLabel("🔍 Search");
        search_btn.addActionListener(e -> {

            String search_text = tf.getText();
            IStudent[] students = Students.getStudentsByFuzzySearchName(search_text);
            DefaultTableModel model = (DefaultTableModel) searching_table.getModel();
            model.setRowCount(0);
            for (IStudent student : students) {
                String name = student.getName();
                String id = student.getBUId();
                model.addRow(new String[]{name, id, " + Add"});
            }
        });
        search_btn.add(btn_label);

        top_panel_left.add(tf);
        top_panel_right.add(search_btn);

        top_panel.add(top_panel_left);
        top_panel.add(top_panel_right);

        curr_frame.add(top_panel, BorderLayout.NORTH);

    }

    private void setCenterPanel(JFrame curr_frame) {
        // Create panels
        JPanel curr_panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));

        curr_panel.add(setCenterChildPanel("Search results", searching_table));
        curr_panel.add(setCenterChildPanel("Pending", pending_table));


        // Add panel to frame
        curr_frame.add(curr_panel, BorderLayout.CENTER);
    }

    private JPanel setCenterChildPanel(String label, JTable curr_table) {

        // Create panel
        JPanel curr_panel = new JPanel();

        // Set panels layout
        curr_panel.setLayout(new FlowLayout());
        curr_panel.setPreferredSize(new Dimension(250, 200));

        curr_panel.add(new JLabel(label));
        if (label.equals("Search results")) {
            curr_table.getColumn("").setCellRenderer(new ButtonRenderer());
            curr_table.getColumn("").setCellEditor(
                    new ButtonEditor(new JCheckBox(), this, pending_table, added_students, course, this.course_view));
        }
        curr_table.setPreferredSize(new Dimension(250, 150));

        curr_panel.add(curr_table);
        return curr_panel;
    }

    private void setBottomPanel(JFrame curr_frame) {
        // Create a panel object
        JPanel bottom_panel = new JPanel();
        JPanel cards=new JPanel(new CardLayout(50, 30));

        // Set layout
        bottom_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20));

        // Create buttons
        JButton button_1 = new JButton("Confirm");
        JButton button_2 = new JButton("Cancel");
        button_1.addActionListener(e -> {
            isConfirmed = true;
            curr_frame.dispose();
        });
        button_2.addActionListener(e -> {
            this.dispose();
        });
        // Add buttons into the center panel
        bottom_panel.add(button_1);
        bottom_panel.add(button_2);
        cards.add(bottom_panel);

        curr_frame.add(bottom_panel, BorderLayout.SOUTH);
    }

}

/**
 * @version 1.0 11/09/98
 */

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

/**
 * @version 1.0 11/09/98
 */

class ButtonEditor extends DefaultCellEditor {
    protected final JButton button;

    private String label;
    private final JFrame curr_frame;
    private final JTable pending_table;
    private final List<String> added_students;
    private final ICourse course;
    private JTable searching_table;
    private int deleted_row;
    private boolean isPushed;
    private final CourseView course_view;

    public ButtonEditor(JCheckBox checkBox,
                        JFrame frame,
                        JTable pending_table,
                        List<String> added_students,
                        ICourse course,
                        CourseView course_view) {
        super(checkBox);
        curr_frame = frame;
        this.pending_table = pending_table;
        this.added_students = added_students;
        this.course = course;
        this.course_view = course_view;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        searching_table = table;
        deleted_row = row;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            DefaultTableModel model = (DefaultTableModel) pending_table.getModel();
            Object[] added_row = Arrays.copyOfRange(getRowAt(deleted_row, searching_table), 0, 2);
            added_students.add(added_row[1].toString());
            model.addRow(added_row);
            DefaultTableModel search_model = (DefaultTableModel) searching_table.getModel();
            search_model.removeRow(deleted_row);

            curr_frame.dispose();
            new AddNameToCourse(searching_table, pending_table, added_students, course, this.course_view);
        }
        isPushed = false;
        return label;
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    private Object[] getRowAt(int row, JTable table) {
        Object[] result = new String[3];

        for (int i = 0; i < 3; i++) {
            result[i] = table.getModel().getValueAt(row, i);
        }

        return result;
    }
}
