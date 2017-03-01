package ui;


import dao.MySQLDAO;
import vo.Course;
import vo.Exam;
import vo.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ExamResultsFrame extends JFrame {
    DefaultTableModel model = new DefaultTableModel();

    JComboBox teacherComboBox;
    JComboBox courseComboBox;
    JComboBox examComboBox;

    public static JTable table;
    public static JScrollPane scrollPane;

    ExamResultsFrame(){
        MySQLDAO dao = new MySQLDAO();

        teacherComboBox = new JComboBox();
        for(Teacher x : dao.readTeachers()){
            teacherComboBox.addItem(x.getName());
        }
        Menu.mainFrm.getContentPane().add(teacherComboBox);

        courseComboBox = new JComboBox();
        Menu.mainFrm.getContentPane().add(courseComboBox);


        table = new JTable(model);
        model.addColumn("Студент");
        model.addColumn("Бал");

        scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(850, 200));
        Menu.mainFrm.getContentPane().add(scrollPane);
        Menu.mainFrm.setVisible(true);

        teacherComboBox.addActionListener(e -> {
            courseComboBox.removeAllItems();
            for(String x : dao.readCourseFromExamsByTeacherName(teacherComboBox.getSelectedItem().toString())){
                courseComboBox.addItem(x);
            }
        });

    }

}
