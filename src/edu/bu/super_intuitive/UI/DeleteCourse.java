import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import java.util.Arrays;
import java.util.List;

public class DeleteCourse implements ActionListener {

    private JFrame frame;
    private JButton button;
    private JPanel panel;
    private final JComboBox<String> comboBox = new JComboBox<>();

    public DeleteCourse() {
        frame = new JFrame("Delete Course");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button = new JButton("Confirm");
        button.addActionListener(this);

        String[] courseList = {"CS611", "CS655"};

        // Create panel and set configures
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(4, 2));

        setComboBox(panel, Arrays.stream(courseList).toList(), comboBox, "CS611");

        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    private void setComboBox(JPanel curr_panel, List<String> str_labels, JComboBox<String> comboBox, String curr_label) {
        for (String s : str_labels) {
            comboBox.addItem(s);
        }
        curr_panel.add(new Label(curr_label));
        curr_panel.add(comboBox);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String courseId = (String) comboBox.getSelectedItem();

        Connection conn = null;
        PreparedStatement st = null;

        try {
            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GradingSystem",
                    "root", "root1234");

            st = (PreparedStatement) conn
                    .prepareStatement("Delete from course where id = ?");

            System.out.println("Creating statement...");
            st.setString(1, courseId);
            st.execute();

            JOptionPane.showMessageDialog(button, "Successfully deleted course!");

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

        this.frame.dispose();
        new InstructorPage();
    }

}
