package ui;


import dao.MySQLDAO;
import vo.Course;
import vo.Exam;
import vo.Student;
import vo.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ExamResultsFrame extends JFrame {
    DefaultTableModel model = new DefaultTableModel();

    private JComboBox teacherComboBox;
    private JComboBox courseComboBox;
    private JComboBox examComboBox;

    public static JTable table;
    public static JScrollPane scrollPane;

    private int lastColumn, lastRow = 0;

    ExamResultsFrame(){
        MySQLDAO dao = new MySQLDAO();
        List<Student> list = new ArrayList<>();


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

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                JTable target = (JTable)e.getSource();
                int row = target.getSelectedRow();
                int column = target.getSelectedColumn();
                // do some action if appropriate column
//                System.out.println(row + " " + column);
                if(lastColumn == 1){
                    String lastValue = model.getValueAt(lastRow, lastColumn).toString();
                    int student_id = list.get(lastRow).getId();
                    dao.updateResultMark(list.get(lastRow).getId(), dao.readResultByStudentId(student_id , courseComboBox.getSelectedItem().toString()).getStudent_id() , lastValue);
                    System.out.println(model.getValueAt(lastRow,lastColumn));

                }
                lastRow = row;
                lastColumn = column;
            }
        });

        teacherComboBox.addActionListener(e -> {
            courseComboBox.removeAllItems();
            for(String x : dao.readCourseFromExamsByTeacherName(teacherComboBox.getSelectedItem().toString())){
                courseComboBox.addItem(x);
            }
        });

        courseComboBox.addActionListener(e -> {
            model.setRowCount(0);
            System.out.println("now should appear student table");
            for(Student x : dao.readStudentsByCourseExam(courseComboBox.getSelectedItem().toString())){
                model.addRow(new Object[] {x.getName(), ""});
                list.add(x);
            }
        });



    }

}
