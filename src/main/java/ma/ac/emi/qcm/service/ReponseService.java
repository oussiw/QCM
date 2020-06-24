package ma.ac.emi.qcm.service;

import ma.ac.emi.qcm.entities.Question;
import ma.ac.emi.qcm.entities.Reponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReponseService {
    Reponse afficherReponse(Long id);
    List<Reponse> afficherListReponse();
    Page<Reponse> listReponseForQuestion(Long idQuestion, int page, int size);
    void ajouterReponse(Reponse reponse);
    void supprimerReponse(Long reponseId);
    Question getQuestion(Long id);
}
