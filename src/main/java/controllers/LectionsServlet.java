package controllers;

import models.pojo.Lection;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import services.LectionService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by smoldyrev on 24.02.17.
 */
@WebServlet("/lection")
@Controller
public class LectionsServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(LectionsServlet.class);
    private LectionService lectionService;

    @Autowired
    public void setLectionService(LectionService lectionService) {
        this.lectionService = lectionService;
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Lection> lections= lectionService.getAllLections();
        logger.debug(lections.size());
        req.setAttribute("lections", lections);
        req.getRequestDispatcher("/lections.jsp").forward(req, resp);;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
