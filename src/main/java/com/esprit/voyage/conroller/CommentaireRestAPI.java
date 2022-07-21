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

import com.esprit.voyage.entity.Commentaire;
import com.esprit.voyage.service.CommentaireService;

@RestController
@RequestMapping(value = "/api/commentaires")
public class CommentaireRestAPI {

	@Autowired
	private CommentaireService commentaireService;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Commentaire>> getCommentaires() {
		return new ResponseEntity<>(commentaireService.getAllCommentaire(), HttpStatus.OK);
	}

	@PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> createCommentaire(@PathVariable(value = "id") int id,
			@RequestBody Commentaire commentaire) throws Exception {
		return new ResponseEntity<>(commentaireService.addCommentaire(id, commentaire), HttpStatus.OK);

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteCommentaire(@PathVariable(value = "id") int id) {
		return new ResponseEntity<>(commentaireService.deleteCommentaire(id), HttpStatus.OK);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> updateCommentaire(@PathVariable(value = "id") int id,
			@RequestBody Commentaire commentaire) {
		return new ResponseEntity<>(commentaireService.updateCommentaire(id, commentaire), HttpStatus.OK);
	}

	@PostMapping(value = "/like/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> like(@PathVariable(value = "id") int id) throws Exception {

		return new ResponseEntity<>(commentaireService.likeCommentaire(id), HttpStatus.OK);
	}

	@PostMapping(value = "/dislike/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> dislike(@PathVariable(value = "id") int id) throws Exception {

		return new ResponseEntity<>(commentaireService.dislikeCommentaire(id), HttpStatus.OK);
	}

}
