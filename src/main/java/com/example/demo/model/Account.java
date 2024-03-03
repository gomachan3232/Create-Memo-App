package com.example.demo.model;

import com.example.demo.validator.UniqueLogin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {
	//必須入力、文字列が3文字以上12文字まで
	@Id
	@NotBlank
	@Size(min = 3,max = 12)
	@UniqueLogin
	private String username;
	
	//必須入力、Email形式であること、文字列が200文字まで
	@NotBlank
	@Email
	@Size(max  = 200)
	private String email;
	
	//必須入力、文字列が6文字以上60文字まで、半角英大文字小文字と数字を含む
	@NotBlank
	@Size(min = 6,max = 60)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$")
	private String password;
}
