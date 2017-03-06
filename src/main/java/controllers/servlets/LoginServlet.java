package controllers.servlets;

import common.exceptions.UserDAOException;
import models.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smoldyrev on 23.02.17.
 */
//@WebServlet("/login")
@Controller
public class LoginServlet extends HttpServlet {
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    private static Logger logger = Logger.getLogger(LoginServlet.class);
    static {
//        PropertyConfigurator.configure("src/main/resources/log4j.xml");
       // DOMConfigurator.configure("log4j.xml");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/students/login.jsp").forward(req,resp);
          req.getRequestDispatcher("/login.jsp").forward(req,resp);
          logger.trace("loginGet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            User user = userService.authorise(login,password);
            if (user.getId()!=0) {
                req.getSession().setAttribute("id",user.getId());
                logger.trace("auth");
                resp.sendRedirect("/students/list");
            } else {
                logger.trace("noauth");
                req.getRequestDispatcher("/login.jsp").forward(req,resp);
            }
        } catch (UserDAOException e) {
            logger.error(e);
            resp.sendRedirect("/students/error.jsp");
        }
    }
}
