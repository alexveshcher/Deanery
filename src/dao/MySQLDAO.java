package dao;



import vo.Auditorium;
import vo.Course;
import vo.Exam;
import vo.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*

conn =DriverManager.getConnection("jdbc:mysql://eu-cdbr-azure-west-d.cloudapp.net/acsm_7c8aacf011bf046?" +
                    "user=b0253c18790b3c&password=d5e58ccb");
 */

public class MySQLDAO {

    public Connection getConnection()  {
        String user = "admin";
        String password = "12345678";
        String database = "deanery";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/"
                    + database + "?user=" + user + "&password=" + password
                    + "&autoReconnect=true&useSSL=false"
            );
//            conn = DriverManager.getConnection("jdbc:mysql://eu-cdbr-azure-west-d.cloudapp.net/acsm_7c8aacf011bf046?user=b0253c18790b3c&password=d5e58ccb");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("You've successfully connected to DB");
        return conn;
    }

    public List<Auditorium> readAuds(){
        List<Auditorium> list = new ArrayList<>();
        String sql = "SELECT * FROM AUDITORIUM";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Auditorium a = new Auditorium();
                a.setNumber(rs.getString("number"));
                list.add(a);
//                System.out.println(a); //DEBUG
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return list;
    }

    public List<Auditorium> readFreeAuds(Date date){
        List<Auditorium> list = new ArrayList<>();
        String sql = "SELECT number\n" +
                "FROM AUDITORIUM\n" +
                "WHERE number NOT IN (SELECT aud\n" +
                "                     FROM EXAM\n" +
                "                     WHERE date = '"+date+"');";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Auditorium a = new Auditorium();
                a.setNumber(rs.getString("number"));
                list.add(a);
//                System.out.println(a); //DEBUG
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return list;
    }

    public List<Course> readCourses(){
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM COURSE";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Course x = new Course();
                x.setName(rs.getString("name"));
                list.add(x);
//                System.out.println(x); //DEBUG
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return list;
    }

    public List<Teacher> readTeachers(){
        List<Teacher> list = new ArrayList<>();
        String sql = "SELECT * FROM TEACHER";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Teacher x = new Teacher();
                x.setName(rs.getString("name"));
                list.add(x);
//                System.out.println(x); //DEBUG
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return list;
    }

    public List<Teacher> readTeacherByName(String name){
        List<Teacher> list = new ArrayList<>();
        String sql = "SELECT * FROM TEACHER\n" +
                "WHERE name='"+ name + "'";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Teacher x = new Teacher();
                x.setId(rs.getInt("id"));
                x.setName(rs.getString("name"));
                list.add(x);
//                System.out.println(x); //DEBUG
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return list;
    }

    public Teacher readTeacherById(int id){
        Teacher x = new Teacher();
        String sql = "SELECT * FROM TEACHER\n" +
                "WHERE id='"+ id + "'";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                x.setId(rs.getInt("id"));
                x.setName(rs.getString("name"));
                x.setDepartment_name(rs.getString("name"));
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return x;
    }


    public List<Exam> readExams(){
        List<Exam> list = new ArrayList<>();
        String sql = "SELECT * FROM EXAM " +
                "ORDER BY date";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Exam x = new Exam();
                x.setCourse_name(rs.getString("course_name"));
                x.setGroup_year(rs.getInt("group_year"));
                x.setDate(rs.getDate("date"));
                x.setProfessor_id(rs.getInt("professor_id"));
                x.setAud(rs.getString("aud"));
                list.add(x);
//                System.out.println(x); //DEBUG
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return list;
    }

    public void addExam(Exam x){
        String sql = "INSERT INTO EXAM (course_name, group_year, date, professor_id, aud) VALUES ("+
                "'" + x.getCourse_name()+"',"+
                "'" + x.getGroup_year()+"',"+
                "'" + x.getDate()+"',"+
                "'" + x.getProfessor_id()+"',"+
                "'" + x.getAud()+"');";
        PreparedStatement stm = null;
        try {
            stm = getConnection().prepareStatement(sql);
            stm.executeUpdate();
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
//        System.out.printf(sql); //DEBUG
    }

    public List<Exam> readExamsByTeacherName(String name){
        List<Exam> list = new ArrayList<>();
        String sql = "SELECT * FROM EXAM\n" +
                "WHERE professor_id = (SELECT id FROM TEACHER\n" +
                "WHERE TEACHER.name = '"+ name + "')" +
                "ORDER BY date";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Exam x = new Exam();
                x.setCourse_name(rs.getString("course_name"));
                x.setGroup_year(rs.getInt("group_year"));
                x.setDate(rs.getDate("date"));
                x.setAud(rs.getString("aud"));
                list.add(x);
                System.out.println(x); //DEBUG
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return list;
    }



}