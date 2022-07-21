package com.esprit.voyage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esprit.voyage.entity.WrongWords;

@Repository
public interface WrongWordsRepository extends JpaRepository<WrongWords, Integer> {

}
