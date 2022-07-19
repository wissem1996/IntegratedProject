package com.esprit.voyage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.voyage.entity.Publication;
import com.esprit.voyage.entity.Reclamation;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Integer> {

	Publication findByIdentifiant(String paramString);

	List<Publication> findAll();
	
	@Query(value = "SELECT * FROM t_publication u WHERE u.auteur_id = ?1", 
		       nativeQuery = true)
		List<Publication> findPublicationByUser(int id);


}
