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

import com.esprit.voyage.entity.Reclamation;
import com.esprit.voyage.service.ReclamationService;

@RestController
@RequestMapping(value = "/api/reclamations")
public class ReclamationRestAPI {

	@Autowired
	private ReclamationService reclamationtService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseEntity> createReclamation(@RequestBody Reclamation reclamation) throws Exception {
		return new ResponseEntity<>(reclamationtService.addReclamation(reclamation), HttpStatus.OK);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Reclamation> updateReclamation(@PathVariable(value = "id") int id, @RequestBody Reclamation reclamation) {
		return new ResponseEntity<>(reclamationtService.updateReclamation(id, reclamation), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteReclamation(@PathVariable(value = "id") int id) {
		return new ResponseEntity<>(reclamationtService.deleteReclamation(id), HttpStatus.OK);
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Reclamation>> getReclamations() {
		return new ResponseEntity<>(reclamationtService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/allOpenReclamation", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Reclamation>> getOpenReclamations() {
		return new ResponseEntity<>(reclamationtService.getAllOpenReclamation(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Reclamation> getReclamationById(@PathVariable(value = "id") int id) {
		return new ResponseEntity<>(reclamationtService.findById(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/affect/{idReclamation}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> affecterReclamation(@PathVariable(value = "idReclamation") int idReclaamtion) throws Exception {
		return new ResponseEntity<>(reclamationtService.affecterReclamation(idReclaamtion), HttpStatus.OK);
	}
	
	@PostMapping(value = "/validate/{idReclaamtion}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> validateReclamation(@PathVariable(value = "idReclaamtion") int idReclaamtion) throws Exception {
		return new ResponseEntity<>(reclamationtService.validateReclamation(idReclaamtion), HttpStatus.OK);
	}
	
	@GetMapping(value = "/mesReclamation", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Reclamation>> getReclamationById() {
		return new ResponseEntity<>(reclamationtService.mesReclamation(), HttpStatus.OK);
	}
	
}
