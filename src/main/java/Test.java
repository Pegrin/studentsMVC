import common.exceptions.UserDAOException;
import models.connector.AcademConnector;
import models.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by smoldyrev on 23.02.17.
 */
public class Test {
    @Autowired
    private UserDAO userDAO;
    public static void main(String[] args) {

//        Connection con = AcademConnector.getConnection();
//        try {
//            userDAO.registrationUser("loginww", "1","wwww");
//        } catch (UserDAOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            PreparedStatement ps = con.prepareStatement("SELECT * FROM \"Main\".\"User\"");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
