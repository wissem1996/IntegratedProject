package com.esprit.voyage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esprit.voyage.entity.Event;

/**
 * @author mokgharbi
 */
public interface EventRepository  extends JpaRepository<Event, Integer> {

}
