/**
 * @Author Chenyu Cao
 * @Description // CSV file export
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A
 **/
package edu.bu.super_intuitive.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;

public class ExportCSV implements ActionListener {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/GradingSystem";
    static final String USER = "root";
    static final String PASS = "yzh373df";
    private final JFrame frame;
    private final JButton button;
    private final JPanel panel;
    private final JComboBox list;
    private final StringBuilder sb = new StringBuilder();

    // Constructor
    public ExportCSV() {
        frame = new JFrame("Export CSV");
        button = new JButton("Confirm");
        panel = new JPanel();

        String[] tables = {"courses", "staffs", "assignments"};
        list = new JComboBox(tables);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(2, 2));
        //panel.setLayout(new FlowLayout());
        button.addActionListener(this);
        JScrollPane sp = new JScrollPane(list);
        panel.add(sp);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // Connect to database
    public void connectToDB(String tableName){
        Connection conn = null;
        PreparedStatement st = null;
        try {
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            st = (PreparedStatement) conn
                    .prepareStatement("select * from " + tableName);
            System.out.println("Creating statement...");

            // Get the result set
            ResultSet rs = st.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int count = metaData.getColumnCount(); //number of column
            String[] columnName = new String[count];
            for(int i = 1; i <= count; i++){
                columnName[i-1] = metaData.getColumnName(i);
                sb.append(columnName[i-1]);
                sb.append(",");
            }
            sb.append("\r\n");

            // Get the data
            while(rs.next()){
                for(int i = 1; i <= count; i++){
                    sb.append(rs.getString(columnName[i-1]));
                    sb.append(",");
                }
                sb.append("\r\n");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        // Close the connection
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

    // Listen to the button event
    @Override
    public void actionPerformed(ActionEvent e) {
        String tableName = (String) list.getSelectedItem();
        connectToDB(tableName);
        try (PrintWriter writer = new PrintWriter(tableName + ".csv")) {
            writer.write(sb.toString());
            System.out.println("done!");
        } catch (FileNotFoundException e2) {
            System.out.println(e2.getMessage());
        }
        this.frame.dispose();
    }
}