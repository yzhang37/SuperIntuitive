package edu.bu.super_intuitive.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class ImportCSV implements ActionListener{
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/GradingSystem";
    static final String USER = "root";
    static final String PASS = "yzh373df";
    private JFrame frame;
    private JButton button;
    private JPanel panel;
    private JLabel label;
    private JComboBox list;
    private ArrayList<String> data = new ArrayList<String>();
    public ImportCSV() {
        frame = new JFrame("Import CSV");
        button = new JButton("Browse");
        panel = new JPanel();
        label = new JLabel("Import to table: ");
        String tables[] = {"courses", "staffs", "assignments"};
        list = new JComboBox(tables);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(1, 2));
        //panel.setLayout(new FlowLayout());
        button.addActionListener((ActionListener) this);
        JScrollPane sp = new JScrollPane(list);
        panel.add(label);
        panel.add(sp);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String tableName = (String) list.getSelectedItem();
        String filePath = "";
        JFileChooser j = new JFileChooser(System.getProperty("user.dir"));
        j.setMultiSelectionEnabled(true);
        j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        j.setFileHidingEnabled(false);
        if (j.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            java.io.File f = j.getSelectedFile();
            filePath = f.getPath();
        }
        // String filePath = System.getProperty("user.dir") + "/test.csv";
        File file = new File(filePath);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        String column = null;
        try {
            column = br.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //System.out.println(column);
        String line = null;
        while (true) {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //System.out.println(line);
            data.add(line);
        }

        String[] s = null;
        Connection conn = null;
        PreparedStatement st = null;
        try {
            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            switch (tableName){
                case("courses"): {
                    for(int i = 0; i < data.size(); i++) {
                        s = data.get(i).split(",");
                        st = (PreparedStatement) conn
                                .prepareStatement("INSERT INTO " + tableName + "(" + column + ")" +
                                        " VALUES (?, ?, ?, ?, ?)");
                        st.setString(1, s[0]);
                        st.setString(2, s[1]);
                        st.setString(3, s[2]);
                        st.setString(4, s[3]);
                        st.setString(5, s[4]);
                        st.execute();
                    }
                    JOptionPane.showMessageDialog(button, "Successfully imported to course!");
                    break;
                }
                case("staffs"): {
                    for(int i = 0; i < data.size(); i++) {
                        s = data.get(i).split(",");
                        st = (PreparedStatement) conn
                                .prepareStatement("INSERT INTO " + tableName + "(" + column + ")" +
                                        " VALUES (?, ?, ?, ?)");

                        st.setString(1, s[0]);
                        st.setString(2, s[1]);
                        st.setString(3, s[2]);
                        st.setString(4, s[3]);
                        st.execute();
                    }
                    JOptionPane.showMessageDialog(button, "Successfully imported to staffs!");
                    break;
                }
                case("assignments"): {
                    for(int i = 0; i < data.size(); i++) {
                        s = data.get(i).split(",");
                        st = (PreparedStatement) conn
                                .prepareStatement("INSERT INTO " + tableName + "(" + column + ")" +
                                        " VALUES (?, ?, ?, ?, ?)");

                        st.setString(1, s[0]);
                        st.setString(2, s[1]);
                        st.setString(3, s[2]);
                        st.setString(4, s[3]);
                        st.setString(5, s[4]);
                        st.execute();
                    }
                    JOptionPane.showMessageDialog(button, "Successfully imported to assignments!");
                    break;
                }
            }
            this.frame.dispose();

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
        };
    }
}
