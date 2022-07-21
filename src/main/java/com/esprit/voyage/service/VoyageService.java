
package com.esprit.voyage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.esprit.voyage.entity.Client;
import com.esprit.voyage.entity.MyConstants;
import com.esprit.voyage.entity.Reclamation;
import com.esprit.voyage.entity.Voyage;
import com.esprit.voyage.repository.VoyageRepository;

@Service
public class VoyageService {
	@Autowired
	private VoyageRepository voyageRepository;

	@Autowired
	public JavaMailSender emailSender;

	public Voyage addVoyage(Voyage voyage) {

		try {
			sendMailVoyage(voyage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return voyageRepository.save(voyage);

	}

	public Voyage updateVoyage(int id, Voyage newVoyage) {
		if (voyageRepository.findById(id).isPresent()) {
			Voyage existingVoyage = voyageRepository.findById(id).get();
			existingVoyage.setDateDepart(newVoyage.getDateDepart());
			existingVoyage.setDateArrivee(newVoyage.getDateArrivee());
			existingVoyage.setLieuDepart(newVoyage.getLieuDepart());
			existingVoyage.setLieuArrivee(newVoyage.getLieuArrivee());
			existingVoyage.setDescription(newVoyage.getDescription());
			existingVoyage.setObjectif(newVoyage.getObjectif());
			existingVoyage.setCompagnie(newVoyage.getCompagnie());
			existingVoyage.setTarif(newVoyage.getTarif());
			return voyageRepository.save(existingVoyage);
		} else
			return null;
	}

	public String deleteVoyage(int id) {
		if (voyageRepository.findById(id).isPresent()) {
			voyageRepository.deleteById(id);
			return "Voyage supprimé";
		} else
			return "Voyage non supprimé";
	}

	private void sendMailVoyage(Voyage voyage) throws Exception {

		// Create a Simple MailMessage.
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(MyConstants.FRIEND_EMAIL);
		message.setSubject("Voyage");
		message.setText("Le Voyage de  : " + voyage.getLieuDepart() + " vers " + voyage.getLieuArrivee() + "\n"
				+ "est confirmé le  : " + voyage.getDateDepart());

		// Send Message!
		this.emailSender.send(message);

	}

	public List<Voyage> getAll() {
		List<Voyage> Voyages = voyageRepository.findAll();
		return Voyages;
	}
	
	public List<Voyage> VoyageParDestination(String destination) {
		List<Voyage> voyages = voyageRepository.listVoyage(destination);
		return voyages;
		
	}

	
	public String getStatVoyage(String key , String value) {
		StringBuilder str = new StringBuilder();
		int nb=0;
		if (key.equals("lieuDepart")) {
			nb=voyageRepository.statVoyageDepart(value);
			str.append("le nombre de voyages disponible  allant a partir de  " +value +"    est :" +nb+"\n"  );

		}
			else if (key.equals("lieuArrivee")) {
			 nb = voyageRepository.statVoyage(value);
				str.append("le nombre de voyages disponible  allant vers " +value +   "   est :     " +nb+"\n"  );


			
		}
		return str.toString() ;
	}
	
	
	
	
	
	
}
