package ui;


import dao.MySQLDAO;
import vo.Course;
import vo.Exam;
import vo.Student;
import vo.Teacher;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamResultsFrame extends JFrame {
    DefaultTableModel model = new DefaultTableModel();

    private JComboBox teacherComboBox;
    private JComboBox courseComboBox;
    private JComboBox examComboBox;

    public static JTable table;
    public static JScrollPane scrollPane;

    private JButton saveButton;

    private int lastColumn, lastRow = 0;

    List<Student> list;
    MySQLDAO dao;

    ExamResultsFrame(){
        dao = new MySQLDAO();
        list = new ArrayList<>();


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
        saveButton = new JButton("Save");
        Menu.mainFrm.getContentPane().add(saveButton);

        Menu.mainFrm.getContentPane().add(scrollPane);
        Menu.mainFrm.setVisible(true);

        teacherComboBox.addActionListener(e -> {
            courseComboBox.removeAllItems();
            for(String x : dao.readCourseFromExamsByTeacherName(teacherComboBox.getSelectedItem().toString())){
                courseComboBox.addItem(x);
            }
        });

        courseComboBox.addActionListener(e -> {
            list = new ArrayList<>();
            model.setRowCount(0);
            System.out.println("now should appear student table");
            if(courseComboBox.getSelectedItem()!=null) {
                for (Student x : dao.readStudentsByCourseExam(courseComboBox.getSelectedItem().toString())) {
                    model.addRow(new Object[]{x.getName(), dao.readResultByStudentId(x.getId(), courseComboBox.getSelectedItem().toString()).getMark()});
                    list.add(x);
                }
            }
        });

        saveButton.addActionListener(e -> {
//            System.out.println("saveButton.addActionListener(e ->");
            saveMarks();
        });


    }

    private Map<Integer,Integer> marks(){
        Map<Integer,Integer> marks =  new HashMap<>();
        int tableSize = table.getRowCount();
        for(int i = 0; i < tableSize; i++){
            marks.put(list.get(i).getId(),Integer.valueOf(table.getValueAt(i,1).toString()));
//            System.out.println(marks.get(list.get(i).getId()));
        }
        return marks;
    }

    private void saveMarks(){
        Map<Integer,Integer> map = marks();
        for (Integer key : map.keySet()) {
//            System.out.println(key + ": " +  map.get(key));
            dao.updateResultMark(key, dao.readResultByStudentId(0 , courseComboBox.getSelectedItem().toString()).getGroup_id(), map.get(key).toString());

        }
    }


}
