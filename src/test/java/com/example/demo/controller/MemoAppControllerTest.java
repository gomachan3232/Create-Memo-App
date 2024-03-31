package com.example.demo.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.model.Account;
import com.example.demo.model.Memo;

import jakarta.transaction.Transactional;	

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class MemoAppControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	//テストで必要な情報を作成
	
	//ユーザ登録失敗
	private Account createFailedUser() {
		Account user = new Account();
		return user;
	}
	
	//ユーザ登録成功
	private Account createSuccessUser() {
		Account user = new Account();
		user.setUsername("testUser");
		user.setEmail("sample@example.com");
		user.setPassword("Password1");
		return user;
	}
	
	//メモ登録失敗
	private Memo createFailedMemo() {
		Memo memo = new Memo();
		memo.setMemoDate(LocalDateTime.now());
		memo.setMemoContent("");
		return memo;
	}
	
	//メモ登録成功
	private Memo createSuccessMemo() {
		Memo memo = new Memo();
		memo.setMemoDate(LocalDateTime.now());
		memo.setMemoContent("test用");
		return memo;
	}

	//ログアウト時のテスト----------------------------------------------------------------------------
	
	@Test
	@DisplayName("TOPページへのアクセスが成功する")
	void topPageSuccessAccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/top"))
			//ステータスコードがOK(200)である
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("ユーザ登録画面へのアクセスが成功する")
	void registerUserSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/registerUser"))
			//ステータスコードがOK(200)である
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("ユーザ登録画面で登録に失敗すると再度ユーザ登録画面に遷移する")
	void registerUserFailedRegistration() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/registerUser")
				//入力の属性を設定
				.flashAttr("user", createFailedUser())
				//CSRFトークンを自動挿入
				.with(csrf())
			)
			//エラーがあることを検証
			.andExpect(model().hasErrors())
			//指定したHTMLを表示しているか検証
			.andExpect(view().name("registerUser"));
	}
	
	@Test
	@DisplayName("ユーザ登録画面で登録に成功するとログイン画面に遷移する")
	void registerUserSuccessRegistration() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/registerUser")
				//入力の属性を設定
				.flashAttr("user", createSuccessUser())
				//CSRFトークンを自動挿入
				.with(csrf())
			)
			//ステータスコードがリダイレクトステータスレスポンスコード(302)である
			.andExpect(status().isFound())
			//指定したHTMLを表示しているか検証
			.andExpect(redirectedUrl("/login?registerUser"));
	}
	
	@Test
	@DisplayName("ログイン画面へのアクセスが成功する")
	void loginPageSuccessAccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/login"))
			//ステータスコードがOK(200)である
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("ログイン画面でログインに失敗すると再度ログイン画面に遷移する")
	void loginPageSuccessLogin() throws Exception {
		mockMvc.perform(formLogin("/top")
				//ユーザ名
				.user("nobody")
				//パスワード
				.password("password")
			)
			//ステータスコードがリダイレクトステータスレスポンスコード(302)である
			.andExpect(status().isFound())
			//指定したHTMLを表示しているか検証
			.andExpect(redirectedUrl("/login?error"));
				
	}
	
	@Test
	@DisplayName("ログイン画面でログインに成功するとメモ一覧画面に遷移する")
	void loginPageFailedLogin() throws Exception {
		mockMvc.perform(formLogin("/top")
				//ユーザ名
				.user("admin")
				//パスワード
				.password("Password1")
			)
			//ステータスコードがリダイレクトステータスレスポンスコード(302)である
			.andExpect(status().isFound())
			//指定したHTMLを表示しているか検証
			.andExpect(redirectedUrl("/"));
	}
	
	@Test
	@DisplayName("ユーザ編集画面にアクセスするとTOPページにリダイレクトされる")
	void editUserPageFailedAccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/editUser"))
			//ステータスコードがリダイレクトステータスレスポンスコード(302)である
			.andExpect(status().isFound())
			//指定したHTMLを表示しているか検証
			.andExpect(redirectedUrl("http://localhost/top"));
	}
	
	@Test
	@DisplayName("メモ一覧画面にアクセスするとTOPページにリダイレクトされる")
	void memoPageFailedAccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/memo"))
			//ステータスコードがリダイレクトステータスレスポンスコード(302)である
			.andExpect(status().isFound())
			//指定したHTMLを表示しているか検証
			.andExpect(redirectedUrl("http://localhost/top"));
	}
	
	@Test
	@DisplayName("メモ編集画面にアクセスするとTOPページにリダイレクトされる")
	void editMemoPageFailedAccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/editMemo/8502"))
			//ステータスコードがリダイレクトステータスレスポンスコード(302)である
			.andExpect(status().isFound())
			//指定したHTMLを表示しているか検証
			.andExpect(redirectedUrl("http://localhost/top"));
	}
	
	//ログアウト時のテストここまで----------------------------------------------------------------------
	
	//ログイン時のテスト-------------------------------------------------------------------------------
	
	@Test
	@DisplayName("メモ一覧画面へのアクセスが成功する")
	@WithMockUser(username = "admin")
	void memoPageSuccessAccess() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
		//ステータスコードがOK(200)である
			.andExpect(status().isOk())
			//指定したHTMLを表示しているか検証
			.andExpect(view().name("memo"));
	}
	
	@Test
	@DisplayName("ユーザ編集画面へのアクセスが成功する")
	@WithMockUser(username = "admin")
	void editUserPageSuccessAccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/editUser"))
			//ステータスコードがOK(200)である
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("ユーザ編集に失敗すると再度ユーザ編集画面に遷移する")
	@WithMockUser(username = "admin")
	void editUserFailedEdit() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/editUser")
				//入力の属性を設定
				.flashAttr("user", createFailedUser())
				//CSRFトークンを自動挿入
				.with(csrf())
			)
			//ステータスコードがOK(200)である
			.andExpect(status().isOk())
			//指定したHTMLを表示しているか検証
			.andExpect(view().name("editUser"));
	}
	
	@Test
	@DisplayName("ユーザ編集に成功するとメモ一覧画面に遷移する")
	@WithMockUser(username = "adimin")
	void editUserSuccessEdit() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/editUser")
				//入力の属性を設定
				.flashAttr("user", createSuccessUser())
				//CSRFトークンを自動挿入
				.with(csrf())
			)
			//ステータスコードがリダイレクトステータスレスポンスコード(302)である
			.andExpect(status().isFound())
			//指定したHTMLを表示しているか検証
			.andExpect(redirectedUrl("/"));
	}
	
	@Test
	@DisplayName("ログアウトボタンを押下するとログアウトに成功する")
	@WithMockUser(username = "admin")
	void logoutSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/logout"))
			//ステータスコードがリダイレクトステータスレスポンスコード(302)である
			.andExpect(status().isFound())
			//指定したHTMLを表示しているか検証
			.andExpect(redirectedUrl("/top?logout"));
	}
	
	@Test
	@DisplayName("メモの登録に失敗すると再度メモ一覧画面に遷移し、エラー文が表示される")
	@WithMockUser(username = "admin")
	void memoFailedRegistration() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.post("/memo")
				//入力の属性を設定
				.flashAttr("memo", createFailedMemo())
				//CSRFトークンを自動挿入
				.with(csrf())
				)
			//ステータスコードがリダイレクトステータスレスポンスコード(302)である
			.andExpect(status().isFound())
			//指定したHTMLを表示しているか検証
			.andExpect(redirectedUrl("/?error"));
	}
	
	@Test
	@DisplayName("メモの登録に成功するとメモ一覧画面に遷移する")
	@WithMockUser(username = "admin")
	void memoSuccessRegistration() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.post("/memo")
				//入力の属性を設定
				.flashAttr("memo", createSuccessMemo())
				//CSRFトークンを自動挿入
				.with(csrf())
				)
			//ステータスコードがOK(200)である
			.andExpect(status().isOk())
			//指定したHTMLを表示しているか検証
			.andExpect(view().name("memo"));
	}
	
	@Test
	@DisplayName("メモ編集画面へのアクセスが成功する")
	@WithMockUser(username = "admin")
	void editMemoSuccessAccess() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/editMemo/8502"))
			//ステータスコードがOK(200)である
			.andExpect(status().isOk())
			//指定したHTMLを表示しているか検証
			.andExpect(view().name("editMemo"));
	}
	
	@Test
	@DisplayName("メモ編集画面で編集に失敗すると再度メモ編集画面に遷移する")
	@WithMockUser(username = "admin")
	void editMemoFailedEdit() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/editMemo/8502")
				//入力の属性を設定
				.flashAttr("memo", createFailedMemo())
				//CSRFトークンを自動挿入
				.with(csrf())
				)
			//ステータスコードがリダイレクトステータスレスポンスコード(302)である
			.andExpect(status().isFound())
			//指定したHTMLを表示しているか検証
			.andExpect(redirectedUrl("/editMemo/8502?error"));
	}
	
	@Test
	@DisplayName("メモ編集画面で編集に成功するとメモ一覧画面に遷移する")
	@WithMockUser(username = "admin")
	void editMemoSuccessEdit() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/editMemo/8502")
				//入力の属性を設定
				.flashAttr("memo", createSuccessMemo())
				//CSRFトークンを自動挿入
				.with(csrf())
				)
			//ステータスコードがリダイレクトステータスレスポンスコード(302)である
			.andExpect(status().isFound())
			//指定したHTMLを表示しているか検証
			.andExpect(redirectedUrl("/"));
	}

}
