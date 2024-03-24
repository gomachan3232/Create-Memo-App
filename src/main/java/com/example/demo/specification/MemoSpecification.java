package com.example.demo.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class MemoSpecification<T> {
	
	public Specification<T> usernameEqual(String username) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.equal(root.get("userName"), username);
			}
		};
	}
	
	public Specification<T> memoContains(String memo) {
		return StringUtils.isEmpty(memo) ? null : new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.like(root.get("memoContent"), "%" + memo + "%");
			}
		};
	}
	
	public Specification<T> startDateGreaterThanEqual(LocalDateTime startDate) {
		return startDate == null ? null : new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.greaterThanOrEqualTo(root.get("memoDate"), startDate);
			}
		};
	}
	
	public Specification<T> endDateGreaterThanEqual(LocalDateTime endDate) {
		return endDate == null ? null : new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.lessThanOrEqualTo(root.get("memoDate"), endDate);
			}
		};
	}
}
