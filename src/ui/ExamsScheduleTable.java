package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import dao.MySQLDAO;
import vo.Auditorium;
import vo.Course;
import vo.Exam;
import vo.Teacher;

import static ui.Menu.mainFrm;


public class ExamsScheduleTable extends JFrame  {

    DefaultTableModel model = new DefaultTableModel();

    public static JTable table;
    public static JScrollPane scrollPane;
    MySQLDAO dao;

    public ExamsScheduleTable() {
        dao = new MySQLDAO();
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
        table.setPreferredScrollableViewportSize(new Dimension(850, 200));
        JButton addButton = new JButton("Додати");
        JButton statsButton = new JButton("Статистика");
        JButton editButton = new JButton("Змінити");
        JButton deleteButton = new JButton("Видалити");

        boolean isConfirmedd = true;
        mainFrm.getContentPane().add(scrollPane);
        mainFrm.getContentPane().add(statsButton);
        mainFrm.getContentPane().add(addButton);
        mainFrm.getContentPane().add(editButton);
        mainFrm.getContentPane().add(deleteButton);
        mainFrm.setVisible(true);
        addButton.addActionListener(new ActionListener() {
            boolean isConfirmed = isConfirmedd;
            public void actionPerformed(ActionEvent e) {
                if(isConfirmed){
                    addButton.setText("Підтвердити");
                    model.addRow(new Object[] { "", "", "", "", "" });
                    isConfirmed = false;
                }
                else {
                    addButton.setText("Додати");
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
        statsButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            String course_name = model.getValueAt(selectedRow,0).toString();
            int group_year = Integer.parseInt(model.getValueAt(selectedRow,1).toString());
            JPanel panel = new StatisticsPanel(course_name,group_year);
            int result = JOptionPane.showConfirmDialog(null, panel,
                    "Статистика", JOptionPane.OK_OPTION);
//
//            //ensuring we have selected row
//            if(selectedRow > -1){
//                String course_name = model.getValueAt(selectedRow,0).toString();
//                int group_year = Integer.parseInt(model.getValueAt(selectedRow,1).toString());
//                mainFrm.getContentPane().remove(scrollPane);
//                mainFrm.getContentPane().remove(addButton);
//                mainFrm.getContentPane().remove(statsButton);
//                mainFrm.revalidate();
//                mainFrm.repaint();
//
//                new StatisticsPanel(course_name,group_year);
//            }

        });
        editButton.addActionListener(e -> {
            Exam exam = getSelectedExam();


            JTextField yearField = new JTextField(5);
            JTextField dateField = new JTextField(5);
            dateField.setText(exam.getDate().toString());

            yearField.setText(String.valueOf(exam.getGroup_year()));

            JComboBox courseBox = new JComboBox();
            for(Course x : dao.readCourses()){
                courseBox.addItem(x.getName());
            }

            courseBox.setSelectedItem(exam.getCourse_name());

            JComboBox teacherBox = new JComboBox();
            for(Teacher x : dao.readTeachers()){
                teacherBox.addItem(x.getName());
            }
            teacherBox.setSelectedItem(dao.readTeacherById(exam.getProfessor_id()).getName());

            JComboBox auditoriumBox = new JComboBox();
            for(Auditorium x : dao.readFreeAuds(getTypedDate())){
                auditoriumBox.addItem(x.getNumber());
            }
            auditoriumBox.setSelectedItem(exam.getAud());



            JPanel myPanel = new JPanel(new GridLayout(5,2));
            myPanel.setPreferredSize(new Dimension(350,100));
            myPanel.add(new JLabel("Дисципліна:"));
            myPanel.add(courseBox);
            myPanel.add(new JLabel("Рік навчання:"));
            myPanel.add(yearField);
            myPanel.add(new JLabel("Дата:"));
            myPanel.add(dateField);
            myPanel.add(new JLabel("Викладач:"));
            myPanel.add(teacherBox);
            myPanel.add(new JLabel("Аудиторія:"));
            myPanel.add(auditoriumBox);



            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Введіть нові дані", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
//                System.out.println("x value: " + courseNameField.getText());

                Exam x = new Exam();
                x.setCourse_name(courseBox.getSelectedItem().toString());
                x.setGroup_year(Integer.valueOf(yearField.getText()));
                x.setDate(Date.valueOf(dateField.getText()));
                x.setProfessor_id(dao.readTeacherByName(teacherBox.getSelectedItem().toString()).get(0).getId());
                x.setAud(auditoriumBox.getSelectedItem().toString());
                dao.updateExam(exam, x);
                model.setRowCount(0);
                //add all exams from database and show them
                List<Exam> examz = dao.readExams();
                for(Exam xx : examz){
                    model.addRow(new Object[] { xx.getCourse_name(), xx.getGroup_year(), xx.getDate(), dao.readTeacherById(xx.getProfessor_id()).getName() , xx.getAud() });
                }

            }

        });
        deleteButton.addActionListener(e -> {
            Exam exam = getSelectedExam();
            dao.deleteExam(exam);
            model.setRowCount(0);
            //add all exams from database and show them
            List<Exam> examz = dao.readExams();
            for(Exam xx : examz){
                model.addRow(new Object[] { xx.getCourse_name(), xx.getGroup_year(), xx.getDate(), dao.readTeacherById(xx.getProfessor_id()).getName() , xx.getAud() });
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

    private Exam getSelectedExam(){
        int i = table.getSelectedRow();

        Vector vector = (Vector) model.getDataVector().get(i);
        Object[] objArray = vector.toArray();

        Exam exam = new Exam();
        exam.setCourse_name(objArray[0].toString());
        exam.setGroup_year((int) objArray[1]);
        exam.setDate((Date) objArray[2]);
        exam.setProfessor_id(dao.readTeacherByName(objArray[3].toString()).get(0).getId());
        exam.setAud((String) objArray[4]);
        return exam;
    }

}
