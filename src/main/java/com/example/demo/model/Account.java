package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {
	//必須入力、文字列が20文字まで
	@NotBlank
	private String username;
	
	//必須入力、Email形式であること、文字列が200文字まで
	@NotBlank
	@Email
	@Size(max  = 200)
	private String email;
	
	//
	@NotBlank
	@Size(min = 6,max = 50)
	private String password;
}
