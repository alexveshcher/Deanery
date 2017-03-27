package ui;

import dao.MySQLDAO;
import vo.Department;
import vo.Exam;
import vo.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class DepartmentExamsFrame extends JFrame{
    DefaultTableModel model = new DefaultTableModel();

    public static JTable table;
    public static JScrollPane scrollPane;
    JComboBox departmentComboBox;

    private static final String NOT_SELECTABLE_OPTION = " - ... - ";

    public DepartmentExamsFrame() {
        MySQLDAO dao = new MySQLDAO();

        departmentComboBox = new JComboBox();
        departmentComboBox.setModel(new DefaultComboBoxModel() {
            private static final long serialVersionUID = 1L;
            boolean selectionAllowed = true;

            @Override
            public void setSelectedItem(Object anObject) {
                if (!NOT_SELECTABLE_OPTION.equals(anObject)) {
                    super.setSelectedItem(anObject);
                } else if (selectionAllowed) {
                    // Allow this just once
                    selectionAllowed = false;
                    super.setSelectedItem(anObject);
                }
            }
        });

        departmentComboBox.addItem(NOT_SELECTABLE_OPTION);
        for(Department x : dao.readDepartments()){
            departmentComboBox.addItem(x.getName());
        }
        departmentComboBox.setSelectedItem("d");
        Menu.mainFrm.getContentPane().add(departmentComboBox);

        table = new JTable(model);
        model.addColumn("Дисципліна");
        model.addColumn("Рік навчання");
        model.addColumn("Дата");
//        model.addColumn("Викладач");
        model.addColumn("Аудиторія");

        scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(850, 200));
        Menu.mainFrm.getContentPane().add(scrollPane);
        Menu.mainFrm.setVisible(true);

        departmentComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //remove all rows from table
                model.setRowCount(0);
                //add all exams from database and show them
                List<Exam> exams = dao.readExamsByDepartmentName(departmentComboBox.getSelectedItem().toString());
                for(Exam x : exams){
                    model.addRow(new Object[] { x.getCourse_name(), x.getGroup_year(), x.getDate(), x.getAud() });
                }
            }
        });
    }
}
