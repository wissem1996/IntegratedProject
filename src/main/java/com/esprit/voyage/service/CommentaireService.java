package com.esprit.voyage.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.esprit.voyage.entity.Client;
import com.esprit.voyage.entity.Commentaire;
import com.esprit.voyage.entity.Like;
import com.esprit.voyage.entity.Publication;
import com.esprit.voyage.entity.WrongWords;
import com.esprit.voyage.repository.CommentaireRepository;
import com.esprit.voyage.repository.LikeRepository;
import com.esprit.voyage.repository.PublicationRepository;
import com.esprit.voyage.security.services.UserDetailsImpl;

@Service
public class CommentaireService {

	@Autowired
	private CommentaireRepository commentaireRepository;

	@Autowired
	private PublicationRepository publicationRepository;

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private ClientService clientservice;

	@Autowired
	private WrongWordsService wrongWordsService;

	public String addCommentaire(int id, Commentaire commentaire) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		int idClient = privilge.getId();
		Client client = clientservice.findById(idClient);

		String contenu = commentaire.getContenu();

		if (!contenu.isEmpty() && !checkWrongWords(contenu)) {

			return " Comment Contains wrong word ";

		}

		commentaire.setDateDeCreation(new Date());
		commentaire.setAuteur(client);

		Publication publication = publicationRepository.findById(id).get();
		publication.setNombreComments(publication.getNombreComments() + 1);

		commentaire.setPublicationId(publication);

		commentaireRepository.save(commentaire);
		return "Comment saved";
	}

	public String deleteCommentaire(int id) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		int idClient = privilge.getId();
		Client client = clientservice.findById(idClient);

		Commentaire commentaire = commentaireRepository.findById(id).get();
		Client auteur = commentaire.getAuteur();
		if (auteur.getId() == client.getId()) {
			commentaireRepository.deleteById(id);
			
			Publication publication = commentaire.getPublicationId();
			publication.setNombreComments(publication.getNombreComments() - 1);

			publicationRepository.save(publication);
			
			return "Comment Deleted";

		} else
			return "Permission denied";
	}

	public String updateCommentaire(int id, Commentaire newCommentaire) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		int idClient = privilge.getId();
		Client client = clientservice.findById(idClient);

		String contenu = newCommentaire.getContenu();

		if (!contenu.isEmpty() && !checkWrongWords(contenu)) {

			return "New Comment Contains wrong word ";

		}

		Commentaire commentaire = commentaireRepository.findById(id).get();
		Client auteur = commentaire.getAuteur();
		if (auteur.getId() == client.getId()) {
			commentaire.setDateDeModification(new Date());
			commentaire.setContenu(newCommentaire.getContenu());

			commentaireRepository.save(commentaire);
			return "Comment Updated";
		} else
			return "Comment not Updated , you are not the author";

	}

	public List<Commentaire> getAllCommentaire() {
		List<Commentaire> Commentaires = commentaireRepository.findAll();
		return Commentaires;
	}

	public String likeCommentaire(int id) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		int idClient = privilge.getId();
		Client client = clientservice.findById(idClient);

		Commentaire commentaire = commentaireRepository.findById(id).get();
		Boolean likeExist = likeRepository.existsByUserIdAndCommentId(client.getId(), commentaire.getIdentifiant());

		if (likeExist) {

			return "Alrady Liked";

		} else {

			Like newCommentLike = new Like();
			newCommentLike.setCommentId(id);
			newCommentLike.setUserId(client.getId());
			newCommentLike.setLiked(true);

			likeRepository.save(newCommentLike);

			commentaire.setNombreLike(commentaire.getNombreLike() + 1);
			commentaireRepository.save(commentaire);

			return "Liked";

		}

	}

	public String dislikeCommentaire(int id) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		int idClient = privilge.getId();
		Client client = clientservice.findById(idClient);

		Commentaire commentaire = commentaireRepository.findById(id).get();
		Boolean likeExist = likeRepository.existsByUserIdAndCommentId(client.getId(), commentaire.getIdentifiant());

		if (likeExist) {

			likeRepository.deleteByUserIdAndCommentId(client.getId(), commentaire.getIdentifiant());

			commentaire.setNombreLike(commentaire.getNombreLike() - 1);
			commentaireRepository.save(commentaire);

			return " DisLiked";

		} else {

			return "Alrady DisLiked";

		}

	}

	private boolean checkWrongWords(String contenu) {

		List<WrongWords> wrongWords = wrongWordsService.getAllWrongWords();
		Iterator<?> iterator = wrongWords.iterator();
		while (iterator.hasNext()) {
			WrongWords wrongWord = (WrongWords) iterator.next();
			if (contenu.contains(wrongWord.getWord())) {
				return false;
			}
		}

		return true;

	}

}
