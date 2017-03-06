package services;

import common.exceptions.UserDAOException;
import models.dao.UserDAO;
import models.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * Created by smoldyrev on 23.02.17.
 */
@Service
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
//@Scope(value="session")
//@Component=@Service
public class UserService {
    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

//    public static boolean authorise(String login, String password) throws UserDAOException {
//        if(UserDAO.getUserByLoginAndPassword(login, password) != null){
//            return true;
//        }
//        return false;
//    }

    public User authorise(String login, String password) throws UserDAOException {
        return userDAO.getUserByLoginAndPassword(login, password);
    }


    public boolean registration(String login, String password, String email) throws UserDAOException {
        return userDAO.registrationUser(login, password, email);
    }

}
