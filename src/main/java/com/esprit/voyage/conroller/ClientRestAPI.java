package com.esprit.voyage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.voyage.entity.Client;
import com.esprit.voyage.entity.Statistique;
import com.esprit.voyage.repository.ClientRepository;
import com.esprit.voyage.service.ClientService;

@RestController
@RequestMapping(value = "/api/clients")
public class ClientRestAPI {

	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ClientRepository clientRep;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Client> createClient(@RequestBody Client client) {
		return new ResponseEntity<>(clientRep.save(client), HttpStatus.OK);
	}

//	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseStatus(HttpStatus.OK)
//	public ResponseEntity<Client> updateClient(@PathVariable(value = "id") int id, @RequestBody String client) {
//		return new ResponseEntity<>(clientService.updateClientNbReclamation(id, client), HttpStatus.OK);
//	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteClient(@PathVariable(value = "id") int id) {
		return new ResponseEntity<>(clientService.deleteClient(id), HttpStatus.OK);
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Client>> getClients() {
		return new ResponseEntity<>(clientService.getAll(), HttpStatus.OK);
	}
	@DeleteMapping(value = "/annuller/{idUser}/{idEvent}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> annulerParticipation(@PathVariable(value = "idUser") int idUser,@PathVariable(value = "idEvent") int idEvent) {
		return new ResponseEntity<>(clientService.annulerParticipation(idUser,idEvent), HttpStatus.OK);
	}
	
	@GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Statistique>> getStats() {
		return new ResponseEntity<>(clientService.mostMonthUserCreation(), HttpStatus.OK);
	}
	
	
}
