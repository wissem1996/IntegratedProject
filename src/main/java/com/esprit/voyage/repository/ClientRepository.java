package com.esprit.voyage.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.voyage.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
	@Query(value = "SELECT * FROM model_client u WHERE u.username = ?1", nativeQuery = true)
	Client findDistinctLastName(String firstName);

	@Query(value = "SELECT * FROM model_client u WHERE u.id = ?1", nativeQuery = true)
	Client findClientByID(int id);

	@Query(value = "SELECT * FROM model_client u WHERE u.nb_reclamation = 5", nativeQuery = true)
	List<Client> findClientAlert();

	@Query(value = "SELECT * FROM model_client u WHERE u.role = ?1", nativeQuery = true)
	Client findValidator(String role);

	Optional<Client> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	
}
