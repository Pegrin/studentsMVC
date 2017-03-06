package models.connector;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by smoldyrev on 23.02.17.
 */
public class AcademConnector {

    private static Logger logger = Logger.getLogger(AcademConnector.class);

    private static Connection connection;

    private static AcademConnector ourInstance = new AcademConnector();

    public static AcademConnector getInstance() {
        return ourInstance;
    }

    private AcademConnector() {
    }

    /**Возвращает подключение к БД
     * @return connection
     */
    public static Connection getConnection() {

        try {
            if (connection!=null) connection.close();
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/students";
            connection = DriverManager.getConnection(url, "postgres", "123456");

            logger.trace("Successful connect to base: "+url);
        } catch (ClassNotFoundException e) {
            logger.error(e);
            System.out.println("Не найден драйвер");
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Ошибка подключение к БД!");
        }
        return connection;
    }

}
