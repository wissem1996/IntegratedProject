package com.esprit.voyage.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.esprit.voyage.entity.Event;
import com.esprit.voyage.entity.Statistique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esprit.voyage.entity.Client;
import com.esprit.voyage.repository.ClientRepository;

@Service
public class ClientService {
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private EventService eventService;

	public Client addClient(Client client) {
		return clientRepository.save(client);
	}

	public Client updateClientNbReclamation(String newClient) {

		// Client existingClient = clientRepository.findById(id).get();
		Client existingClient = clientRepository.findDistinctLastName(newClient);
		if (!(existingClient == null)) {
			existingClient.setNbReclamation(existingClient.getNbReclamation() + 1);
			// existingClient.setStatus(newReclamation.getStatus());

			return clientRepository.save(existingClient);
		} else
			return null;
	}

	public String deleteClient(int id) {
		if (clientRepository.findById(id).isPresent()) {
			clientRepository.deleteById(id);
			return "reclamation supprimé";
		} else
			return "reclamation non supprimé";
	}

	public List<Client> getAll() {
		List<Client> clients = clientRepository.findAll();
		return clients;
	}

	public Client findById(int id) {
		Client existingClient = null;
		if (clientRepository.findById(id).isPresent()) {
			existingClient = clientRepository.findById(id).get();
		}
		return existingClient;
	}

	public Client findByName(String name) {
		Client existingClient = null;
		existingClient = clientRepository.findDistinctLastName(name);
		return existingClient;
	}

	public List<Client> findByNbReclamtion() {
		List<Client> existingClient = null;
		existingClient = clientRepository.findClientAlert();
		return existingClient;
	}

	public Client findByRole(String role) {
		Client existingClient = null;
		existingClient = clientRepository.findValidator(role);
		return existingClient;
	}

	// methode annuler participation fait appel à findByID de service event
	public String annulerParticipation(int idUser, int idEvent) {
		Client user = clientRepository.findById(idUser).get();
		Set events = user.getEvents();
		Event event = eventService.findEventById(idEvent);
		events.remove(event);
		eventService.updateEventObject(event);
		clientRepository.save(user);
		return "participation est annulé";
	}

	public List<Statistique> mostMonthUserCreation() {
		List<Statistique> lst = new ArrayList<Statistique>();
		Date date = new Date();
		date.getMonth();
		int nbUserJanuary = 0;
		int nbUserFebruary = 0;
		int nbUserMarch = 0;
		int nbUserApril = 0;
		int nbUserMay = 0;
		int nbUserJune = 0;
		int nbUserJuly = 0;
		int nbUserAugust = 0;
		int nbUserSeptember = 0;
		int nbUserOctober = 0;
		int nbUserNovember = 0;
		int nbUserDecember = 0;
		List<Client> listesClient = clientRepository.findAll();
		for (Client client : listesClient) {
			if (client.getCreationDate().getMonth() == 0) {
				nbUserJanuary++;
			}
			if (client.getCreationDate().getMonth() == 1) {
				nbUserFebruary++;
			}
			if (client.getCreationDate().getMonth() == 2) {
				nbUserMarch++;
			}
			if (client.getCreationDate().getMonth() == 3) {
				nbUserApril++;
			}
			if (client.getCreationDate().getMonth() == 4) {
				nbUserMay++;
			}
			if (client.getCreationDate().getMonth() == 5) {
				nbUserJune++;
			}
			if (client.getCreationDate().getMonth() == 6) {
				nbUserJuly++;
			}
			if (client.getCreationDate().getMonth() == 7) {
				nbUserAugust++;
			}
			if (client.getCreationDate().getMonth() == 8) {
				nbUserSeptember++;
			}
			if (client.getCreationDate().getMonth() == 9) {
				nbUserOctober++;
			}
			if (client.getCreationDate().getMonth() == 10) {
				nbUserNovember++;
			}
			if (client.getCreationDate().getMonth() == 11) {
				nbUserDecember++;
			}
		}

		Map<String, Integer> newmap = new HashMap<String, Integer>();
		newmap.put("January", nbUserJanuary);
		newmap.put("February", nbUserFebruary);
		newmap.put("March", nbUserMarch);
		newmap.put("April", nbUserApril);
		newmap.put("May", nbUserMay);
		newmap.put("June", nbUserJune);
		newmap.put("July", nbUserJuly);
		newmap.put("August", nbUserAugust);
		newmap.put("September", nbUserSeptember);
		newmap.put("October", nbUserOctober);
		newmap.put("November", nbUserNovember);
		newmap.put("December", nbUserDecember);

		List list = new ArrayList(newmap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				return e1.getValue().compareTo(e2.getValue());
			}
		});
		for(int i=11 ;i>=0;i--) {
		String d = (String) list.get(i).toString();
		Statistique st = new Statistique(d.substring(0, d.indexOf("=")), d.substring(d.indexOf("=") + 1, d.length()));
//		String month = d.substring(0, d.indexOf("="));
//		String nbUser = d.substring(d.indexOf("=") + 1, d.length());
		lst.add(st);
		}
		return lst;
	}
}
