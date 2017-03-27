package ui;

import dao.MySQLDAO;
import vo.Exam;
import vo.TGroup;
import vo.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class GroupExamsFrame extends JFrame{
    DefaultTableModel model = new DefaultTableModel();

    public static JTable table;
    public static JScrollPane scrollPane;
    JComboBox groupComboBox;

    private static final String NOT_SELECTABLE_OPTION = " - ... - ";

    public GroupExamsFrame() {
        MySQLDAO dao = new MySQLDAO();

        groupComboBox = new JComboBox();
        groupComboBox.setModel(new DefaultComboBoxModel() {
            private static final long serialVersionUID = 1L;
            boolean selectionAllowed = true;

            @Override
            public void setSelectedItem(Object anObject) {
                if (!NOT_SELECTABLE_OPTION.equals(anObject)) {
                    super.setSelectedItem(anObject);
                } else if (selectionAllowed) {
                    // Allow this just once
                    selectionAllowed = false;
                    super.setSelectedItem(anObject);
                }
            }
        });

        groupComboBox.addItem(NOT_SELECTABLE_OPTION);
        for(TGroup x : dao.readGroups()){
            groupComboBox.addItem(x.getId());
        }
        Menu.mainFrm.getContentPane().add(groupComboBox);

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

        groupComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //remove all rows from table
                model.setRowCount(0);
                //add all exams from database and show them
                List<Exam> exams = dao.readExamsByGroupId(Integer.valueOf(groupComboBox.getSelectedItem().toString()));
                for(Exam x : exams){
                    model.addRow(new Object[] { x.getCourse_name(), x.getGroup_year(), x.getDate(), dao.readTeacherById(x.getProfessor_id()).getName() , x.getAud() });
                }
            }
        });
    }
}
