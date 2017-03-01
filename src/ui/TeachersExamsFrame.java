package ui;

import dao.MySQLDAO;
import vo.Exam;
import vo.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class TeachersExamsFrame extends JFrame{
    DefaultTableModel model = new DefaultTableModel();

    public static JTable table;
    public static JScrollPane scrollPane;
    JComboBox teachersComboBox;

    public TeachersExamsFrame() {
        MySQLDAO dao = new MySQLDAO();

        teachersComboBox = new JComboBox();
        for(Teacher x : dao.readTeachers()){
            teachersComboBox.addItem(x.getName());
        }
        Menu.mainFrm.getContentPane().add(teachersComboBox);

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

        teachersComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //remove all rows from table
                model.setRowCount(0);
                //add all exams from database and show them
                List<Exam> exams = dao.readExamsByTeacherName(teachersComboBox.getSelectedItem().toString());
                for(Exam x : exams){
                    model.addRow(new Object[] { x.getCourse_name(), x.getGroup_year(), x.getDate(), x.getAud() });
                }
            }
        });
    }
}
