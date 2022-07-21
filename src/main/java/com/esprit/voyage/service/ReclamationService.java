package com.esprit.voyage.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.esprit.voyage.entity.Client;
import com.esprit.voyage.entity.ERole;
import com.esprit.voyage.entity.MyConstants;
import com.esprit.voyage.entity.Reclamation;
import com.esprit.voyage.entity.Role;
import com.esprit.voyage.entity.Statistique;
import com.esprit.voyage.entity.Status;
import com.esprit.voyage.repository.ReclamationRepository;
import com.esprit.voyage.security.services.UserDetailsImpl;

@Service
public class ReclamationService {

	@Autowired
	private ReclamationRepository reclamationRepository;

	@Autowired
	private ClientService clientRepository;

	@Autowired
	public JavaMailSender emailSender;

	public ResponseEntity<?> addReclamation(Reclamation reclamation) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		List<String> authorities = privilge.getAuthorities().stream().map(role -> new String(role.getAuthority()))
				.collect(Collectors.toList());
		for (String role : authorities) {
			if (role.equals(ERole.ROLE_USER.toString())) {

				if (reclamation.getStatus() == null) {
					reclamation.setStatus(Status.OPEN);
					clientRepository.updateClientNbReclamation(reclamation.getNomClientReclame());
				}
				reclamation.setDateReclamation(new Date());
				Client client = clientRepository.findByName(privilge.getUsername());
				//sendReclamtionEmail(reclamation, client.getId(), reclamation.getNomClientReclame());
				reclamation.setClient(client);

				reclamation.setAssignee(client);
				reclamation.setLastAssigne(client);
				reclamationRepository.save(reclamation);
				return ResponseEntity.ok(reclamation);

			}
			else return ResponseEntity.badRequest().body("The user " + privilge.getUsername() + " does't have the right to add a new reclamation");
		}
		return null;

	}

	public Reclamation updateReclamation(int id, Reclamation newReclamation) {
		if (reclamationRepository.findById(id).isPresent()) {
			Reclamation existingReclamation = reclamationRepository.findById(id).get();
			//existingReclamation.setStatus(newReclamation.getStatus());
			existingReclamation.setDescription(newReclamation.getDescription());
			return reclamationRepository.save(existingReclamation);
		} else
			return null;
	}

	public String deleteReclamation(int id) {
		if (reclamationRepository.findById(id).isPresent()) {
			reclamationRepository.deleteById(id);
			return "reclamation supprimé";
		} else
			return "reclamation non supprimé";
	}

	public List<Reclamation> getAll() {
		List<Reclamation> reclamations = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		List<String> authorities = privilge.getAuthorities().stream().map(role -> new String(role.getAuthority()))
				.collect(Collectors.toList());
		for (String role : authorities) {
			if (role.equals(ERole.ROLE_ADMIN.toString()) || role.equals(ERole.ROLE_EMPLOYEE.toString()) || role.equals(ERole.ROLE_VALIDATOR.toString())) {
			reclamations = reclamationRepository.findAll();
			}
		}
		//Statistique test = clientRepository.mostMonthUserCreation();
		//System.out.println("Le mois " + test.getMonth() + " est le mois ou plus d'utilisateur sont creer avec un nombres de " + test.getNbuser() + " utilisateur");
		return reclamations;
	}

	public List<Reclamation> getAllOpenReclamation() {
		List<Reclamation> reclamations = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		List<String> authorities = privilge.getAuthorities().stream().map(role -> new String(role.getAuthority()))
				.collect(Collectors.toList());
		for (String role : authorities) {
			if (role.equals(ERole.ROLE_ADMIN.toString()) || role.equals(ERole.ROLE_EMPLOYEE.toString()) || role.equals(ERole.ROLE_VALIDATOR.toString())) {
			reclamations = reclamationRepository.findOpenReclamation(Status.OPEN.name());
			}
		}
		
		return reclamations;
	}
	private void sendReclamtionEmail(Reclamation reclamation, int id, String name) throws Exception {

		Client existingClient = clientRepository.findById(id);
		Client existingClient1 = clientRepository.findByName(name);
		if (existingClient == null) {
			throw new Exception("client introuvable");
		}
		if (existingClient1 == null) {
			throw new Exception("client introuvable");
		}
		// Create a Simple MailMessage.
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(MyConstants.FRIEND_EMAIL);
		message.setSubject("Reclamtion");
		message.setText("Hello, Im testing Simple Email");
		message.setText("Réclamateur : " + existingClient.getName() + " " + existingClient.getusername() + "\n"
				+ "Personne reclame : " + existingClient1.getName() + " " + existingClient1.getusername() + "\n"
				+ "Description : " + reclamation.getDescription() + "\n" + "Status : " + reclamation.getStatus());

		// Send Message!
		this.emailSender.send(message);

	}

	public Reclamation findById(int id) {
		Reclamation existingReclamation = null;
		if (reclamationRepository.findById(id).isPresent()) {
			existingReclamation = reclamationRepository.findById(id).get();
		}
		return existingReclamation;
	}

	@Scheduled(fixedRate = 60000)
	public void disabledUser() {
		List<Client> clinets = clientRepository.findByNbReclamtion();
		Iterator<Client> it = clinets.iterator();
		while (it.hasNext()) {
			Client cli = (Client) it.next();
			if (!cli.getIsDisabled()) {
				notifDisabledclient(cli);
				cli.setIsDisabled(Boolean.TRUE);
				clientRepository.addClient(cli);
			}
		}
	}

	private void notifDisabledclient(Client client) {

		// Create a Simple MailMessage.
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(client.getEmail());
		message.setSubject("Votre compte est temporairement désactivé");
		message.setText("Bonjour Mr/Mme : " + client.getName() + " " + client.getusername() + "\n"
				+ "Votre compte est temporairement désactive suite d'avoir un nombre de réclamation supérieur à 5 ");

		// Send Message!
		this.emailSender.send(message);

	}

	public String affecterReclamation(int idReclamation) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		String message = null;
		Reclamation reclamation = null;
		Client validator = clientRepository.findById(2);
		Client employee = clientRepository.findById(privilge.getId());
		Iterator<Role> itRole = employee.getRoles().iterator();
		while (itRole.hasNext()) {
			Role role = itRole.next();
			if (role.getName().equals(ERole.ROLE_EMPLOYEE)) {
				reclamation = reclamationRepository.findById(idReclamation).get();
				reclamation.setAssignee(validator);
				reclamation.setLastAssigne(employee);
				reclamation.setDateTraitement(new Date());
				reclamation.setStatus(Status.WAITINGVALIDATION);
				reclamationRepository.save(reclamation);
				message = "Reclamation affecté and waiting for validation";

			} else {
				message = "The user : " + employee.getusername() + " " + employee.getName()
						+ " don't have the right to assigne this reclamation ";
			}
		}

		return message;
	}

	public String validateReclamation(int idReclamation) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		Client validator = clientRepository.findById(privilge.getId());
		String message = null;
		Reclamation reclamation = reclamationRepository.findById(idReclamation).get();
		Iterator<Role> itRole = validator.getRoles().iterator();
		while (itRole.hasNext()) {
			Role role = itRole.next();
			if (role.getName().equals(ERole.ROLE_VALIDATOR)) {
				if (reclamation.getStatus().equals(Status.WAITINGVALIDATION)) {
					Client lastAssigne = reclamation.getAssignee();
					reclamation.setAssignee(reclamation.getLastAssignee());
					reclamation.setLastAssigne(lastAssigne);
					reclamation.setStatus(Status.INPROGRESS);
					reclamationRepository.save(reclamation);
					message = "Reclmation validate";
					break;
				} else if (reclamation.getStatus().equals(Status.INPROGRESS)) {
					message = "Reclmation déja validé";
					break;
				}
			} else
				message = "The user " + validator.getName() + " " + validator.getusername()
						+ " doesn't have the right to validate this reclamation with id : " + reclamation.getId();

		}

		return message;
	}

	public List<Reclamation> mesReclamation() {
		List<Reclamation>  mesReclamations = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		Client client = clientRepository.findByName(privilge.getUsername());
		Iterator<Role> itRole = client.getRoles().iterator();
		while (itRole.hasNext()) {
			Role role = itRole.next();
			if (role.getName().equals(ERole.ROLE_VALIDATOR) || role.getName().equals(ERole.ROLE_EMPLOYEE)) {
		    mesReclamations = reclamationRepository.findReclamationByUser(client.getId());
			}}
		
		return mesReclamations;

	}

	private void privilegeUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		List<String> authorities = privilge.getAuthorities().stream().map(role -> new String(role.getAuthority()))
				.collect(Collectors.toList());
		for (String role : authorities) {
			if (role.equals(ERole.ROLE_USER.toString())) {
				System.out.println("ok");
			}
		}
	}
}
