import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class ImportCSV {
    private JFrame frame;
    private JButton button;
    private ArrayList<String> data = new ArrayList<String>();;
    public ImportCSV() {
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
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(line);
        while (true) {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println(line);
            data.add(line);
        }

        String[] s = data.get(0).split(",");
        Connection conn = null;
        PreparedStatement st = null;
        try {
            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GradingSystem",
                    "root", "root1234");

            st = (PreparedStatement) conn
                    .prepareStatement("INSERT INTO course (id, name, instructor, semester)" +
                            " VALUES (?, ?, ?, ?)");

            System.out.println("Creating statement...");
            st.setString(1, s[0]);
            st.setString(2, s[1]);
            st.setString(3, s[2]);
            st.setString(4, s[3]);
            st.execute();

            JOptionPane.showMessageDialog(button, "Successfully added course!");

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
