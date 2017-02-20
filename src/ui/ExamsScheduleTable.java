package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingUtilities;

import dao.MySQLDAO;
import ui.Menu;
import vo.Auditorium;
import vo.Exam;


public class ExamsScheduleTable extends JFrame {

    DefaultTableModel model = new DefaultTableModel();

    public static JTable jTabSchedule;
    public static JScrollPane jscrlp;

    public ExamsScheduleTable() {
        MySQLDAO dao = new MySQLDAO();
        jTabSchedule = new JTable(model);
        model.addColumn("Дисципліна");
        model.addColumn("Рік навчання");
        model.addColumn("Дата");
        model.addColumn("Викладач");
        model.addColumn("Аудиторія");

        List<Exam> exams = dao.readExams();
        for(Exam x : exams){
            model.addRow(new Object[] { x.getCourse_name(), x.getGroup_year(), x.getDate(), x.getProfessor_id() , x.getAud() });

        }

//        model.addRow(new Object[] { "ТМ", "4", "20.04.2017", "А. О. Афонін", "2-214" });
//        model.addRow(new Object[] { "ІС", "4", "24.04.2017", "О. П. Жежерун", "1-223" });
//        model.addRow(new Object[] { "УПП", "4", "28.04.2017",  "В. С. Проценко", "1-310" });
//        model.addRow(new Object[] { "УПП", "4", "28.04.2017",  "В. С. Проценко", auds.get(1).getNumber() });
        jscrlp = new JScrollPane(jTabSchedule);
        jTabSchedule.setPreferredScrollableViewportSize(new Dimension(450, 200));
        JButton addRow = new JButton("Додати");
        boolean isConfirmedd = true;
        Menu.mainFrm.getContentPane().add(jscrlp);
        Menu.mainFrm.getContentPane().add(addRow);
        Menu.mainFrm.setVisible(true);
        addRow.addActionListener(new ActionListener() {
            boolean isConfirmed = isConfirmedd;
            public void actionPerformed(ActionEvent e) {
                if(isConfirmed){
                    addRow.setText("Підтвердити");
                    model.addRow(new Object[] { "", "", "", "", "" });
                    isConfirmed = false;
                }
                else {
                    addRow.setText("Додати");
                    isConfirmed = true;
                    Exam x = new Exam();
                    x.setCourse_name(model.getValueAt(model.getRowCount()-1,0).toString());
                    x.setGroup_year(Integer.parseInt(model.getValueAt(model.getRowCount()-1,1).toString()));
                    x.setDate(Date.valueOf(model.getValueAt(model.getRowCount()-1,2).toString()));
                    x.setProfessor_id(Integer.parseInt(model.getValueAt(model.getRowCount()-1,3).toString()));
                    x.setAud(model.getValueAt(model.getRowCount()-1,4).toString());
                    dao.addExam(x);
                    System.out.println(x);
                }
            }
        });
    }

}
