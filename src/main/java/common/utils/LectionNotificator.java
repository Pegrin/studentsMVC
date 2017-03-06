package common.utils;

import controllers.ListServlet;
import models.pojo.Lection;
import models.pojo.Student;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import services.StudentService;

import java.util.List;


/**
 * Created by smoldyrev on 24.02.17.
 */
@Controller
public class LectionNotificator {
    @Autowired
    public static void setStudentService(StudentService studentService) {
        LectionNotificator.studentService = studentService;
    }

    private static StudentService studentService;
    private static Logger logger = Logger.getLogger(LectionNotificator.class);

    public static void notifyByLection(Lection lection){
        List<Student> students =
                studentService.getStudentsByGroupId(lection.getGroupid());
        logger.trace(students.size() + "founded");
        for (Student student:
             students) {
            Mailer.sendMail(student.getEmail(),"lection is neared", lection.getSubject());
        }
    }
}
