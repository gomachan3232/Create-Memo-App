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
	
	//コンストラクタ
	public MemoService (MemoRepository memoRepository) {
		this.memoRepository = memoRepository;
	}
	
	//ログインユーザ名でメモをフィルタリングする
	public List<Memo> getLoginUserMemo(Authentication loginUser) {
		
		String username = loginUser.getName();
		//ログインユーザ名でメモをフィルタリングする
		Specification<Memo> spec = new MemoSpecification<Memo>().usernameEqual(username);
		//memoDate(メモの登録時間)で降順にソートする
		Sort sort = Sort.by(Sort.Direction.DESC, "memoDate");
		
		return memoRepository.findAll(spec, sort);
		
	}
	
	//idでメモの情報を取ってくる
	public Memo findById(Long id) {
		return memoRepository.findById(id).get();
	}
	
	//日付や検索単語でメモを検索する
	public List<Memo> memoSearch(String keyword, String loginUser, LocalDateTime startDate, LocalDateTime endDate) {
		MemoSpecification<Memo> spec = new MemoSpecification<Memo>();
		Sort sort = Sort.by(Sort.Direction.DESC, "memoDate");
		
		//keywordの文字が含まれているメモをフィルタリング
		return memoRepository.findAll(Specification.where(spec.memoContains(keyword))
				//ログインユーザ名でメモをフィルタリング
				.and(spec.usernameEqual(loginUser))
				//startDate以降のメモをフィルタリング
				.and(spec.startDateGreaterThanEqual(startDate))
				//endDate以前のメモをフィルタリングしmemoDateでソート
				.and(spec.endDateGreaterThanEqual(endDate)), sort);
	}

	//getLoginUserMemoテスト用
	public List<Memo> getLoginUserMemo(String username) {
		Specification<Memo> spec = new MemoSpecification<Memo>().usernameEqual(username);
		Sort sort = Sort.by(Sort.Direction.DESC, "memoDate");
		return memoRepository.findAll(spec, sort);
	}

}
