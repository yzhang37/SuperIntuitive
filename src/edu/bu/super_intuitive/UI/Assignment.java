import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Assignment implements ActionListener {
    private JFrame frame;
    private JLabel label1, label2, label3;
    private JPanel panel, panel2;
    private JTextArea textArea1, textArea2, textArea3, textArea4;
    private JTable table;
    private Object[][] data;
    private String courseId, courseName, credit, grade;

    public Assignment(String studentId, String courseCode){
        frame = new JFrame("Assignment Information");
        label1 = new JLabel("Name:");
        label2 = new JLabel("BU ID:");
        label3 = new JLabel("Email:");
        panel = new JPanel();
        panel2 = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new FlowLayout());
        String[] columnNames = {"Course ID", "Course Name", "Credit", "Grade"};
        connectToDB(courseCode, studentId);

        textArea1 = new JTextArea(courseId);
        textArea2 = new JTextArea(courseName);
        textArea3 = new JTextArea(credit);
        textArea4 = new JTextArea(grade);
        panel.add(label1);
        panel.add(textArea1);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(label2);
        panel.add(textArea2);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(label3);
        panel.add(textArea3);
        table = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(table);
        panel2.add(sp);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void connectToDB(String courseCode, String studentId){
        Connection conn = null;
        PreparedStatement st = null;
        try {
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://172.20.10.3/GradingSystem",
                    "root", "root1234");

            st = (PreparedStatement) conn
                    .prepareStatement("Select * from " + courseCode + ", student " +
                            "where student.id = ? and student.id = " + courseCode + ".id");
            System.out.println("Creating statement...");
            st.setString(1, studentId);
            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                data = new Object[][]{
                        {"TicTacToe", rs.getInt("TicTacToe")},
                        {"Legends", rs.getInt("Legends")},
                        {"Midterm", rs.getInt("Midterm")},
                        {"Current Grades", "80"}
                };
                courseId = rs.getString("courseId");
                courseName = rs.getString("email");
                credit = rs.getString("credit");
                grade = rs.getString("grade");
            }
        } catch (SQLException sqlException) {
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

}
