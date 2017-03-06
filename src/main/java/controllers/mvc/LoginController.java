package controllers.mvc;

import common.exceptions.UserDAOException;
import models.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import services.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by olymp on 06.03.2017.
 */
@Controller
public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class);
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLoginPage(Model model,
                                @RequestParam(name = "login", defaultValue = "") String login,
                                @RequestParam(name = "password", defaultValue = "") String password,
                                HttpSession session,
                                HttpServletResponse resp){
        try {
            User user = userService.authorise(login,password);
            if (user!=null&&user.getId()!=0) {
                session.setAttribute("id",user.getId());
                logger.trace("auth");
                resp.sendRedirect("/students/list");
            } else {
                logger.trace("noauth");
            }
        } catch (UserDAOException e) {
            logger.error(e);
            try {
                resp.sendRedirect("/students/error.jsp");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "login";
    }
}
