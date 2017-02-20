package dao;



import vo.Auditorium;
import vo.Exam;

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
                    + database + "?user=" + user + "&password=" + password);
//            conn = DriverManager.getConnection("jdbc:mysql://eu-cdbr-azure-west-d.cloudapp.net/acsm_7c8aacf011bf046?user=b0253c18790b3c&password=d5e58ccb");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("You've successfully connected to DB");
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
                System.out.println(a); //DEBUG
            }
            stm.close();
        } catch (SQLException e) {
            System.out.println("Feel the pain of sql:" + e);
        }
        return list;
    }

    public List<Exam> readExams(){
        List<Exam> list = new ArrayList<>();
        String sql = "SELECT * FROM EXAM";
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
        System.out.printf(sql); //DEBUG
    }

//    @Override
//    public Book readBook(int key) throws SQLException {
//        Book b = new Book();
//        String sql = "SELECT * FROM APP.BOOKS\n"
//                + "WHERE ID = "+key+"\n";
//        //+ "OR PUBLISHINGYEAR LIKE %"+Integer.parseInt(searchWord)+"%";
//
//        PreparedStatement stm = getConnection().prepareStatement(sql);
//        ResultSet rs = stm.executeQuery();
//
//        while (rs.next()) {
//            b.setId(rs.getInt("id"));
//            b.setTitle(rs.getString("title"));
//            b.setAuthors(rs.getString("authors"));
//            b.setYear(rs.getInt("publishingyear"));
//
//        }
//        return b;
//    }
//
//    /*
//        @Override
//        public List<Book> getAllBooks() throws SQLException {
//            String sql = "SELECT * FROM APP.BOOKS";
//            PreparedStatement stm = getConnection().prepareStatement(sql);
//            ResultSet rs = stm.executeQuery();
//            List<Book> list = new ArrayList<Book>();
//            while (rs.next()) {
//                Book b = new Book();
//                b.setId(rs.getInt("id"));
//                b.setTitle(rs.getString("title"));
//                b.setAuthors(rs.getString("authors"));
//                b.setYear(rs.getInt("publishingyear"));
//
//                list.add(b);
//            }
//            return list;
//        }
//    */
//    public List<Book> searchBook(String searchWord) throws SQLException {
//        List<Book> list = null;
//        String sql = "SELECT * FROM APP.BOOKS\n"
//                + "WHERE AUTHORS LIKE '%"+searchWord+"%'\n"
//                + "OR TITLE LIKE '%"+searchWord+"%'\n";
//        //+ "OR PUBLISHINGYEAR LIKE %"+Integer.parseInt(searchWord)+"%";
//
//        PreparedStatement stm = getConnection().prepareStatement(sql);
//        ResultSet rs = stm.executeQuery();
//        list = new ArrayList<Book>();
//        while (rs.next()) {
//            Book b = new Book();
//            b.setId(rs.getInt("id"));
//            b.setTitle(rs.getString("title"));
//            b.setAuthors(rs.getString("authors"));
//            b.setYear(rs.getInt("publishingyear"));
//
//            list.add(b);
//        }
//        return list;
//    }
//
//
//
//
//
//    @Override
//    public void makeOrder(Order ord) throws SQLException {
//        String sql = "INSERT INTO APP.ORDERS (BOOK_ID, STUDENT_ID, COMPLETED) VALUES ("
//                +ord.getBook().getId()+","+
//                +ord.getStudent()+","
//                +ord.isCompleted()+")";
//        PreparedStatement stm = getConnection().prepareStatement(sql);
//        stm.executeUpdate();
//        stm.close();
//    }
//
//
//
//
//
//    @Override
//    public List<Order> getUncompleted() throws SQLException {
//        String sql = "SELECT * FROM orders WHERE COMPLETED=FALSE ";
//        PreparedStatement stm = getConnection().prepareStatement(sql);
//        ResultSet rs = stm.executeQuery();
//        List<Order> list = new ArrayList<Order>();
//        Book b;
//
//        while (rs.next()) {
//            Order ord = new Order();
//            ord.setId(rs.getInt("id"));
//            b = readBook(rs.getInt("BOOK_ID"));
//            ord.setBook(b);
//            ord.setStudent(rs.getInt("STUDENT_ID"));
//            ord.setCompleted(rs.getBoolean("completed"));
//            list.add(ord);
//        }
//        return list;
//    }
//
//    public List<Order> search(String searchWord) throws SQLException {
//        List<Order> list = null;
//        String sql = "SELECT * FROM APP.ORDERS\n" + "WHERE ID LIKE '%"
//                + searchWord + "%'\n" + "OR BOOK_ID LIKE '%" + searchWord
//                + "%'\n";
//
//        PreparedStatement stm = getConnection().prepareStatement(sql);
//        ResultSet rs = stm.executeQuery();
//        list = new ArrayList<Order>();
//        while (rs.next()) {
//            Order ord = new Order();
//            ord.setId(rs.getInt("id"));
//            list.add(ord);
//        }
//        return list;
//    }
}