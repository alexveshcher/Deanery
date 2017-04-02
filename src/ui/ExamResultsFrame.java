package ui;


import dao.MySQLDAO;
import vo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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

        Menu.mainFrm.getContentPane().add(scrollPane);
        Menu.mainFrm.getContentPane().add(saveButton);
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
                List<Result> results = dao.readResultsByCourseExam(courseComboBox.getSelectedItem().toString());
                List<Student> students = dao.readStudentsByCourseExam(courseComboBox.getSelectedItem().toString());
                for (int i = 0; i < students.size(); i++) {
                    model.addRow(new Object[]{students.get(i).getName(), results.get(i).getMark()});
                    list.add(students.get(i));
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
            String markCell =  table.getValueAt(i,1).toString();
            Integer mark = null;
            if(!markCell.equals("")){
                mark = Integer.valueOf(markCell);
            }

            marks.put(list.get(i).getId(),mark);
//            System.out.println(marks.get(list.get(i).getId()));
        }
        return marks;
    }

    private void saveMarks(){
        Map<Integer,Integer> map = marks();
        for (Integer key : map.keySet()) {
//            System.out.println(key + ": " +  map.get(key));
            String mark = "";
            if(map.get(key) != null){
                mark = map.get(key).toString();
            }
            dao.updateResultMark(key, dao.readResultByStudentId(0 , courseComboBox.getSelectedItem().toString()).getGroup_id(), mark);

        }
    }


}
