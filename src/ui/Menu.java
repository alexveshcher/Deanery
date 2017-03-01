package ui;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Menu extends JFrame {

//    private JButton ok;
    private JButton schedule;
    private JButton menu;
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
        schedule = new JButton("Розклад сесії");
        menu = new JButton("Головне меню");
        exams = new JButton("Іспити");
        mainFrm.getContentPane().add(schedule);
        mainFrm.setLocationRelativeTo(null);
        mainFrm.setVisible(true);
        schedule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                schedule.setVisible(false);
                mainFrm.getContentPane().add(menu);
                mainFrm.getContentPane().add(exams);
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
                        mainFrm.getContentPane().removeAll();
                        mainFrm.getContentPane().add(menu);
                        mainFrm.getContentPane().add(exams);
                        mainFrm.revalidate();
                        mainFrm.repaint();
                        new ExamsScheduleTable();
                    }
                });
            }
        });
    }

}
