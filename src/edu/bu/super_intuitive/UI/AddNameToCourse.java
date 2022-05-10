package edu.bu.super_intuitive.UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.*;
import javax.swing.table.*;
import java.awt.event.ActionListener;

/**
 * @Author Hanyu Chen
 * @Description //TODO $
 * @Date $ 05.05.2022$
 * @Param $
 * @return $
 **/
public class AddNameToCourse extends JFrame {

    private final JTable searching_table;
    private final JTable pending_table;

    public AddNameToCourse(JTable st, JTable pt) {
//        curr_frame = new JFrame();
        this.setTitle("Add name to course");   // Set window title
        this.setSize(620,400);    // Set window size
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // Set window closeable
        this.setLayout(new BorderLayout());
        this.searching_table = st;
        this.pending_table = pt;

        setTopPanel(this);
        setCenterPanel(this);
        setBottomPanel(this);
//        System.out.println("🔍");
        this.setVisible(true);    // Set window to be visible
    }

    private void setTopPanel(JFrame curr_frame) {
        // Create panel
        JPanel top_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel top_panel_left = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 20));
        JPanel top_panel_right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 40, 20));

        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(120, 35));
        JButton search_btn = new JButton();
        search_btn.setPreferredSize(new Dimension(80, 35));
        JLabel btn_label = new JLabel("🔍 Search");
        search_btn.add(btn_label);

        top_panel_left.add(tf);
        top_panel_right.add(search_btn);

        top_panel.add(top_panel_left);
        top_panel.add(top_panel_right);

        curr_frame.add(top_panel, BorderLayout.NORTH);

    }

    private void setCenterPanel(JFrame curr_frame) {
        // Create panels
        JPanel curr_panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));

        curr_panel.add(setCenterChildPanel("Search results", searching_table));
        curr_panel.add(setCenterChildPanel("Pending", pending_table));


        // Add panel to frame
        curr_frame.add(curr_panel, BorderLayout.CENTER);
    }

    private JPanel setCenterChildPanel(String label, JTable curr_table) {

        // Create panel
        JPanel curr_panel = new JPanel();

        // Set panels layout
        curr_panel.setLayout(new FlowLayout());
        curr_panel.setPreferredSize(new Dimension(250, 200));

        curr_panel.add(new JLabel(label));
//        JTable curr_table = new JTable(data, new String[]{"Name", "ID", ""});
        if (label.equals("Search results")) {
            curr_table.getColumn("").setCellRenderer(new ButtonRenderer());
            curr_table.getColumn("").setCellEditor(
                    new ButtonEditor(new JCheckBox(), this, pending_table));
        }
        curr_table.setPreferredSize(new Dimension(250, 150));
//        curr_list.setAlignmentX(Component.CENTER_ALIGNMENT);

        curr_panel.add(curr_table);
        return curr_panel;
    }

    private void setBottomPanel(JFrame curr_frame) {
        // Create a panel object
        JPanel bottom_panel = new JPanel();
        JPanel cards=new JPanel(new CardLayout(50, 30));
        ActionListener button_listener1 = e -> {
            curr_frame.dispose();
            new AddCourse();
        };

        // Set layout
        bottom_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20));

        // Create buttons
        JButton button_1 = new JButton("Confirm");
        JButton button_2 = new JButton("Cancel");
        button_1.addActionListener(button_listener1);

        // Add buttons into the center panel
        bottom_panel.add(button_1);
        bottom_panel.add(button_2);
        cards.add(bottom_panel);

        curr_frame.add(bottom_panel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        List<Object[]> searching_res_test = new ArrayList<>();
        List<Object[]> pending_res_test = new ArrayList<>();
        DefaultTableModel tmp = new DefaultTableModel(new Object[]{"Name", "ID", ""}, 0);
//        Object[][] tmp = new Object[][] {{"", "", ""}};
        for (int i = 0; i < 3; i++) {
            tmp.addRow(new Object[]{"U88923596", "Hanyu Chen", " + Add"});
        }
        new AddNameToCourse(new JTable(tmp),
                new JTable(new DefaultTableModel(new Object[]{"Name", "ID"}, 0)));
    }

}

/**
 * @version 1.0 11/09/98
 */

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
//        if (isSelected) {
//            setForeground(table.getSelectionForeground());
//            setBackground(table.getSelectionBackground());
//        } else {
//            setForeground(table.getForeground());
//            setBackground(UIManager.getColor("Button.background"));
//        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

/**
 * @version 1.0 11/09/98
 */

class ButtonEditor extends DefaultCellEditor {
    protected JButton button;

    private String label;
    private final JFrame curr_frame;
    private final JTable pending_table;
    private JTable searching_table;
    private int deleted_row;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox, JFrame frame, JTable pending_table) {
        super(checkBox);
        curr_frame = frame;
        this.pending_table = pending_table;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        searching_table = table;
        deleted_row = row;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            DefaultTableModel model = (DefaultTableModel) pending_table.getModel();
            Object[] added_row = Arrays.copyOfRange(getRowAt(deleted_row, searching_table), 0, 2);
            model.addRow(added_row);
            DefaultTableModel search_model = (DefaultTableModel)searching_table.getModel();
            search_model.removeRow(deleted_row);
//            search_model.setRowCount(search_model.getColumnCount()+1);


            curr_frame.dispose();
            new AddNameToCourse(searching_table, pending_table);
            // System.out.println(label + ": Ouch!");
        }
        isPushed = false;
        return new String(label);
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    private Object[] getRowAt(int row, JTable table) {
        Object[] result = new String[3];

        for (int i = 0; i < 3; i++) {
            result[i] = table.getModel().getValueAt(row, i);
        }

        return result;
    }
}
