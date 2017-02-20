package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.sql.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import dao.MySQLDAO;
import vo.Auditorium;
import vo.Course;
import vo.Exam;
import vo.Teacher;


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

        //Dropdown list for courses
        TableColumn courseColumn = jTabSchedule.getColumnModel().getColumn(0);
        JComboBox comboBox0 = new JComboBox();
        for(Course x : dao.readCourses()){
            comboBox0.addItem(x.getName());
        }
        courseColumn.setCellEditor(new DefaultCellEditor(comboBox0));

        //Dropdown list for auditoriums
        TableColumn teachersColumn = jTabSchedule.getColumnModel().getColumn(3);
        JComboBox comboBox3 = new JComboBox();
        for(Teacher x : dao.readTeachers()){
            comboBox3.addItem(x.getName());
        }
        teachersColumn.setCellEditor(new DefaultCellEditor(comboBox3));


        //Dropdown list for auditoriums
        TableColumn audsColumn = jTabSchedule.getColumnModel().getColumn(4);
        JComboBox comboBox4 = new JComboBox();
        for(Auditorium x : dao.readAuds()){
            comboBox4.addItem(x.getNumber());
        }
        audsColumn.setCellEditor(new DefaultCellEditor(comboBox4));

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
