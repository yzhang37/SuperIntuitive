import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class ExportCSV implements ActionListener {
    private JFrame frame;
    private JButton button;
    private JPanel panel;
    private JComboBox list;
    private StringBuilder sb = new StringBuilder();
    public ExportCSV() {
        frame = new JFrame("Export CSV");
        button = new JButton("Confirm");
        panel = new JPanel();

        String tableData[] = {"course", "student"};
        list = new JComboBox(tableData);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(2, 2));
        //panel.setLayout(new FlowLayout());
        button.addActionListener(this);
        JScrollPane sp = new JScrollPane(list);
        panel.add(sp);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void connectToDB(String tableName){
        Connection conn = null;
        PreparedStatement st = null;
        try {
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GradingSystem",
                    "root", "root1234");

            st = (PreparedStatement) conn
                    .prepareStatement("select * from " + tableName);
            System.out.println("Creating statement...");
            ResultSet rs = st.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int count = metaData.getColumnCount(); //number of column
            String columnName[] = new String[count];
            for(int i = 1; i <= count; i++){
                columnName[i-1] = metaData.getColumnName(i);
                sb.append(columnName[i-1]);
            }
            sb.append("\r\n");
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
        String tableName = (String) list.getSelectedItem();
        connectToDB(tableName);
        try (PrintWriter writer = new PrintWriter("test2.csv")) {
            writer.write(sb.toString());
            System.out.println("done!");
        } catch (FileNotFoundException e2) {
            System.out.println(e2.getMessage());
        }
        this.frame.dispose();
    }
}