package com.codingdojo.cynthia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.cynthia.modelos.Event;

@Repository
public interface RepositorioEventos extends CrudRepository<Event, Long> {
	
	List<Event> findByState(String state); //SELECT * FROM events WHERE state = <String state>
	
	List<Event> findByStateIsNot(String state); //SELECT * FROM events WHERE state != <String state>
	
}
