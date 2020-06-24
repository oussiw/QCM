package ma.ac.emi.qcm.service;


import ma.ac.emi.qcm.entities.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionService {

	public List<Formation> getFormations() {
        return null;
    }

    public List<Niveau> getNiveauxByFormation(Long idformation) {
        return null;
    }

    public List<Matiere> getMatiereByNiveau(Long idNiveau) {
        return null;
    }

    public List<QCM> getQcmByMatiere(Long idMatiere) {
        return null;
    }

    public List<Question> getQuestionsBy() {
        return null;
    }
}
