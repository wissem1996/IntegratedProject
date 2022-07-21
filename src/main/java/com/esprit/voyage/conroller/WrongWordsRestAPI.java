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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.voyage.entity.WrongWords;
import com.esprit.voyage.service.WrongWordsService;

@RestController
@RequestMapping(value = "/api/wrongWords")
public class WrongWordsRestAPI {

	@Autowired
	private WrongWordsService wrongWordsService;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<WrongWords>> getWrongWords() {
		return new ResponseEntity<>(wrongWordsService.getAllWrongWords(), HttpStatus.OK);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> createWrongWords(@RequestBody WrongWords wrongWords) throws Exception {
		return new ResponseEntity<>(wrongWordsService.addWrongWords(wrongWords), HttpStatus.OK);

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteWrongWords(@PathVariable(value = "id") int id) {
		return new ResponseEntity<>(wrongWordsService.deleteWrongWords(id), HttpStatus.OK);
	}

}
