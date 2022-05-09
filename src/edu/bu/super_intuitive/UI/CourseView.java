import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CourseView extends JFrame {
    private final String ui_user_name = "@temp CPK";
    private final String ui_course_name = "@temp CS611: Object-Oriented Programming of Java";
    private JButton button1, button2, button3, button4, button5, button6;

    public CourseView() {
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

        var instruction_label = new JLabel("<html>Please select an option from the menu below:</html>");
        var oldLabelFont = instruction_label.getFont();
        instruction_label.setFont(new Font(oldLabelFont.getFontName(), Font.PLAIN, oldLabelFont.getSize()));
        instruction_label.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        var instruction_label_panel = new JPanel();
        instruction_label_panel.setLayout(new BoxLayout(instruction_label_panel, BoxLayout.LINE_AXIS));
        instruction_label_panel.add(instruction_label);
        center_working_panel.add(instruction_label_panel);

        // 功能面板，列出所有的主要功能和按钮
        var function_choose_panel = new JPanel();
        function_choose_panel.setLayout(new GridLayout(2, 3));
        function_choose_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        button1 = new JButton("View Assignments");
        button2 = new JButton("View Registered Students");
        button3 = new JButton("Compute Statistics and Grades");
        button4 = new JButton("Import");
        button5 = new JButton("Export");
        button6 = new JButton("Back");
        ActionListener button_listener1 = e -> {
            var frame = new CourseView2();
            frame.setVisible(true);
        };
        ActionListener button_listener2 = e -> {
            var frame = new CourseView2();
            frame.setVisible(true);
        };
        ActionListener button_listener3 = e -> {

        };
        ActionListener button_listener4 = e -> {
            new ImportCSV();
        };
        ActionListener button_listener5 = e -> {
            new ExportCSV();
        };
        ActionListener button_listener6 = e -> {
            this.dispose();
            new InstructorPage();
        };
        button1.addActionListener(button_listener1);
        button2.addActionListener(button_listener2);
        button3.addActionListener(button_listener3);
        button4.addActionListener(button_listener4);
        button5.addActionListener(button_listener5);
        button6.addActionListener(button_listener6);
        function_choose_panel.add(button1);
        function_choose_panel.add(button2);
        function_choose_panel.add(button3);
        function_choose_panel.add(button4);
        function_choose_panel.add(button5);
        function_choose_panel.add(button6);

        center_working_panel.add(function_choose_panel);

        // TODO: this default close window action should be
        //  removed in the final structure.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
