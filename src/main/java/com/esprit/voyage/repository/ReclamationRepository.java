package com.esprit.voyage.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.voyage.entity.Reclamation;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Integer> {

	@Query(value = "SELECT * FROM model_reclamation u WHERE u.assignee_id = ?1", 
		       nativeQuery = true)
		List<Reclamation> findReclamationByUser(int id);

	@Query(value = "SELECT * FROM model_reclamation u WHERE u.status = ?1", 
		       nativeQuery = true)
	List<Reclamation> findOpenReclamation(String status);

}
