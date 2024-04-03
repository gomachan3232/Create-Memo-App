package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.annotation.Validated;

import com.example.demo.validator.CreateUser;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@SpringBootTest
@Transactional
@Validated
class AccountTest {
	
	private final Validator validator;
	
	@Autowired
	public AccountTest(Validator validator) {
		this.validator = validator;
	}
	
	@Test
	@DisplayName("username、email、passwordが正しく入力されている")
	void successRegistration() {
		Account testUser = new Account();
		testUser.setUsername("testUser");
		testUser.setEmail("test@example.com");
		testUser.setPassword("Password1");
		Set<ConstraintViolation<Account>> violations = validator.validate(testUser, new Class[] {CreateUser.class} );
		assertEquals(0,violations.size());
	}
	
	@Test
	@DisplayName("usernameが空白の場合")
	void usernameIsBlank() {
		Account testUser = new Account();
		testUser.setUsername("");
		testUser.setEmail("test@example.com");
		testUser.setPassword("Password1");
		Set<ConstraintViolation<Account>> violations = validator.validate(testUser, new Class[] {CreateUser.class} );
		assertEquals(2,violations.size());
		violations.stream().forEach(v ->{
			assertTrue(List.of("3 から 12 の間のサイズにしてください" , "空白は許可されていません").contains(v.getMessage()));
		});
	}
	
	@Test
	@DisplayName("usernameが2文字以下の場合")
	void usernameIsTwoCharactersOrLess() {
		Account testUser = new Account();
		testUser.setUsername("aa");
		testUser.setEmail("test@example.com");
		testUser.setPassword("Password1");
		Set<ConstraintViolation<Account>> violations = validator.validate(testUser, new Class[] {CreateUser.class} );
		assertEquals(1,violations.size());
		violations.stream().forEach(v ->{
			assertEquals("3 から 12 の間のサイズにしてください", v.getMessage());
		});
	}
	
	@Test
	@DisplayName("usernameが13文字以上の場合")
	void usernameIsThirteenCharactersOrMore() {
		Account testUser = new Account();
		testUser.setUsername("1234567890123");
		testUser.setEmail("test@example.com");
		testUser.setPassword("Password1");
		Set<ConstraintViolation<Account>> violations = validator.validate(testUser, new Class[] {CreateUser.class} );
		assertEquals(1,violations.size());
		violations.stream().forEach(v ->{
			assertEquals("3 から 12 の間のサイズにしてください", v.getMessage());
		});
	}
	
	@Test
	@DisplayName("usernameが重複している場合")
	void usernameIsDuplication() {
		Account testUser = new Account();
		testUser.setUsername("admin");
		testUser.setEmail("test@example.com");
		testUser.setPassword("Password1");
		Set<ConstraintViolation<Account>> violations = validator.validate(testUser, new Class[] {CreateUser.class} );
		assertEquals(1,violations.size());
		violations.stream().forEach(v ->{
			assertEquals("このユーザ名は既に登録されています", v.getMessage());
		});
	}
	
	@Test
	@DisplayName("emailが空白の場合")
	void emailIsBlank() {
		Account testUser = new Account();
		testUser.setUsername("testUser");
		testUser.setEmail("");
		testUser.setPassword("Password1");
		Set<ConstraintViolation<Account>> violations = validator.validate(testUser, new Class[] {CreateUser.class} );
		assertEquals(1,violations.size());
		violations.stream().forEach(v ->{
			assertEquals("空白は許可されていません", v.getMessage());
		});
	}
	
	@Test
	@DisplayName("emailがEmail形式ではない場合")
	void emailIsNotEmailFormat() {
		Account testUser = new Account();
		testUser.setUsername("testUser");
		testUser.setEmail("test");
		testUser.setPassword("Password1");
		Set<ConstraintViolation<Account>> violations = validator.validate(testUser, new Class[] {CreateUser.class} );
		assertEquals(1,violations.size());
		violations.stream().forEach(v ->{
			assertEquals("電子メールアドレスとして正しい形式にしてください", v.getMessage());
		});
	}
	
