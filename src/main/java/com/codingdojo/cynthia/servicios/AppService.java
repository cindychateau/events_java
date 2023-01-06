package com.codingdojo.cynthia.servicios;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.cynthia.modelos.User;
import com.codingdojo.cynthia.repositorios.RepositorioEventos;
import com.codingdojo.cynthia.repositorios.RepositorioMensajes;
import com.codingdojo.cynthia.repositorios.RepositorioUsuarios;

@Service
public class AppService {
	
	@Autowired
	private RepositorioUsuarios repositorio_usuarios;
	
	@Autowired
	private RepositorioEventos repositorio_eventos;
	
	@Autowired
	private RepositorioMensajes repositorio_mensajes;
	
	public User register(User nuevoUsuario, BindingResult result) {
		
		String nuevoEmail = nuevoUsuario.getEmail(); //Obtenemos el correo
		User existeUsuario = repositorio_usuarios.findByEmail(nuevoEmail); //NULL o Objeto User
		
		//Verificando que el correo no exista
		if(existeUsuario != null) {
			result.rejectValue("email", "Unique", "El correo ya está registrado en nuestra BD");
		}
		
		//Comparando las contraseñas
		String contra = nuevoUsuario.getPassword();
		String confirmacion = nuevoUsuario.getConfirm();
		if(! contra.equals(confirmacion)) {
			result.rejectValue("confirm", "Matches", "Las contraseñas no coinciden");
		}
		
		if(!result.hasErrors()) {
			//Encriptamos contraseña
			String contra_encr = BCrypt.hashpw(nuevoUsuario.getPassword(), BCrypt.gensalt());
			nuevoUsuario.setPassword(contra_encr);
			//Guardo usuario
			return repositorio_usuarios.save(nuevoUsuario);
		}else {
			return null;
		}
		
		
	}
	
	public User login(String email, String password) {
		
		//Buscamos que el correo esté en la BD
		User existeUsuario = repositorio_usuarios.findByEmail(email); //NULL o Objeto Usuario
		if(existeUsuario == null) {
			return null;
		}
		
		//Comparamos contraseñas encriptadas
		if(BCrypt.checkpw(password, existeUsuario.getPassword())) {
			return existeUsuario;
		} else {
			return null;
		}
		
	}
	
}
