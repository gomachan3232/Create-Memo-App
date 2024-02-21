package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Account;

public interface UserRepository extends JpaRepository<Account, Long>{
	Optional<Account> findByUsername(String username);
}
