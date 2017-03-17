package models.userdetail;

import common.exceptions.UserDAOException;
import models.dao.UserDAO;
import models.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Khayrutdinov Marat
 *         Email: mail@wtiger.org
 *         Created on 13.03.2017.
 */
@Component("userDetail")
public class UserDetail implements UserDetailsService {
    Logger logger = Logger.getLogger(UserDetail.class);
    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        try {
            User user = userDAO.getUserByLogin(username);
            List<GrantedAuthority> list = new ArrayList<>();
            list.add(new SimpleGrantedAuthority(user.getRole()));
            userDetails =
                    new org.springframework.security.core.userdetails.User(username,
                            user.getPassword(), list);
        } catch (UserDAOException e) {
            logger.error("Ошибка получения пользователя от dao", e);
        }

        return userDetails;
    }


}
