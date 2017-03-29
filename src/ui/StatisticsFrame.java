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
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(0, 1));
        JLabel exam = new JLabel("Іспит: " + dao.readExamByCourseNameAndGroupYear(course_name,group_year));

        JLabel text1 = new JLabel("Кількість студентів");
        JLabel a = new JLabel("A: " + dao.studentsWithA(course_name,group_year));
        JLabel b = new JLabel("B: " + dao.studentsWithB());
        JLabel c = new JLabel("C: " + dao.studentsWithC());
        JLabel d = new JLabel("D: " + dao.studentsWithD());
        JLabel e = new JLabel("E: " + dao.studentsWithE());
        JLabel f = new JLabel("F: " + dao.studentsWithF());

        JLabel notPresent = new JLabel("Не присутні: " + dao.notPresentStudents());
        JLabel average = new JLabel("Середній бал: " + dao.averageMark(course_name,group_year));

        statsPanel.add(exam);
        statsPanel.add(text1);
        statsPanel.add(a);
        statsPanel.add(b);
        statsPanel.add(c);
        statsPanel.add(d);
        statsPanel.add(e);
        statsPanel.add(f);
        statsPanel.add(notPresent);
        statsPanel.add(average);
        Menu.mainFrm.add(statsPanel);
        Menu.mainFrm.setVisible(true);

    }
}
