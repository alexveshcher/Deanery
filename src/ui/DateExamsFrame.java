package ui;

import dao.MySQLDAO;
import vo.Exam;
import vo.TGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DateExamsFrame extends JFrame{
    DefaultTableModel model = new DefaultTableModel();

    public static JTable table;
    public static JScrollPane scrollPane;
    JTextField dateTextField;


    private static final String NOT_SELECTABLE_OPTION = " - ... - ";

    public DateExamsFrame() {
        MySQLDAO dao = new MySQLDAO();

        dateTextField = new JTextField(15);

        Menu.mainFrm.getContentPane().add(dateTextField);

        table = new JTable(model);
        model.addColumn("Дисципліна");
        model.addColumn("Рік навчання");
        model.addColumn("Дата");
        model.addColumn("Викладач");
        model.addColumn("Аудиторія");

        scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(850, 200));
        Menu.mainFrm.getContentPane().add(scrollPane);
        Menu.mainFrm.setVisible(true);

        dateTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //remove all rows from table
                model.setRowCount(0);
                //add all exams from database and show them
                List<Exam> exams = dao.readExamsByDate(dateTextField.getText());
                for(Exam x : exams){
                    model.addRow(new Object[] { x.getCourse_name(), x.getGroup_year(), x.getDate(), dao.readTeacherById(x.getProfessor_id()).getName() , x.getAud() });
                }
            }
        });
    }
}
