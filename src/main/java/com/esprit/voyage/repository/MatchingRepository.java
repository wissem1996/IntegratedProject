package com.esprit.voyage.repository;

import com.esprit.voyage.entity.Client;
import com.esprit.voyage.entity.Matching;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MatchingRepository extends CrudRepository<Matching, Long> {
    @Query("select e from Client e where e.voyageID like :voyageID")
    public List<Client> searchForMatch(@Param("voyageID") long voyageID);

    @Query("select e from Client e where e.id like :idClient")
    public Client getNewMatcher(@Param("idClient") int idClient);
}