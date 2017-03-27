package dao;



import vo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*

conn =DriverManager.getConnection("jdbc:mysql://eu-cdbr-azure-west-d.cloudapp.net/acsm_7c8aacf011bf046?" +
                    "user=b0253c18790b3c&password=d5e58ccb");
 */

public class MySQLDAO {
    private final String CON1 = "jdbc:mysql://eu-cdbr-azure-west-d.cloudapp.net/acsm_7c8aacf011bf046?" +
            "user=b0253c18790b3c&password=d5e58ccb";

    private final String CON2 = "jdbc:mysql://localhost/"
            + "deanery?user=admin&password=12345678"
            + "&autoReconnect=true&useSSL=false";

    private final String CON3 = "jdbc:mysql://194.44.143.138/projman2?" +
            "user=projman2&password=!projman";

    public Connection getConnection()  {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(CON2);
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

    public List<Department> readDepartments(){
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM DEPARTMENT";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Department x = new Department();
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

    public List<TGroup> readGroups(){
        List<TGroup> list = new ArrayList<>();
        String sql = "SELECT * FROM TGROUP";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                TGroup x = new TGroup();
                x.setId(rs.getInt("id"));
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

    public List<Exam> readExamsByDepartmentName(String name){
        List<Exam> list = new ArrayList<>();
        String sql = "SELECT * FROM EXAM\n" +
                "WHERE course_name IN (SELECT name FROM COURSE\n" +
                "WHERE department_name = '"+ name + "')" +
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

    public List<Exam> readExamsByGroupId(int id){
        List<Exam> list = new ArrayList<>();
        String sql = "SELECT * FROM EXAM\n" +
                "WHERE course_name   IN (SELECT course_name  FROM TGROUP \n" +
                "WHERE id = '"+ id + "')" +
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
                System.out.println(x); //DEBUG
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return list;
    }

    public Exam readExamByCourseNameAndGroupYear(String course_name, int group_year){
        Exam x = new Exam();

        String sql = "SELECT * FROM EXAM\n" +
                "WHERE course_name = '" +course_name+ "' AND group_year='"+group_year+"'" ;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                x.setCourse_name(rs.getString("course_name"));
                x.setGroup_year(rs.getInt("group_year"));
                x.setDate(rs.getDate("date"));
                x.setProfessor_id(rs.getInt("professor_id"));
                x.setAud(rs.getString("aud"));
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return x;
    }

    public List<String> readCourseFromExamsByTeacherName(String name){
        List<String> list = new ArrayList<>();
        String sql = "SELECT course_name FROM EXAM\n" +
                "WHERE professor_id = (SELECT id FROM TEACHER\n" +
                "WHERE TEACHER.name = '"+ name + "')" +
                "ORDER BY date";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String x = rs.getString("course_name");
                list.add(x);
                System.out.println(x); //DEBUG
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return list;
    }

    public int studentsWithA(){
        String sql = "";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            stm.close();
        } catch (SQLException e) {
//            System.out.println("Feel the pain of sql:" + e);
        }
        return 0;
    }

    public int studentsWithB(){
        return 0;
    }

    public int studentsWithC(){
        return 0;
    }

    public int studentsWithD(){
        return 0;
    }

    public int studentsWithE(){
        return 0;
    }

    public int studentsWithF(){
        return 0;
    }

    public int notPresentStudents(){
        return 0;
    }

    public double averageMark(){
        return 79.05;
    }

    public List<Student> readStudentsByCourseExam(String course_name){
        List<Student> list = new ArrayList<>();
        String sql = "SELECT *\n" +
                "FROM STUDENT\n" +
                "WHERE id IN (SELECT student_id\n" +
                "            FROM RESULT\n" +
                "            WHERE group_id IN (SELECT id\n" +
                "                              FROM TGROUP, EXAM\n" +
                "                              WHERE TGROUP.year = EXAM.group_year AND TGROUP.course_name = '"+ course_name+ "'));";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Student x = new Student();
                x.setId(rs.getInt("id"));
                x.setName(rs.getString("name"));
                //need to add other fields
                list.add(x);
                System.out.println(x); //DEBUG
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return list;
    }

    public List<Result> readResultsByCourseExam(String course_name){
        List<Result> list = new ArrayList<>();
        String sql = "SELECT *\n" +
                "FROM STUDENT\n" +
                "WHERE id = (SELECT student_id\n" +
                "            FROM RESULT\n" +
                "            WHERE group_id = (SELECT id\n" +
                "                              FROM TGROUP, EXAM\n" +
                "                              WHERE TGROUP.year = EXAM.group_year AND TGROUP.course_name = '"+ course_name+ "'));";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Result x = new Result();
                x.setGroup_id(rs.getInt("group_id"));
                x.setStudent_id(rs.getInt("student_id"));
                x.setMark(rs.getString("mark"));
                //need to add other fields
                list.add(x);
                System.out.println(x); //DEBUG
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return list;
    }

    public Result readResultByStudentId(int student_id, String course_name){
        Result x = new Result();
        String sql = "SELECT *\n" +
                "            FROM RESULT\n" +
                "            WHERE group_id = (SELECT id\n" +
                "                              FROM TGROUP, EXAM\n" +
                "                              WHERE TGROUP.year = EXAM.group_year AND TGROUP.course_name = '"+ course_name+ "');";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {

                x.setGroup_id(rs.getInt("group_id"));
                x.setStudent_id(rs.getInt("student_id"));
                x.setMark(rs.getString("mark"));
                //need to add other fields
                System.out.println(x); //DEBUG
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return x;
    }

    public void updateResultMark(int student_id, int group_id, String mark){
        String sql = "UPDATE RESULT\n" +
                "SET mark = '" + mark +"'\n" +
                "WHERE group_id ="+ group_id +" AND student_id ="+ student_id +";";

        Statement stm = null;

        try {
            stm = getConnection().createStatement();
            stm.executeUpdate(sql);
            stm.close();
            getConnection().close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
    }


}