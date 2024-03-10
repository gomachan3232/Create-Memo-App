package com.example.demo.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.model.Memo;
import com.example.demo.repository.MemoRepository;
import com.example.demo.specification.MemoSpecification;

@Service
public class MemoService {
	
	private final MemoRepository memoRepository;
	
	public MemoService (MemoRepository memoRepository) {
		this.memoRepository = memoRepository;
	}
	
	public List<Memo> getSearch(Authentication loginUser) {
		
		String username = loginUser.getName();
		MemoSpecification<Memo> spec = new MemoSpecification<>();
		
		return memoRepository.findAll(
				Specification.where(spec.usernameEqual(username)));
		
	}

}
