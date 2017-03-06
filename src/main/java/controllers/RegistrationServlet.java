package controllers;

import common.exceptions.UserDAOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smoldyrev on 23.02.17.
 */
@WebServlet("/registration")
@Controller
public class RegistrationServlet extends HttpServlet {
    @Autowired
    private UserService userService;
    private static Logger logger = Logger.getLogger(RegistrationServlet.class);

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("on post");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        try {
            if(userService.registration(login, password,email)){
                logger.trace("true");
                resp.sendRedirect("/students/login");
            }else{
                logger.trace("false");
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
            }
        } catch (UserDAOException e) {
            e.printStackTrace();
        }
    }
}
