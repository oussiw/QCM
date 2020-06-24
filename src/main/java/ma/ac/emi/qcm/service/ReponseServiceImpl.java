package ma.ac.emi.qcm.service;


import ma.ac.emi.qcm.entities.Question;
import ma.ac.emi.qcm.entities.Reponse;
import ma.ac.emi.qcm.repository.QuestionRepository;
import ma.ac.emi.qcm.repository.ReponseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReponseServiceImpl implements ReponseService {

    @Autowired
    private ReponseRepository reponseRepository;

    @Autowired
    private QuestionRepository questionRepo;

    @Override
    public Reponse afficherReponse(Long id) {
        Optional<Reponse> opReponse = reponseRepository.findById(id);
        if(!opReponse.isPresent()){
            throw new RuntimeException("Aucune reponse trouvee");
        }
        return opReponse.get();
    }

    @Override
    public List<Reponse> afficherListReponse() {
        return reponseRepository.findAll();
    }

    @Override
    public Page<Reponse> listReponseForQuestion(Long idQuestion, int page, int size) {
        return reponseRepository.listReponses(getQuestion(idQuestion), PageRequest.of(page,size));
    }

    @Override
    public void ajouterReponse(Reponse reponse) {
        reponseRepository.save(reponse);
    }

    @Override
    public void supprimerReponse(Long reponseId) {
        reponseRepository.delete(afficherReponse(reponseId));
    }

    @Override
    public Question getQuestion(Long id) {
        return questionRepo.getOne(id);
    }
}
