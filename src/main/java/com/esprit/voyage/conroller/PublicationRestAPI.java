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

import com.esprit.voyage.entity.Publication;
import com.esprit.voyage.service.PublicationService;

@RestController
@RequestMapping(value = "/api/publications")
public class PublicationRestAPI {

	@Autowired
	private PublicationService publicationService;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Publication>> getPublications() {
		return new ResponseEntity<>(publicationService.mesPublication(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Publication> getPublication(@PathVariable(value = "id") int id) {
		return new ResponseEntity<>(publicationService.getPublication(id), HttpStatus.OK);
	}

	@PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> createPublication(@PathVariable(value = "id") int id,
			@RequestBody Publication publication) throws Exception {
		return new ResponseEntity<>(publicationService.addPublication(id, publication), HttpStatus.OK);

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deletePublication(@PathVariable(value = "id") int id) {
		return new ResponseEntity<>(publicationService.deletePublication(id), HttpStatus.OK);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> updatePublication(@PathVariable(value = "id") int id,
			@RequestBody Publication publication) {
		return new ResponseEntity<>(publicationService.updatePublication(id, publication), HttpStatus.OK);
	}

	@PostMapping(value = "/like/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> like(@PathVariable(value = "id") int id) throws Exception {

		return new ResponseEntity<>(publicationService.likePublication(id), HttpStatus.OK);
	}

	@PostMapping(value = "/dislike/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> dislike(@PathVariable(value = "id") int id) throws Exception {

		return new ResponseEntity<>(publicationService.dislikePublication(id), HttpStatus.OK);
	}

}