	@Test
	@DisplayName("emailが201文字以上の場合")
	void emailIsTwoHundred() {
		Account testUser = new Account();
		testUser.setUsername("testUser");
		testUser.setEmail("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345test@example.com");
		testUser.setPassword("Password1");
		Set<ConstraintViolation<Account>> violations = validator.validate(testUser, new Class[] {CreateUser.class} );
		assertEquals(2,violations.size());
		violations.stream().forEach(v ->{
			assertTrue(List.of("0 から 200 の間のサイズにしてください" , "電子メールアドレスとして正しい形式にしてください").contains(v.getMessage()));
		});
	}
	
	@Test
	@DisplayName("passwordが空白の場合")
	void passwordIsBlank() {
		Account testUser = new Account();
		testUser.setUsername("testUser");
		testUser.setEmail("test@example.com");
		testUser.setPassword("");
		Set<ConstraintViolation<Account>> violations = validator.validate(testUser, new Class[] {CreateUser.class} );
		assertEquals(3,violations.size());
		violations.stream().forEach(v ->{
			assertTrue(List.of("空白は許可されていません" , "6 から 60 の間のサイズにしてください", "半角英字大小と数字を含む値を設定してください").contains(v.getMessage()));
		});
	}
	
	@Test
	@DisplayName("passwordが5文字以下の場合")
	void passwordIsFiveCharactersOrLess() {
		Account testUser = new Account();
		testUser.setUsername("testUser");
		testUser.setEmail("test@example.com");
		testUser.setPassword("Pass1");
		Set<ConstraintViolation<Account>> violations = validator.validate(testUser, new Class[] {CreateUser.class} );
		assertEquals(1,violations.size());
		violations.stream().forEach(v ->{
			assertEquals("6 から 60 の間のサイズにしてください", v.getMessage());
		});
	}
	
	@Test
	@DisplayName("passwordが61文字以上の場合")
	void passwordIsSixtyOneCharactersOrMore() {
		Account testUser = new Account();
		testUser.setUsername("testUser");
		testUser.setEmail("test@example.com");
		testUser.setPassword("Pass1Pass1Pass1Pass1Pass1Pass1Pass1Pass1Pass1Pass1Pass1Pass1P");
		Set<ConstraintViolation<Account>> violations = validator.validate(testUser, new Class[] {CreateUser.class} );
		assertEquals(1,violations.size());
		violations.stream().forEach(v ->{
			assertEquals("6 から 60 の間のサイズにしてください", v.getMessage());
		});
	}
	
	@Test
	@DisplayName("passwordに数字が含まれていない場合")
	void passwordIsNotContainsNumber() {
		Account testUser = new Account();
		testUser.setUsername("testUser");
		testUser.setEmail("test@example.com");
		testUser.setPassword("Password");
		Set<ConstraintViolation<Account>> violations = validator.validate(testUser, new Class[] {CreateUser.class} );
		assertEquals(1,violations.size());
		violations.stream().forEach(v ->{
			assertEquals("半角英字大小と数字を含む値を設定してください", v.getMessage());
		});
	}
	
	@Test
	@DisplayName("passwordに半角英字(小文字)が含まれていない場合")
	void passwordIsNotContainsHalfWidthLowercaseLetters() {
		Account testUser = new Account();
		testUser.setUsername("testUser");
		testUser.setEmail("test@example.com");
		testUser.setPassword("P12345");
		Set<ConstraintViolation<Account>> violations = validator.validate(testUser, new Class[] {CreateUser.class} );
		assertEquals(1,violations.size());
		violations.stream().forEach(v ->{
			assertEquals("半角英字大小と数字を含む値を設定してください", v.getMessage());
		});
	}
	
	@Test
	@DisplayName("passwordに半角英字(大文字)が含まれていない場合")
	void passwordIsNotContansHalfWidthUppercaseLetters() {
		Account testUser = new Account();
		testUser.setUsername("testUser");
		testUser.setEmail("test@example.com");
		testUser.setPassword("password1");
		Set<ConstraintViolation<Account>> violations = validator.validate(testUser, new Class[] {CreateUser.class} );
		assertEquals(1,violations.size());
		violations.stream().forEach(v ->{
			assertEquals("半角英字大小と数字を含む値を設定してください", v.getMessage());
		});
	}

}
