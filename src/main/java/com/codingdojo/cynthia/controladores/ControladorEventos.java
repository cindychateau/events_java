package com.codingdojo.cynthia.controladores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.codingdojo.cynthia.modelos.Event;
import com.codingdojo.cynthia.modelos.Message;
import com.codingdojo.cynthia.modelos.State;
import com.codingdojo.cynthia.modelos.User;
import com.codingdojo.cynthia.servicios.AppService;

@Controller
@RequestMapping("/events")
public class ControladorEventos {
	
	@Autowired
	private AppService servicio;
	
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("event") Event event,
						 BindingResult result,
						 HttpSession session,
						 Model model,
						 @RequestParam("imagen") MultipartFile imagen) {
		
		/*Revisamos que haya iniciado sesion*/
		User usuario_en_sesion = (User)session.getAttribute("user_session");
		
		if(usuario_en_sesion == null) {
			return "redirect:/";
		}
		/*Revisamos que haya iniciado sesion*/
		
		//Revisamos si hay errores en la validación del evento
		if(result.hasErrors()) {
			//Enviamos de nuevo todo lo que se despliega en Dashboard
			//Mandamos los estados
			model.addAttribute("states", State.States);
			
			//Obtengo usuario en sesión
			User myUser = servicio.find_user(usuario_en_sesion.getId());
			model.addAttribute("user", myUser); //Enviamos a dashboard el user
			
			//Mandamos las dos listas de eventos
			String miEstado = usuario_en_sesion.getState();
			List<Event> eventos_miestado = servicio.eventos_estado(miEstado);
			List<Event> eventos_otrosedos = servicio.eventos_otros(miEstado);
			
			model.addAttribute("eventos_miestado", eventos_miestado);
			model.addAttribute("eventos_otrosedos", eventos_otrosedos);
			
			
			return "dashboard.jsp";
		} else {
			
			if(!imagen.isEmpty()) {
				//Ruta
				Path directorioImagenes = Paths.get("src/main/resources/static/img");
				//Ruta Absoluta
				String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
				
				try {
					
					//Imagen en Bytes
					byte[] bytesImg = imagen.getBytes();
					//Ruta completa, con todo y nombre de imagen
					Path rutaCompleta = Paths.get(rutaAbsoluta+"/"+imagen.getOriginalFilename());
					Files.write(rutaCompleta, bytesImg); //Guardar mi imagen en la ruta
					
					event.setImage(imagen.getOriginalFilename());
					
				} catch(IOException e) {
					e.printStackTrace();
				}
				
			}
			
			servicio.save_event(event);
			return "redirect:/dashboard";
		}
	}
	
	@GetMapping("/join/{event_id}")
	public String join(@PathVariable("event_id") Long event_id,
					   HttpSession session) {
		
		/*Revisamos que haya iniciado sesion*/
		User usuario_en_sesion = (User)session.getAttribute("user_session");
		
		if(usuario_en_sesion == null) {
			return "redirect:/";
		}
		/*Revisamos que haya iniciado sesion*/
		
		servicio.join_event(usuario_en_sesion.getId(), event_id);
		
		return "redirect:/dashboard";
	}
	
	@GetMapping("/remove/{event_id}")
	public String remove(@PathVariable("event_id") Long event_id,
						 HttpSession session){
		/*Revisamos que haya iniciado sesion*/
		User usuario_en_sesion = (User)session.getAttribute("user_session");
		
		if(usuario_en_sesion == null) {
			return "redirect:/";
		}
		/*Revisamos que haya iniciado sesion*/
		
		servicio.remove_event(usuario_en_sesion.getId(), event_id);
		
		return "redirect:/dashboard";
		
	}
	
	@GetMapping("/{event_id}")
	public String show_event(@PathVariable("event_id") Long event_id,
							 HttpSession session,
							 @ModelAttribute("message") Message message,
							 Model model) {
		/*Revisamos que haya iniciado sesion*/
		User usuario_en_sesion = (User)session.getAttribute("user_session");
		
		if(usuario_en_sesion == null) {
			return "redirect:/";
		}
		/*Revisamos que haya iniciado sesion*/
		
		Event evento = servicio.find_event(event_id);
		model.addAttribute("evento", evento);
		
		return "show.jsp";
		
	}
	
	@PostMapping("/message")
	public String message(@Valid @ModelAttribute("message") Message message,
						  BindingResult result,
						  HttpSession session,
						  Model model) {
		/*Revisamos que haya iniciado sesion*/
		User usuario_en_sesion = (User)session.getAttribute("user_session");
		
		if(usuario_en_sesion == null) {
			return "redirect:/";
		}
		/*Revisamos que haya iniciado sesion*/
		
		if(result.hasErrors()) {
			model.addAttribute("evento", message.getEvent());
			return "show.jsp";
		} else {
			servicio.save_message(message);
			return "redirect:/events/"+message.getEvent().getId();
		}
		
		
	}
	
	@GetMapping("/edit/{event_id}")
	public String edit_event(@PathVariable("event_id") Long event_id, 
							 HttpSession session, 
							 Model model) {
		/*REVISAMOS SESION*/
		User usuario_en_sesion = (User)session.getAttribute("user_session");
		if(usuario_en_sesion == null) {
			return "redirect:/";
		}
		/*REVISAMOS SESION*/
		
		Event event = servicio.find_event(event_id);
		
		if(event == null || !event.getPlanner().getId().equals(usuario_en_sesion.getId())) {
			return "redirect:/dashboard";
		}
		
		model.addAttribute("event",event);
		model.addAttribute("states", State.States);
		
		return "edit.jsp";
	}
	
	@PutMapping("/update")
	public String update_event(@Valid @ModelAttribute("event") Event event, 
							   BindingResult result, HttpSession session, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("states", State.States);
			return "edit.jsp";
		}
		
		Event thisEvent = servicio.find_event(event.getId());
		event.setAttendees(thisEvent.getAttendees());
		servicio.save_event(event);
		
		return "redirect:/dashboard";
	}
	
	@DeleteMapping("/delete/{event_id}")
	public String delete_event(@PathVariable("event_id") Long event_id, HttpSession session) {
		/*REVISAMOS SESION*/
		User usuario_en_sesion = (User)session.getAttribute("user_session");
		if(usuario_en_sesion == null) {
			return "redirect:/";
		}
		/*REVISAMOS SESION*/
		
		Event evento = servicio.find_event(event_id);
		if(evento == null || !evento.getPlanner().getId().equals(usuario_en_sesion.getId())) {
			return "redirect:/dashboard";
		}
		
		servicio.delete_event(event_id);
		
		return "redirect:/dashboard";
	}
	
}
