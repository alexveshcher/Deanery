package ui;


import dao.MySQLDAO;
import vo.Exam;
import vo.Result;
import vo.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class StatisticsPanel extends JPanel {


    public StatisticsPanel(String course_name, int group_year) {
        MySQLDAO dao = new MySQLDAO();
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(0, 1));

        List<Result> results = dao.readResultsByCourseExam(course_name);


        JLabel text1 = new JLabel("Кількість студентів");
        JLabel a = new JLabel("A: " + countA(results));
        JLabel b = new JLabel("B: " + countB(results));
        JLabel c = new JLabel("C: " + countC(results));
        JLabel d = new JLabel("D: " + countD(results));
        JLabel e = new JLabel("E: " + countE(results));
        JLabel f = new JLabel("F: " + countF(results));

        JLabel notPresent = new JLabel("Не присутні: " + dao.notPresentStudents());
        JLabel average = new JLabel("Середній бал: " + avg(results));

        statsPanel.add(text1);
        statsPanel.add(a);
        statsPanel.add(b);
        statsPanel.add(c);
        statsPanel.add(d);
        statsPanel.add(e);
        statsPanel.add(f);
        statsPanel.add(notPresent);
        statsPanel.add(average);
        this.add(statsPanel);
    }

    private double avg(List<Result> results){
        double sum = 0;
        double size = results.size();
        for(Result result : results){
            sum+=Double.valueOf(result.getMark());
        }
        return sum/size;
    }

    private int countA(List<Result> results){
        int sum = 0;
        for(Result result : results){
            double mark = Double.valueOf(result.getMark());
            if(mark > 90 )
            sum++;
        }
        return sum;
    }

    private int countB(List<Result> results){
        int sum = 0;
        for(Result result : results){
            double mark = Double.valueOf(result.getMark());
            if(mark > 80 && mark < 91 )
                sum++;
        }
        return sum;
    }

    private int countC(List<Result> results){
        int sum = 0;
        for(Result result : results){
            double mark = Double.valueOf(result.getMark());
            if(mark > 70 && mark < 81 )
                sum++;
        }
        return sum;
    }

    private int countD(List<Result> results){
        int sum = 0;
        for(Result result : results){
            double mark = Double.valueOf(result.getMark());
            if(mark > 65 && mark < 71 )
                sum++;
        }
        return sum;
    }
    private int countE(List<Result> results){
        int sum = 0;
        for(Result result : results){
            double mark = Double.valueOf(result.getMark());
            if(mark > 59 && mark < 66 )
                sum++;
        }
        return sum;
    }

    private int countF(List<Result> results){
        int sum = 0;
        for(Result result : results){
            double mark = Double.valueOf(result.getMark());
            if(mark < 60 )
                sum++;
        }
        return sum;
    }
}
