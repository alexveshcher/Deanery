package ui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ui.ExamsScheduleTable;
import ui.TestsScheduleTable;
import vo.Exam;


public class Menu extends JFrame {

    private JButton ok;
    private JButton schedule;
    private JButton tests;
    private JButton exams;
    public static JFrame mainFrm;

    public Menu() {
        initialize();
    }

    private void initialize() {
        mainFrm = new JFrame("Вхід в систему");
        mainFrm.getContentPane().setLayout(new FlowLayout());
        mainFrm.setSize(500, 500);
        mainFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ok = new JButton("Вхід");
        schedule = new JButton("Розклад сесії");
        tests = new JButton("Заліки");
        exams = new JButton("Іспити");
        mainFrm.getContentPane().add(ok);
        mainFrm.getContentPane().add(schedule);
        mainFrm.setLocationRelativeTo(null);
        mainFrm.setVisible(true);
        schedule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ok.setVisible(false);
                schedule.setVisible(false);
                mainFrm.getContentPane().add(tests);
                mainFrm.getContentPane().add(exams);
                tests.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mainFrm.getContentPane().removeAll();
                        mainFrm.getContentPane().add(tests);
                        mainFrm.getContentPane().add(exams);
                        new TestsScheduleTable();
                    }
                });
                exams.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mainFrm.getContentPane().removeAll();
                        mainFrm.getContentPane().add(tests);
                        mainFrm.getContentPane().add(exams);
                        new ExamsScheduleTable();
                    }
                });
            }
        });
    }

}
