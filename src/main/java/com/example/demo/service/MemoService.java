package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
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
	
	public List<Memo> getLoginUserMemo(Authentication loginUser) {
		
		String username = loginUser.getName();
		Specification<Memo> spec = new MemoSpecification<Memo>().usernameEqual(username);
		Sort sort = Sort.by(Sort.Direction.DESC, "memoDate");
		return memoRepository.findAll(spec, sort);
		
	}
	
	public Memo findById(Long id) {
		return memoRepository.findById(id).get();
	}
	
	public List<Memo> memoSearch(String keyword, String loginUser, LocalDateTime startDate, LocalDateTime endDate) {
		MemoSpecification<Memo> spec = new MemoSpecification<Memo>();
		Sort sort = Sort.by(Sort.Direction.DESC, "memoDate");
		return memoRepository.findAll(Specification.where(spec.memoContains(keyword))
				.and(spec.usernameEqual(loginUser))
				.and(spec.startDateGreaterThanEqual(startDate))
				.and(spec.endDateGreaterThanEqual(endDate)), sort);
	}

}
