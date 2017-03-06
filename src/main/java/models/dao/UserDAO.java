package models.dao;

import common.exceptions.UserDAOException;
import models.connector.AcademConnector;
import models.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by smoldyrev on 23.02.17.
 */
@Repository
public class UserDAO {

    private static Logger logger = Logger.getLogger(UserDAO.class);

    private static final String SQL_FIND_USER = "SELECT * FROM \"Main\".\"User\"\n" +
            "WHERE login = ? AND password = ?";

    private static final String SQL_CREATE_USER = "INSERT INTO \"Main\".\"User\"(login, password, role, email) VALUES (?,?,?,?)";
    private static final String SQL_USER_ID = "SELECT * FROM \"Main\".\"User\"\n" +
            "WHERE id = ?";

    public User getUserByLoginAndPassword(String login, String password) throws UserDAOException {

        logger.debug(login + " " + password);
        User user = null;
        try(Connection connection = AcademConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                logger.debug("find");
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getString("email")
                );
            }else{
                logger.debug(login + " " + password + " not found");
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new UserDAOException();
        }
        return user;
    }

    public boolean registrationUser(String login, String password, String email) throws UserDAOException {
        try(Connection connection = AcademConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_USER)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, "");
            preparedStatement.setString(4, email);
            int count = preparedStatement.executeUpdate();
            if(count > 0){
                logger.debug("inserted " + count);
                return true;
            }else{
                logger.debug(login + " " + password + " not inserted");
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    public static User getUserById(int id) throws UserDAOException {

        User user = null;
        try(Connection connection = AcademConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_USER_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                logger.debug("find");
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getString("email")
                );
            }else{
                logger.debug(id + " not found");
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new UserDAOException();
        }
        return user;
    }
}
