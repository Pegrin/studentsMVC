package models.dao;

import common.exceptions.UserDAOException;
import models.connector.AcademConnector;
import models.pojo.Student;
import models.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bot on 23.02.17.
 */
@Repository
public class StudentDao {

    private Logger logger = Logger.getLogger(StudentDao.class);

    private String SQL_ALL_STUDENTS = "SELECT * FROM \"Main\".\"Student\"";

    private String SQL_STUDENTS_GROUP = "SELECT * FROM \"Main\".\"Student\" WHERE id_group = ?";

    private String SQL_FIND_STUDENT = "SELECT * FROM \"Main\".\"Student\" WHERE id =?";

    private String SQL_DELETE_STUDENT = "DELETE FROM \"Main\".\"Student\" WHERE id = ?";

    private String SQL_UPDATE_STUDENT = "UPDATE \"Main\".\"Student\"\n" +
            "\tSET id=?, name=?, birthdate=?, sex=?, id_group=?, email = ?" +
            "\tWHERE id=?";

    private String SQL_INSERT_STUDENT = "INSERT INTO \"Main\".\"Student\"(\n" +
            "\t name, birthdate, sex, id_group, email)\n" +
            "\tVALUES (?, ?, ?, ?,?);";

    public List<Student> getAllStudents(){
        List<Student> studentsList = new ArrayList<>();
        try(Connection connection = AcademConnector.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_ALL_STUDENTS);

            while(resultSet.next()) {
                logger.debug(resultSet.getString("name"));

                Student student = new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("birthdate"),
                        resultSet.getString("sex"),
                        resultSet.getInt("id_group"),
                        resultSet.getString("email")
                );
                studentsList.add(student);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return studentsList;
    }

    public int deleteStudent(int id) {
        int count = 0;
        try(Connection connection = AcademConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_STUDENT)) {
            preparedStatement.setInt(1, id);

            count = preparedStatement.executeUpdate();
            logger.debug(id+" student was deleted");
        } catch (SQLException e) {
            logger.error(e);
        }
        return count;
    }

    public int updateStudent(Student student){

        int count = 0;
        try(Connection connection = AcademConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_STUDENT)) {
            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setDate(3, new Date(student.getBirthdate().getTime()));
            preparedStatement.setString(4, student.getSex());
            preparedStatement.setInt(5, student.getIdGroup());
            preparedStatement.setInt(6, student.getId());
            preparedStatement.setString(7, student.getEmail());

            count = preparedStatement.executeUpdate();
            logger.debug(student.getId()+" student was update"+student.getIdGroup());
        } catch (SQLException e) {
            logger.error(e);
        }
        return count;
    }

    public int insertStudent(Student student){

        int count = 0;
        try(Connection connection = AcademConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_STUDENT)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setDate(2, new Date(student.getBirthdate().getTime()));
            preparedStatement.setString(3, student.getSex());
            preparedStatement.setInt(4, student.getIdGroup());
            preparedStatement.setString(5, student.getEmail());

            count = preparedStatement.executeUpdate();
            logger.debug(student.getId()+" student was insert"+student.getIdGroup());
        } catch (SQLException e) {
            logger.error(e);
        }
        return count;
    }

    public Student getStudentById(int id) throws UserDAOException {

        logger.debug(id);
        Student student = null;
        try(Connection connection = AcademConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_STUDENT)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                logger.debug("find"+id);
                student = new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("birthdate"),
                        resultSet.getString("sex"),
                        resultSet.getInt("id_group"),
                        resultSet.getString("email"));
            }else{
                logger.debug(id+" not found");
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new UserDAOException();
        }
        return student;
    }

    public List<Student> getStudentsByGroup(int groupid){
        List<Student> studentsList = new ArrayList<>();
        try(Connection connection = AcademConnector.getConnection()){
            PreparedStatement preparedStatement= connection.prepareStatement(SQL_STUDENTS_GROUP);
            preparedStatement.setInt(1,groupid);
            ResultSet resultSet = preparedStatement.executeQuery();


            while(resultSet.next()) {
                logger.debug(resultSet.getString("name"));

                Student student = new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("birthdate"),
                        resultSet.getString("sex"),
                        resultSet.getInt("id_group"),
                        resultSet.getString("email")
                );
                studentsList.add(student);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return studentsList;
    }
}
