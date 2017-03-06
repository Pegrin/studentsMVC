package services;

import models.dao.LectionDAO;
import models.dao.StudentDao;
import models.pojo.Lection;
import models.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by smoldyrev on 24.02.17.
 */
@Service
public class LectionService {
    private LectionDAO lectionDAO;

    @Autowired
    public void setLectionDAO(LectionDAO lectionDAO) {
        this.lectionDAO = lectionDAO;
    }

    public List<Lection> getAllLections(){
        return lectionDAO.getAllLections();
    }

    public int deleteLectioOnId(int id){

        return lectionDAO.deleteLection(id);
    }

    public int updateLectionOnId(Lection lection){

        return lectionDAO.updateLection(lection);
    }

    public int insertLection(Lection lection){

        return lectionDAO.insertLection(lection);
    }

    public  List<Lection> getNearedLection() {
        return lectionDAO.getNearedLections();
    }
}
