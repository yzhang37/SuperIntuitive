package edu.bu.super_intuitive.UI; /**
 * @Author Hanyu Chen
 * @Description //TODO $
 * @Date $ 05.05.2022$
 * @Param $
 * @return $
 **/

import edu.bu.super_intuitive.models.grading.ICourse;
import edu.bu.super_intuitive.service.mysql.grading.Instructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InstructorPage extends JFrame {

    private Instructor instructor = new Instructor("U00000000");

    public InstructorPage() throws InstantiationException {
        setTitle("Instructor page");    // Set window title
        setSize(650,580);    // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // Set window closeable
        setLayout(new BorderLayout());    // Set BorderLayout

        // Add panels to the window
        add(setCenterPanel(),BorderLayout.CENTER);
        add(setTopPanel(), BorderLayout.NORTH);
        add(setBottomPanel(this), BorderLayout.SOUTH);

        setVisible(true);    //设置窗口可见
    }


    private JPanel setTopPanel() {
        JPanel top_panel=new JPanel();    // Create a JPanel object

        // Configures of top panel
        top_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        top_panel.setBackground(Color.lightGray);

        // Create a JLabel object
        JLabel jl = new JLabel("Hello Christine");
        jl.setFont(new Font("Serif", Font.PLAIN, 36));
        top_panel.add(jl);    // Add label to the panel

        return top_panel;
    }

    private JPanel setCenterPanel() throws InstantiationException {
        JPanel center_panel=new JPanel();
        JPanel cards=new JPanel(new CardLayout(50, 30));

        ICourse[] allCourses = instructor.getOwnedCourses();
        for (ICourse course : allCourses) {
            JButton p1_button_1 = new JButton("<html>" + course.getAlias()
                    + " " + course.getName() + "<br>" +
                    "Semester: " + course.getSemester() + "<br>" +
                    "Instructor: " + course.getInstructor().getName() +
                    "</html>");
            p1_button_1.setPreferredSize(new Dimension(200, 100));
            p1_button_1.addActionListener(e -> {
                JFrame frame = new CourseView(course);
                frame.setVisible(true);
            });
            center_panel.add(p1_button_1);
        }
        cards.add(center_panel,"card1");
        return cards;
    }

    private JPanel setBottomPanel(JFrame curr_frame) {
        // Create a panel object
        JPanel bottom_panel = new JPanel();
        JPanel cards=new JPanel(new CardLayout(50, 30));
        ActionListener button_listener1 = e -> {
            curr_frame.dispose();
            try {
                new AddCourse();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            }
        };
        ActionListener button_listener2 = e -> {
            curr_frame.dispose();
            try {
                new DeleteCourse();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            }
        };

        // Set layout
        bottom_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20));

        // Create buttons
        JButton button_1 = new JButton(" + add");
        JButton button_2 = new JButton(" - delete");
        button_1.addActionListener(button_listener1);
        button_2.addActionListener(button_listener2);

        // Add buttons into the center panel
        bottom_panel.add(button_1);
        bottom_panel.add(button_2);
        cards.add(bottom_panel);

        return cards;
    }

//    private void jdbcTest() {
//        Connection conn = null;
//        Statement stmt = null;
//
//        try {
//            //STEP 2: Register JDBC driver
//            Class.forName(JDBC_DRIVER);
//
//            //STEP 3: Open a connection
//            System.out.println("Connecting to database...");
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//            //STEP 4: Execute a query
//            System.out.println("Creating statement...");
//            stmt = conn.createStatement();
//            String sql;
//            sql = "SELECT * FROM courseList";
//            stmt.executeUpdate("USE CS611_Final;");
//            ResultSet rs = stmt.executeQuery(sql);
//            rs.next();
//            System.out.println(rs.getString(1));
//
//        } catch (Exception se) {
//            //Handle errors for JDBC
//            se.printStackTrace();
//        }//Handle errors for Class.forName
//        finally {
//            //finally block used to close resources
//            try{
//                if(stmt!=null)
//                    stmt.close();
//            } catch (SQLException ignored){}
//            try{
//                if(conn!=null)
//                    conn.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//    }

}
