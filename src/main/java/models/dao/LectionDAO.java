package models.dao;

import common.exceptions.UserDAOException;
import models.connector.AcademConnector;
import models.pojo.Lection;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoldyrev on 24.02.17.
 */
@Repository
public class LectionDAO {

    private String SQL_ALL_LECTIONS = "SELECT * FROM \"Main\".\"Lection\"";

    private String SQL_NEARED_LECTIONS = "SELECT * FROM \"Main\".\"Lection\" WHERE date >? AND date <?";

    private String SQL_DELETE_LECTION = "DELETE FROM \"Main\".\"Lection\" WHERE id = ?";

    private String SQL_UPDATE_LECTION = "UPDATE \"Main\".\"Lection\"\n" +
            "\tSET id=?, name=?, subject=?, date=?, groupid=?, textlection=?" +
            "\tWHERE id=?";

    private String SQL_INSERT_LECTION = "INSERT INTO \"Main\".\"Lection\"(\n" +
            "\t name, subject, date, groupid, textlection)\n" +
            "\tVALUES (?, ?, ?, ?,?);";

    private String SQL_FIND_LECTION = "SELECT * FROM \"Main\".\"Lection\" WHERE id =?";


    private static Logger logger = Logger.getLogger(LectionDAO.class);

    public List<Lection> getAllLections(){
        List<Lection> lections = new ArrayList<>();
        try(Connection connection = AcademConnector.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_ALL_LECTIONS);

            while(resultSet.next()) {
                logger.debug(resultSet.getString("name"));

                Lection lection = new Lection(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("subject"),
                        resultSet.getString("textLection"),
                        resultSet.getInt("groupid"),
                        resultSet.getDate("date"));
                lections.add(lection);
            }
        } catch (SQLException e) {
            logger.error(e);
        }

        logger.debug(lections.size());
        return lections;
    }

    public int deleteLection(int id) {
        int count = 0;
        try(Connection connection = AcademConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_LECTION)) {
            preparedStatement.setInt(1, id);

            count = preparedStatement.executeUpdate();
            logger.debug(id+" lection was deleted");
        } catch (SQLException e) {
            logger.error(e);
        }
        return count;
    }

    public int updateLection(Lection lection){

        int count = 0;
        try(Connection connection = AcademConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_LECTION)) {
            preparedStatement.setInt(1, lection.getId());
            preparedStatement.setString(2, lection.getName());
            preparedStatement.setString(3, lection.getSubject());
            preparedStatement.setDate(4, new Date(lection.getDate().getTime()));
            preparedStatement.setInt(5, lection.getGroupid());
            preparedStatement.setString(6, lection.getTextLection());
            preparedStatement.setInt(7, lection.getId());


            count = preparedStatement.executeUpdate();
            logger.debug(lection.getId()+" lection was update "+lection.getGroupid());
        } catch (SQLException e) {
            logger.error(e);
        }
        return count;
    }

    public int insertLection(Lection lection){

        int count = 0;
        try(Connection connection = AcademConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_LECTION)) {

            preparedStatement.setString(1, lection.getName());
            preparedStatement.setString(2, lection.getSubject());
            preparedStatement.setDate(3, new Date(lection.getDate().getTime()));
            preparedStatement.setInt(4, lection.getGroupid());
            preparedStatement.setString(5, lection.getTextLection());

            count = preparedStatement.executeUpdate();
            logger.debug(lection.getId()+" lection was insert"+lection.getTextLection());
        } catch (SQLException e) {
            logger.error(e);
        }
        return count;
    }

    public Lection getLectionById(int id) throws UserDAOException {

        logger.debug(id);
        Lection lection = null;
        try(Connection connection = AcademConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_LECTION)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                logger.debug("find lection"+id);
                lection = new Lection(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("subject"),
                        resultSet.getString("textlection"),
                        resultSet.getInt("groupid"),
                        resultSet.getDate("Date"));
            }else{
                logger.debug(id+" lection not found");
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new UserDAOException();
        }
        return lection;
    }

    public List<Lection> getNearedLections(){
        List<Lection> lections = new ArrayList<>();
        try(Connection connection = AcademConnector.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_NEARED_LECTIONS);
            preparedStatement.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(2,new Timestamp(System.currentTimeMillis()+60*60*1000));
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                logger.debug(resultSet.getString("name"));

                Lection lection = new Lection(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("subject"),
                        resultSet.getString("textLection"),
                        resultSet.getInt("groupid"),
                        resultSet.getDate("date"));
                lections.add(lection);
            }
        } catch (SQLException e) {
            logger.error(e);
        }

        logger.debug(lections.size());
        return lections;
    }
}

