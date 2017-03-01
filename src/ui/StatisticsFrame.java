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

public class StatisticsFrame extends JFrame {


    public StatisticsFrame(String course_name, int group_year) {
        MySQLDAO dao = new MySQLDAO();
        JLabel exam = new JLabel("Іспит: " + dao.readExamByCourseNameAndGroupYear(course_name,group_year));

        JLabel text1 = new JLabel("Кількість студентів");
        JLabel a = new JLabel("A: " + dao.studentsWithA());
        JLabel b = new JLabel("B: " + dao.studentsWithB());
        JLabel c = new JLabel("C: " + dao.studentsWithC());
        JLabel d = new JLabel("D: " + dao.studentsWithD());
        JLabel e = new JLabel("E: " + dao.studentsWithE());
        JLabel f = new JLabel("F: " + dao.studentsWithF());

        JLabel notPresent = new JLabel("Не присутні: " + dao.notPresentStudents());
        JLabel average = new JLabel("Середній бал: " + dao.averageMark());

        Menu.mainFrm.getContentPane().add(exam);
        Menu.mainFrm.getContentPane().add(text1);
        Menu.mainFrm.getContentPane().add(a);
        Menu.mainFrm.getContentPane().add(b);
        Menu.mainFrm.getContentPane().add(c);
        Menu.mainFrm.getContentPane().add(d);
        Menu.mainFrm.getContentPane().add(e);
        Menu.mainFrm.getContentPane().add(f);
        Menu.mainFrm.getContentPane().add(notPresent);
        Menu.mainFrm.getContentPane().add(average);
        Menu.mainFrm.setVisible(true);

    }
}
