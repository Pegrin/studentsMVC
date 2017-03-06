package controllers;

import models.pojo.Lection;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.LectionService;
import services.StudentService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smoldyrev on 24.02.17.
 */
@WebServlet("/deleteLection")
@Controller
public class DeleteLectionServlet extends HttpServlet {
    private LectionService lectionService;

    @Autowired
    public void setLectionService(LectionService lectionService) {
        this.lectionService = lectionService;
    }

    private static Logger logger = Logger.getLogger(DeleteLectionServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (lectionService.deleteLectioOnId(Integer.parseInt(id)) > 0) {
            logger.trace(req.getParameter("id") + " was deleted");
            resp.sendRedirect("/students/lections");
        } else {
            logger.trace(id + " not deleted");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
