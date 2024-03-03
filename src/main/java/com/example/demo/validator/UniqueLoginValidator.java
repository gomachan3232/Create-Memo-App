package com.example.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String>{
	
	private final UserRepository userRepository;
	
	public UniqueLoginValidator() {
		this.userRepository = null;
	}
	
	@Autowired
	public UniqueLoginValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return userRepository == null || userRepository.findByUsername(value).isEmpty();
	}

}
