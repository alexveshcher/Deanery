package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    public static JTable table;
    public static JScrollPane scrollPane;

    public ExamsScheduleTable() {
        MySQLDAO dao = new MySQLDAO();
        table = new JTable(model);
        model.addColumn("Дисципліна");
        model.addColumn("Рік навчання");
        model.addColumn("Дата");
        model.addColumn("Викладач");
        model.addColumn("Аудиторія");

        //Dropdown list for courses
        TableColumn courseColumn = table.getColumnModel().getColumn(0);
        JComboBox comboBox0 = new JComboBox();
        for(Course x : dao.readCourses()){
            comboBox0.addItem(x.getName());
        }
        courseColumn.setCellEditor(new DefaultCellEditor(comboBox0));

        //Dropdown list for teachers
        TableColumn teachersColumn = table.getColumnModel().getColumn(3);
        JComboBox comboBox3 = new JComboBox();
        for(Teacher x : dao.readTeachers()){
            comboBox3.addItem(x.getName());
        }
        teachersColumn.setCellEditor(new DefaultCellEditor(comboBox3));

        //add all exams from database and show them
        List<Exam> exams = dao.readExams();
        for(Exam x : exams){
            model.addRow(new Object[] { x.getCourse_name(), x.getGroup_year(), x.getDate(), dao.readTeacherById(x.getProfessor_id()).getName() , x.getAud() });
        }

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                int column = target.getSelectedColumn();
//                System.out.println(row + " " + column);
                if(column == 4 && row == model.getRowCount()-1){
//                    System.out.println("auds triggered");
                    //Dropdown list for auditoriums
                    TableColumn audsColumn = table.getColumnModel().getColumn(4);
                    JComboBox comboBox4 = new JComboBox();
                    for(Auditorium x : dao.readFreeAuds(getTypedDate())){
                        comboBox4.addItem(x.getNumber());
                    }
                    audsColumn.setCellEditor(new DefaultCellEditor(comboBox4));
                }
            }
        });

        scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(450, 200));
        JButton addRow = new JButton("Додати");
        boolean isConfirmedd = true;
        Menu.mainFrm.getContentPane().add(scrollPane);
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
                    x.setProfessor_id(dao.readTeacherByName(model.getValueAt(model.getRowCount()-1,3).toString()).get(0).getId());
                    x.setAud(model.getValueAt(model.getRowCount()-1,4).toString());
                    dao.addExam(x);
                    System.out.println(x);
                }
            }
        });
    }

    private Date getTypedDate(){
        Date date = Date.valueOf("2005-01-01");
        if(model.getRowCount() > 0 && !model.getValueAt(model.getRowCount()-1,2).toString().isEmpty()){
            date = Date.valueOf(model.getValueAt(model.getRowCount()-1,2).toString());
        }
        return date;
    }

}
