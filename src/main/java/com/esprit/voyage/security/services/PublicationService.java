package com.esprit.voyage.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.esprit.voyage.entity.Client;
import com.esprit.voyage.entity.Commentaire;
import com.esprit.voyage.entity.ERole;
import com.esprit.voyage.entity.Like;
import com.esprit.voyage.entity.Publication;
import com.esprit.voyage.entity.Reclamation;
import com.esprit.voyage.entity.Voyage;
import com.esprit.voyage.entity.WrongWords;
import com.esprit.voyage.repository.LikeRepository;
import com.esprit.voyage.repository.PublicationRepository;
import com.esprit.voyage.repository.VoyageRepository;
import com.esprit.voyage.security.services.UserDetailsImpl;

@Service
public class PublicationService {

	@Autowired
	private PublicationRepository publicationRepository;

	@Autowired
	private ClientService clientservice;
	
	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private VoyageRepository voyageRepository;

	@Autowired
	private WrongWordsService wrongWordsService;

	public String addPublication(int id, Publication publication) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		int idClient = privilge.getId();
		Client client = clientservice.findById(idClient);

		String contenu = publication.getContenu();

		if (!contenu.isEmpty() && !checkWrongWords(contenu)) {

			return " Publication Contains wrong word ";

		}

		Voyage voyage = voyageRepository.findById(id).get();

		publication.setDateDeCreation(new Date());
		publication.setAuteur(client);
		publication.setVoyageId(voyage);

		publicationRepository.save(publication);

		return "Post created";
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

	public String deletePublication(int id) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		int idClient = privilge.getId();
		Client client = clientservice.findById(idClient);

		Publication publication = publicationRepository.findById(id).get();

		Client auteur = publication.getAuteur();

		if (auteur.getId() == client.getId()) {

			publicationRepository.deleteById(id);
			return "Post Deleted";

		}
		return "Permission denied";

	}

	public String updatePublication(int id, Publication newPublication) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		int idClient = privilge.getId();
		Client client = clientservice.findById(idClient);

		String contenu = newPublication.getContenu();

		if (!contenu.isEmpty() && !checkWrongWords(contenu)) {

			return "New Post Contains wrong word ";

		}
		Publication publication = publicationRepository.findById(id).get();
		Client auteur = publication.getAuteur();
		if (auteur.getId() == client.getId()) {

			publication.setDateDeModification(new Date());
			publication.setPubImageUrl(newPublication.getPubImageUrl());
			publication.setContenu(newPublication.getContenu());

			publicationRepository.save(publication);
			return "Post Updated";
		}
		return "Permission denied";

	}

	public List<Publication> getAllPublication() {
		List<Publication> publications = publicationRepository.findAll();
		return publications;
	}
	
	public Publication getPublication(int id) {
		Publication publication = publicationRepository.findById(id).get();
		return publication;
	}

	public List<Publication> mesPublication() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		int idClient = privilge.getId();

		List<Publication> mesPublications = publicationRepository.findPublicationByUser(idClient);
		return mesPublications;

	}

	public String likePublication(int id) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		int idClient = privilge.getId();
		Client client = clientservice.findById(idClient);

		Publication publication = publicationRepository.findById(id).get();
		Boolean likeExist = likeRepository.existsByUserIdAndPostId(client.getId(), publication.getIdentifiant());

		if (likeExist) {

			return "Alrady Liked";

		} else {

			Like newPostLike = new Like();
			newPostLike.setPostId(id);
			newPostLike.setUserId(client.getId());
			newPostLike.setLiked(true);

			likeRepository.save(newPostLike);

			publication.setNombreLike(publication.getNombreLike() + 1);
			publicationRepository.save(publication);

			return "Liked";

		}

	}

	public String dislikePublication(int id) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl privilge = (UserDetailsImpl) authentication.getPrincipal();
		int idClient = privilge.getId();
		Client client = clientservice.findById(idClient);

		Publication publication = publicationRepository.findById(id).get();
		Boolean likeExist = likeRepository.existsByUserIdAndPostId(client.getId(), publication.getIdentifiant());

		if (likeExist) {

			likeRepository.deleteByUserIdAndPostId(client.getId(), publication.getIdentifiant());

			publication.setNombreLike(publication.getNombreLike() - 1);
			publicationRepository.save(publication);

			return " DisLiked";

		} else {

			return "Alrady DisLiked";

		}

	}

}
