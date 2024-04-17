package com.example.demo.config;

import java.time.LocalDateTime;

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
		//DBに保存
		userRepository.save(user);
		
		//メモを作成
		var memo = new Memo();
		memo.setMemoContent("sample");
		memo.setMemoDate(LocalDateTime.now());
		memo.setUserName("admin");
		//DBに保存
		memoRepository.save(memo);
	}

}
