package com.codingdojo.cynthia.controladores;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.cynthia.modelos.State;
import com.codingdojo.cynthia.modelos.User;
import com.codingdojo.cynthia.servicios.AppService;

@Controller
public class ControladorUsuarios {
	
	@Autowired
	private AppService servicio;
	
	@GetMapping("/")
	public String index(@ModelAttribute("nuevoUsuario") User nuevoUsuario,
						Model model) {
		
		//Enviar los estados que puede elegir
		model.addAttribute("states", State.States);
		
		return "index.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("nuevoUsuario") User nuevoUsuario,
						   BindingResult result,
						   HttpSession session,
						   Model model) {
		
		servicio.register(nuevoUsuario, result);
		if(result.hasErrors()) {
			//Enviar los estados que puede elegir
			model.addAttribute("states", State.States);
			
			return "index.jsp";
		} else {
			session.setAttribute("user_session", nuevoUsuario);
			return "redirect:/dashboard";
		}
		
	}
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session) {
		User usuario_en_sesion = (User)session.getAttribute("user_session");
		
		if(usuario_en_sesion == null) {
			return "redirect:/";
		}
		
		return "dashboard.jsp";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user_session");
		return "redirect:/";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("email") String email,
						@RequestParam("password") String password,
						RedirectAttributes redirectAttributes,
						HttpSession session) {
		
		//Enviar email y password y que el servicio verifique que son correctos
		User usuario_login = servicio.login(email, password);
		
		if(usuario_login == null) {
			//Hay error
			redirectAttributes.addFlashAttribute("error_login", "El correo/password son incorrectos");
			return "redirect:/";
		} else {
			//Guardamos en sesión
			session.setAttribute("user_session", usuario_login);
			return "redirect:/dashboard";
		}
		
	}
	
}