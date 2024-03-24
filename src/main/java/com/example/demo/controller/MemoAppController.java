package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Account;
import com.example.demo.model.Memo;
import com.example.demo.repository.MemoRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MemoService;
import com.example.demo.validator.CreateUser;
import com.example.demo.validator.EditUser;

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
		model.addAttribute("memo", memoService.getLoginUserMemo(loginUser));
		return "memo";
	}
	
	@GetMapping("/searchResults")
	public String searchResultsList(Authentication loginUser,@ModelAttribute("memo") Memo memo, Model model,
			@RequestParam("keyword")String keyword,
			@RequestParam("startDate")String startDate,
			@RequestParam("endDate")String endDate) {
		DateTimeFormatter withoutZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime startDateTime = null;
		LocalDateTime endDateTime = null;
		if(!StringUtils.isEmpty(startDate)) {
			startDateTime = LocalDateTime.parse(startDate + " 00:00:00", withoutZone);
		}
		 if(!StringUtils.isEmpty(endDate)) {
			 endDateTime = LocalDateTime.parse(endDate + " 23:59:59", withoutZone);
		 }
		model.addAttribute("username", loginUser.getName());
		model.addAttribute("authority", loginUser.getAuthorities());
		model.addAttribute("memo", memoService.memoSearch(keyword, loginUser.getName(),startDateTime,endDateTime));
		model.addAttribute("keyword", keyword);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return "memo";
	}
	
	@PostMapping("/memo")
	public String memo(@Validated @ModelAttribute("memo") Memo memo, BindingResult result, Authentication loginUser, Model model) {
		model.addAttribute("username", loginUser.getName());
		model.addAttribute("authority", loginUser.getAuthorities());
		if(result.hasErrors()) {
			model.addAttribute("memo", memoService.getLoginUserMemo(loginUser));
			return "redirect:/?error";
		}
		memo.setMemoDate(LocalDateTime.now());
		memo.setUserName(loginUser.getName());
		
		memoRepository.save(memo);
		
		model.addAttribute("memo", memoService.getLoginUserMemo(loginUser));
		
		return "memo";
	}
	
	@GetMapping("/registerUser")
	public String registerUser(@ModelAttribute("user") Account user) {
		return "registerUser";
	}
	
	@PostMapping("/registerUser")
	public String process(@ModelAttribute("user") @Validated(CreateUser.class) Account user,
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
	public String editUser(@ModelAttribute("user") Account user, Authentication loginUser, Model model) {
		model.addAttribute("loginUserName", loginUser.getName());
		return "editUser";
	}
	
	@PostMapping("/editUser")
	public String updateUser(@ModelAttribute("user") @Validated(EditUser.class) Account user,BindingResult result, Authentication loginUser,Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("loginUserName", loginUser.getName());
			return "editUser";
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "redirect:/";
	}
	
	@GetMapping("/error")
	public String blankMapping() {
	    return "redirect:/";
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
