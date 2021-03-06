/**
 * @Author Hanyu Chen
 * @Description // Class for transcripts
 * @Date $ 05.05.2022$
 * @Param $
 * @return $ N/A    
 **/
package edu.bu.super_intuitive.UI;

import edu.bu.super_intuitive.models.grading.IStudent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Transcripts implements ActionListener {
    private final JFrame frame;
    private final JLabel label1;
    private final JLabel label2;
    private final JLabel label3;
    private final JPanel panel;
    private final JPanel panel2;
    private final JTextArea textArea1;
    private final JTextArea textArea2;
    private final JTextArea textArea3;
    private final JTable table;

    // Constructor
    public Transcripts(IStudent student){
        frame = new JFrame("Transcripts");
        label1 = new JLabel("Name:");
        label2 = new JLabel("BU ID:");
        label3 = new JLabel("Email:");
        panel = new JPanel();
        panel2 = new JPanel();
        textArea1 = new JTextArea(student.getName());
        textArea2 = new JTextArea(student.getBUId());
        textArea3 = new JTextArea(student.getEmail());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new FlowLayout());
        panel.add(label1);
        panel.add(textArea1);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(label2);
        panel.add(textArea2);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(label3);
        panel.add(textArea3);

        // Create the table
        String[] columnNames = {"Course ID",
                "Course Name", "Credit", "Grades"};
        Object[][] data = {
                {"CS611", "OOD", "4.0", "3.5"},
                {"CS655", "Networks", "4.0", "3.3"},
                {"Overall GPA", "", "", "3.5"}
        };
        table = new JTable(data, columnNames);
        table.setShowHorizontalLines(true);
        JScrollPane sp = new JScrollPane(table);
        panel2.add(sp);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

}
