package com.esprit.voyage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.voyage.entity.Voyage;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Integer> {
	@Query(value = "SELECT * FROM model_voyage u WHERE u.lieu_arrivee = ?1 and date_depart > NOW()", nativeQuery = true)
	List<Voyage> listVoyage(String destination);
	
	
	@Query(value = "SELECT COUNT(*) FROM model_voyage  where lieu_depart = ?1 and date_depart > NOW()", nativeQuery = true)
	int statVoyage(String key );

	@Query(value = "SELECT * FROM model_voyage  WHERE lieu_depart = ?1 and date_depart > NOW()", nativeQuery = true)
	List<Voyage> listVoyageDepart(String depart);
	@Query(value = "SELECT * FROM model_voyage  WHERE compagnie = ?1 and date_depart > NOW()", nativeQuery = true)
	List<Voyage> listVoyageByCompagnie(String compagnie);
	
	@Query(value = "SELECT COUNT(*) FROM model_voyage  where lieu_depart = ?1 and date_depart > NOW()", nativeQuery = true)
	int statVoyageDepart(String key );
}





