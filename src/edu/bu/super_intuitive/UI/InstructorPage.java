package edu.bu.super_intuitive.UI; /**
 * @Author Hanyu Chen
 * @Description //TODO $
 * @Date $ 05.05.2022$
 * @Param $
 * @return $
 **/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;



public class InstructorPage extends JFrame {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://172.20.10.3/GradingSystem";

    //  Database credentials -- 数据库名和密码自己修改
    static final String USER = "root";
    static final String PASS = "hou10ttr";


    public InstructorPage()
    {
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

    private JPanel setCenterPanel() {
        JPanel center_panel=new JPanel();
        JPanel cards=new JPanel(new CardLayout(50, 30));

        // Create buttons
        Connection conn = null;
        PreparedStatement st = null;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            st = (PreparedStatement) conn
                    .prepareStatement("Select * from courses");

            ResultSet rs = st.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int count = metaData.getColumnCount(); //number of column
            ActionListener button_listener = e -> {
                new CourseView();
            };
            while(rs.next()){
                String instructorId = rs.getString("instructor");
                //String instructorName = rs.getString("instructorId");
                JButton p1_button_1 = new JButton("<html>" + rs.getString("alias")
                        + " " + rs.getString("name") + "<br>" +
                        "Semester: " + rs.getString("semester") + "<br>" +
                        "Instructor: " + rs.getString("instructor") +
                        "</html>");
                p1_button_1.setPreferredSize(new Dimension(200, 100));
                p1_button_1.addActionListener(button_listener);
                center_panel.add(p1_button_1);
            }

        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }
        finally {
            try{
                if(st!=null)
                    st.close();
            } catch (SQLException ignored){}
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
//        JButton p1_button_1 = new JButton("<html>" +
//                "CS611 Object-oriented Design <br>" +
//                "Semester: Spring<br>" +
//                "Instructor: Christine" +
//                "</html>");
//        JButton p1_button_2 = new JButton("<html>" +
//                "CS112 Intro to Computer Science II <br>" +
//                "Semester: Spring<br>" +
//                "Instructor: Christine" +
//                "</html>");
//        p1_button_1.setPreferredSize(new Dimension(250, 80));
//        p1_button_2.setPreferredSize(new Dimension(250, 80));
//        ActionListener button_listener3 = e -> {
//            var frame = new CourseView();
//            frame.setVisible(true);;
//        };
//        ActionListener button_listener4 = e -> {
//
//        };
//        p1_button_1.addActionListener(button_listener3);
//        p1_button_2.addActionListener(button_listener4);
//
//        // Add buttons into the center panel
//        center_panel.add(p1_button_1);
//        center_panel.add(p1_button_2);
        cards.add(center_panel,"card1");
        return cards;
    }

    private JPanel setBottomPanel(JFrame curr_frame) {
        // Create a panel object
        JPanel bottom_panel = new JPanel();
        JPanel cards=new JPanel(new CardLayout(50, 30));
        ActionListener button_listener1 = e -> {
            curr_frame.dispose();
            new AddCourse();
        };
        ActionListener button_listener2 = e -> {
            curr_frame.dispose();
            new DeleteCourse();
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
