package com.codingdojo.cynthia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.cynthia.modelos.User;

@Repository
public interface RepositorioUsuarios extends CrudRepository<User, Long> {
	
	User findByEmail(String email); //SELECT * FROM users WHERE email = <CORREO>
	
}
