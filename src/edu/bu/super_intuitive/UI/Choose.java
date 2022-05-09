import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

public class Choose implements ActionListener{
    private JFrame frame;
    private JButton button;
    private JLabel label1, label2;
    private JPanel panel;
    private JTextField textField1, textField2;
    private JComboBox list;
//    private String courseCode[];
    private ArrayList<String> courseCode = new ArrayList<String>();

    public Choose(){
        frame = new JFrame("Choose Student and Course");
        button = new JButton("Confirm");
        label1 = new JLabel("Student ID: ");
        label2 = new JLabel("Course Code: ");
        panel = new JPanel();
        textField1 = new JTextField();
        textField1.setSize(100,30);
        connectToDB();
        list = new JComboBox(courseCode.toArray());

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(2, 2));
        //panel.setLayout(new FlowLayout());
        button.addActionListener(this);
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        JScrollPane sp = new JScrollPane(list);
        panel.add(sp);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void connectToDB(){
        Connection conn = null;
        PreparedStatement st = null;
        try {
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GradingSystem",
                    "root", "root1234");

            st = (PreparedStatement) conn
                    .prepareStatement("select * from course");

            System.out.println("Creating statement...");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                courseCode.add(rs.getString("id"));
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
            } catch (SQLException se){
                se.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String studentId = textField1.getText();
        String courseCode = (String) list.getSelectedItem();
        new StudentInfo(studentId, courseCode);
    }
}
