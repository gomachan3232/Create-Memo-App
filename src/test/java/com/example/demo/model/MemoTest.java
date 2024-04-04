package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@SpringBootTest
@Transactional
class MemoTest {
	
	private final Validator validator;
	
	@Autowired
	public MemoTest(Validator validator) {
		this.validator = validator;
	}

	@Test
	@DisplayName("userName、memoDate、memoContentが正しく入力されている")
	void successRegistration() {
		Memo memoTest = new Memo();
		DateTimeFormatter withoutZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime memoDate = LocalDateTime.parse("2024-01-01 00:00:00",withoutZone);
		memoTest.setUserName("testUser");
		memoTest.setMemoDate(memoDate);
		memoTest.setMemoContent("test");
		Set<ConstraintViolation<Memo>> violations = validator.validate(memoTest);
		assertEquals(0,violations.size());
	}
	
	@Test
	@DisplayName("userNameが空白の場合")
	void userNameIsBlank() {
		Memo memoTest = new Memo();
		DateTimeFormatter withoutZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime memoDate = LocalDateTime.parse("2024-01-01 00:00:00",withoutZone);
		memoTest.setUserName("");
		memoTest.setMemoDate(memoDate);
		memoTest.setMemoContent("test");
		Set<ConstraintViolation<Memo>> violations = validator.validate(memoTest);
		assertEquals(0,violations.size());
	}
	
	@Test
	@DisplayName("memoContentが空白の場合")
	void memoContentIsBlank() {
		Memo memoTest = new Memo();
		DateTimeFormatter withoutZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime memoDate = LocalDateTime.parse("2024-01-01 00:00:00",withoutZone);
		memoTest.setUserName("testUser");
		memoTest.setMemoDate(memoDate);
		memoTest.setMemoContent("");
		Set<ConstraintViolation<Memo>> violations = validator.validate(memoTest);
		assertEquals(1,violations.size());
		violations.stream().forEach(v ->{
			assertEquals( "空白は許可されていません",v.getMessage());
		});
	}
	
	@Test
	@DisplayName("memoContentが501文字以上の場合")
	void memoContentIsFiveHundredOneCharactersOrMore() {
		Memo memoTest = new Memo();
		DateTimeFormatter withoutZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime memoDate = LocalDateTime.parse("2024-01-01 00:00:00",withoutZone);
		memoTest.setUserName("testUser");
		memoTest.setMemoDate(memoDate);
		memoTest.setMemoContent("123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901");
		Set<ConstraintViolation<Memo>> violations = validator.validate(memoTest);
		assertEquals(1,violations.size());
		violations.stream().forEach(v ->{
			assertEquals( "0 から 500 の間のサイズにしてください",v.getMessage());
		});
	}

}
