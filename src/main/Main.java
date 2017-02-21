package main;

import dao.MySQLDAO;
import ui.Menu;

import javax.swing.*;
import java.sql.Date;

/**
 * Created by alex on 2/13/17.
 */
public class Main {
    public static void main(String[] args) {
        new Menu();
//        MySQLDAO mySQLDAO = new MySQLDAO();
//        mySQLDAO.getConnection();
//        mySQLDAO.readAuds();
//        mySQLDAO.readExams();
//        System.out.println(mySQLDAO.readTeacherByName("Glybovets").get(0));
//        System.out.println(mySQLDAO.readTeacherById(1));
//        mySQLDAO.readFreeAuds(Date.valueOf("2017-03-03"));
    }
}
