package services;

import models.dao.StudentDao;
import models.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by smoldyrev on 23.02.17.
 */
@Service
public class StudentService {
    StudentDao studentDao;

    @Autowired
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Secured("ROLE_ADMIN")
    public List<Student> getAllStudents(){
        return studentDao.getAllStudents();
    }

    public int deleteStudentOnId(int id){

        return studentDao.deleteStudent(id);
    }

    public int updateStudentOnId(Student student){

        return studentDao.updateStudent(student);
    }

    public int insertStudent(Student student){

        return studentDao.insertStudent(student);
    }

    public List<Student> getStudentsByGroupId(int groupid){
        return studentDao.getStudentsByGroup(groupid);
    }

}
