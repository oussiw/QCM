package ma.ac.emi.qcm.service;

import ma.ac.emi.qcm.entities.QCM;
import ma.ac.emi.qcm.repository.QCMRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QCMService {

	@Autowired
	private QCMRepository qcmRepository;

	public List<QCM> getListQcm() {
		return qcmRepository.findAll();
	}

	public QCM getQcmById(long id) {
		return qcmRepository.getQcmById(id);
	}

	public void saveQcm(QCM qcm) {
		qcmRepository.save(qcm);
	}

	public void deleteQcm(long id) {
		qcmRepository.deleteById(id);
	}

}
