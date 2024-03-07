package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemoAppController {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String showList(Authentication loginUser, Model model) {
		model.addAttribute("username", loginUser.getName());
		model.addAttribute("authority", loginUser.getAuthorities());
		return "memo";
	}
	
	@GetMapping("/registerUser")
	public String registerUser(@ModelAttribute("user") Account user) {
		return "registerUser";
	}
	
	@PostMapping("/registerUser")
	public String process(@Validated @ModelAttribute("user") Account user,
			BindingResult result,Model model) {
		
		if(result.hasErrors()) {
			return "registerUser";
		}
		//パスワードをエンコードし、ユーザを登録
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		
		model.addAttribute("username", user.getUsername());
		
		return "redirect:/login?registerUser";
	}
	
	@GetMapping("/editUser")
	public String editUser() {
		return "editUser";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/top")
	public String top() {
		return "top";
	}
	
	@GetMapping("/editMemo")
	public String editMemo() {
		return "editMemo";
	}

}
