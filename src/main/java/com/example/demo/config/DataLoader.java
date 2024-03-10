package com.example.demo.config;

import java.time.LocalDate;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.model.Account;
import com.example.demo.model.Memo;
import com.example.demo.repository.MemoRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner{
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final MemoRepository memoRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		//adminユーザを作成
		var user = new Account();
		
		user.setUsername("admin");
		user.setPassword(passwordEncoder.encode("Password1"));
		user.setEmail("test@test");
		
		userRepository.save(user);
		
		var memo = new Memo();
		memo.setMemo("sample");
		memo.setDate(LocalDate.parse("2024-01-01"));
		memo.setUserName("admin");
		
		memoRepository.save(memo);
	}

}
