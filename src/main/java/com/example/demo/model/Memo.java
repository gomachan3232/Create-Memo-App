package com.example.demo.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
public class Memo {
	//主キー、自動生成
	@Id
	@GeneratedValue
	private Long id;
	
	//アカウントのユーザ名
	private String userName;
	
	//日付へ変換
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate Date;
	
	//必須入力、文字列が500文字まで
	@NotBlank
	@Size(max = 500)
	private String memo;
	
}
