import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CourseView2 extends JFrame {
    private final String ui_user_name = "@temp CPK";
    private final String ui_course_name = "@temp CS611: Object-Oriented Programming of Java";

    public CourseView2() {
        super();
        setTitle(String.format("Course Management: [%s]\\%s", ui_user_name, ui_course_name));
        setSize(800, 600);

        // 整个窗口是一个 BorderLayout 的容器
        this.setLayout(new BorderLayout());
        // 整个窗口的顶部设置 Hi, welcome to class_name 的标题
        var title_label = new JLabel(String.format("<html>Hi %s, welcome to %s</html>", ui_user_name, ui_course_name));
        title_label.setFont(new Font("Times New Roman", Font.BOLD, 24));
        title_label.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        this.add(title_label, BorderLayout.NORTH);
        // 整个窗口中间放置主要 Working 部件
        var center_working_panel = new JPanel();
        center_working_panel.setLayout(new BoxLayout(center_working_panel, BoxLayout.PAGE_AXIS));
        this.add(center_working_panel, BorderLayout.CENTER);
        var bottom_stub_label = new JLabel("Stub Label");
        this.add(bottom_stub_label, BorderLayout.SOUTH);

        // 添加主要的两个视图，中间需要用 Tabbed 来显示界面
        JTabbedPane course_different_view_tabs = new JTabbedPane();
        center_working_panel.add(course_different_view_tabs);

        var assignment_panel = new JPanel();
        course_different_view_tabs.addTab("Assignments", null, assignment_panel, "Does nothing");
        String[] assign_view_header = { "Name", "Weights", "Full Score", "Comment" };
        var assignment_view = new JTable(new String[][]{
                {"Assignment 1", "20%", "100", "Good"},
        }, assign_view_header);
        JScrollPane assignment_jscroll_pane = new JScrollPane(assignment_view);
        assignment_panel.add(assignment_jscroll_pane);

        // 添加学生的视图
        var student_panel = new JPanel();
        course_different_view_tabs.addTab("Students", null, student_panel, "Does nothing");
        String[] student_view_header = { "ID", "Name", "Email" };
        var student_view = new JTable(new String[][]{
                {"U12345678", "John Smith", "JSmith@bu.edu"},
        }, student_view_header);
        JScrollPane student_jscroll_pane = new JScrollPane(student_view);
        student_panel.add(student_jscroll_pane);

        JButton button_1 = new JButton("back");
        ActionListener button_listener1 = e -> {
            var frame = new CourseView();
            frame.setVisible(true);
        };
        button_1.addActionListener(button_listener1);
        this.add(button_1, BorderLayout.SOUTH);



        // TODO: this default close window action should be
        //  removed in the final structure.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
