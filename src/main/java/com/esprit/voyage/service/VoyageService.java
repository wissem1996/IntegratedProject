
package com.esprit.voyage.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.esprit.voyage.entity.*;
import com.esprit.voyage.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
			if((newVoyage.getDateDepart()!=null))
			existingVoyage.setDateDepart(newVoyage.getDateDepart());
			if((newVoyage.getDateArrivee()!=null))
			existingVoyage.setDateArrivee(newVoyage.getDateArrivee());
			if((newVoyage.getLieuDepart()!=null))
			existingVoyage.setLieuDepart(newVoyage.getLieuDepart());
			if((newVoyage.getLieuArrivee()!=null))
			existingVoyage.setLieuArrivee(newVoyage.getLieuArrivee());
			if((newVoyage.getDescription()!=null))
			existingVoyage.setDescription(newVoyage.getDescription());
			if((newVoyage.getObjectif()!=null))
			existingVoyage.setObjectif(newVoyage.getObjectif());
			if((newVoyage.getCompagnie()!=null))
			existingVoyage.setCompagnie(newVoyage.getCompagnie());
			if((newVoyage.getTarif()!=0.0))
			existingVoyage.setTarif(newVoyage.getTarif());
			return voyageRepository.save(existingVoyage);
		} else
			return null;
	}

	public String deleteVoyage(int id) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		List<String> authorities = privilge.getAuthorities().stream().map(role -> new String(role.getAuthority()))
				.collect(Collectors.toList());
		for (String role : authorities) {
			if (role.equals(ERole.ROLE_ADMIN.toString())||role.equals(ERole.ROLE_MODERATOR.toString())) {

				if (voyageRepository.findById(id).isPresent()) {
					voyageRepository.deleteById(id);
					return "Voyage supprimé";
				} else
					return "Voyage non supprimé :  Voyage not found";

			}
			else return "The user " + privilge.getUsername() + " does't have the right to delete a voyage";
		}
		return null;

	}

	private void sendMailVoyage(Voyage voyage) throws Exception {

		// Create a Simple MailMessage.
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(MyConstants.MY_EMAIL);
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
	public List<Voyage> filter(String key , String value) {
		StringBuilder str = new StringBuilder();
		int nb=0;
		if (key.equals("lieuDepart")) {
			List<Voyage> voyages =voyageRepository.listVoyageDepart(value);
			return voyages;
		}
		else if (key.equals("lieuArrivee")) {
			List<Voyage> voyages = voyageRepository.listVoyage(value);
			return voyages;
      	}
		else if (key.equals("compagnie")) {
			List<Voyage> voyages = voyageRepository.listVoyageByCompagnie(value);
			return voyages;
		}
		return null;
	}
	
	
	
	
	
}
