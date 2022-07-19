package com.esprit.voyage.repository;


import com.esprit.voyage.entity.Client;
import com.esprit.voyage.entity.SearchHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends CrudRepository<SearchHistory, Long> {
    @Query("select s from SearchHistory s where s.adresse like :adresse")
    public List<SearchHistory> history(@Param("adresse") String adresse);

    @Query("SELECT e FROM Client e, SearchHistory s WHERE e.id = s.idClient AND s.adresse like :adresse ")
    public List<Client> listEmp(@Param("adresse") String adresse);

    @Query("SELECT s FROM SearchHistory s WHERE s.idClient like :idClient AND s.adresse like :adresse")
    public List<SearchHistory> isSearchAlreadySaved(@Param("adresse") String adresse, @Param("idClient") int idClient);
}