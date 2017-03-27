package ui;

import vo.Teacher;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Menu extends JFrame {

//    private JButton ok;
    private JButton schedule;
    private JButton menu;
    private JButton exams;
    private JButton teachersExams;
    private JButton departmentExams;
    private JButton groupExams;
    private JButton dateExams;
    private JButton results;
    public static JFrame mainFrm;

    public Menu() {
        initialize();
    }

    private void initialize() {
        mainFrm = new JFrame("Деканат");
        mainFrm.getContentPane().setLayout(new FlowLayout());
        mainFrm.setSize(900, 500);
        mainFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        schedule = new JButton("Розклад сесії");
        menu = new JButton("Головне меню");
        exams = new JButton("Іспити");
        teachersExams = new JButton("Розклад екзаменів викладача");
        departmentExams = new JButton("Розклад екзаменів кафедри");
        groupExams = new JButton("Розклад екзаменів групи");
        dateExams = new JButton("Розклад екзаменів за датою");

        results = new JButton("Результати сесії");

        mainFrm.getContentPane().add(schedule);
        mainFrm.setLocationRelativeTo(null);
        mainFrm.setVisible(true);


        schedule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                schedule.setVisible(false);
                addTopButtons();
                //hope it is the correct way to refresh elements
                mainFrm.revalidate();
                mainFrm.repaint();

                //redirecting to main manu
                menu.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mainFrm.getContentPane().removeAll();
                        mainFrm.getContentPane().add(schedule);
                        schedule.setVisible(true);
                        //hope it is the correct way to refresh elements
                        mainFrm.revalidate();
                        mainFrm.repaint();

                    }
                });

                exams.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        redraw();
                        new ExamsScheduleTable();
                    }
                });

                teachersExams.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        redraw();
                        new TeachersExamsFrame();
                    }
                });

                departmentExams.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        redraw();
                        new DepartmentExamsFrame();
                    }
                });

                groupExams.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        redraw();
                        new GroupExamsFrame();
                    }
                });

                dateExams.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        redraw();
                        new DateExamsFrame();
                    }
                });

                results.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        redraw();
                        new ExamResultsFrame();
                    }
                });
            }
        });
    }

    private void addTopButtons(){
        mainFrm.getContentPane().add(menu);
        mainFrm.getContentPane().add(exams);
        mainFrm.getContentPane().add(teachersExams);
        mainFrm.getContentPane().add(departmentExams);
        mainFrm.getContentPane().add(groupExams);
        mainFrm.getContentPane().add(dateExams);
        mainFrm.getContentPane().add(results);
    }

    private void redraw(){
        mainFrm.getContentPane().removeAll();
        addTopButtons();
        mainFrm.revalidate();
        mainFrm.repaint();
    }

}
