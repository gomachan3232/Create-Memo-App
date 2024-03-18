package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Account;
import com.example.demo.model.Memo;
import com.example.demo.repository.MemoRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MemoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemoAppController {
	
	private final UserRepository userRepository;
	private final MemoRepository memoRepository;
	private final PasswordEncoder passwordEncoder;
	private final MemoService memoService;
	
	@GetMapping("/")
	public String showList(Authentication loginUser,@ModelAttribute("memo") Memo memo, Model model) {
		model.addAttribute("username", loginUser.getName());
		model.addAttribute("authority", loginUser.getAuthorities());
		model.addAttribute("memo", memoService.getSearch(loginUser));
		return "memo";
	}
	
	@PostMapping("/memo")
	public String memo(@Validated @ModelAttribute("memo") Memo memo, BindingResult result, Authentication loginUser, Model model) {
		model.addAttribute("username", loginUser.getName());
		model.addAttribute("authority", loginUser.getAuthorities());
		if(result.hasErrors()) {
			model.addAttribute("memo", memoService.getSearch(loginUser));
			return "redirect:/?error";
		}
		memo.setMemoDate(LocalDateTime.now());
		memo.setUserName(loginUser.getName());
		
		memoRepository.save(memo);
		
		model.addAttribute("memo", memoService.getSearch(loginUser));
		
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
	
	@GetMapping("/editMemo/{id}")
	public String editMemo(@ModelAttribute("memo") Memo memo, Authentication loginUser, Model model, @PathVariable Long id) {
		model.addAttribute("username", loginUser.getName());
		model.addAttribute("authority", loginUser.getAuthorities());
		model.addAttribute("memo", memoService.findById(id));
		return "editMemo";
	}
	
	@PostMapping("editMemo/{id}")
	public String updateMemo(@Validated @ModelAttribute("memo") Memo memo, BindingResult result, Model model, @PathVariable Long id) {

		if(result.hasErrors()) {
			model.addAttribute("memo", memoService.findById(id));
			return "redirect:/editMemo/{id}?error";
		}
		
		memoRepository.save(memo);
		return "redirect:/";
	}
	
	@GetMapping("editMemo/{id}/delete")
	public String memoDelete(@ModelAttribute("memo") Memo memo, @PathVariable Long id) {
		
		memoRepository.delete(memo);
		return "redirect:/";
	}

}
