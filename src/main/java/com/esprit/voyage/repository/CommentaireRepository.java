package com.esprit.voyage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esprit.voyage.entity.Commentaire;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {

	Commentaire findByIdentifiant(String paramString);

}
