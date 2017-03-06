package controllers.mvc;

import models.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import services.StudentService;

import java.util.List;

/**
 * Created by olymp on 06.03.2017.
 */
@Controller
public class ListController {
    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getListPage(Model model){
        List<Student> studentsList= studentService.getAllStudents();
        model.addAttribute("studentList", studentsList);
        return "list";
    }

}
