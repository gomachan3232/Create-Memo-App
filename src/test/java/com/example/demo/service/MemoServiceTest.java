package com.example.demo.service;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Memo;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class MemoServiceTest {
	
	@Autowired
	private MemoService memoService;
	
//テスト用に登録したデータ
//DateTimeFormatter withoutZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//	var memo1 = new Memo();
//	memo1.setMemoContent("memo1");
//	memo1.setMemoDate(LocalDateTime.parse("2024-01-01 00:00:00",withoutZone));
//	memo1.setUserName("testUser");
//	
//	memoRepository.save(memo1);
//	
//	var memo2 = new Memo();
//	memo2.setMemoContent("memo2");
//	memo2.setMemoDate(LocalDateTime.parse("2023-12-31 23:59:59",withoutZone));
//	memo2.setUserName("testUser");
//	
//	memoRepository.save(memo2);
//	
//	var memo3 = new Memo();
//	memo3.setMemoContent("memo3");
//	memo3.setMemoDate(LocalDateTime.parse("2024-04-01 00:00:00",withoutZone));
//	memo3.setUserName("testUser");
//	
//	memoRepository.save(memo3);
//	
//	var memo4 = new Memo();
//	memo4.setMemoContent("memo4");
//	memo4.setMemoDate(LocalDateTime.parse("2024-01-01 00:00:00",withoutZone));
//	memo4.setUserName("unknownUser");
//	
//	memoRepository.save(memo4);
	
	@Test
	@DisplayName("ユーザでメモ内容が絞り込み出来ている")
	void MemoContentsCanBeNarrowedDownByUsername() throws Exception{
		List<Memo> memoList = memoService.getLoginUserMemo("testUser");
		assertEquals(3,memoList.size());
	}
	
	@Test
	@DisplayName("指定したIDのメモ内容が取得できている")
	void TheMemoContentsOfTheSpecifiedIdCanBeObtained() throws Exception{
		Memo memoTest = memoService.findById((long) 8502);
		assertEquals("sample1", memoTest.getMemoContent());
	}
	
	@Test
	@DisplayName("startDate以降のusernameが一致するデータを取得できている")
	void DataThatMatchesUsernameAfterStartDateCanBeRetrieved() throws Exception{
		DateTimeFormatter withoutZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		List<Memo> memoList = memoService.memoSearch(null, "testUser", LocalDateTime.parse("2024-01-01 00:00:00",withoutZone), null);
		assertEquals(2,memoList.size());
	}
	
	@Test
	@DisplayName("endDate以前のusernameが一致するデータを取得できている")
	void DataMatchingTheUsernameBeforeEndDateCanBeObtained() throws Exception{
		DateTimeFormatter withoutZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		List<Memo> memoList = memoService.memoSearch(null, "testUser", null, LocalDateTime.parse("2024-01-01 00:00:00",withoutZone));
		assertEquals(2,memoList.size());
	}
	
	@Test
	@DisplayName("startDate以降endDate以前のusernameが一致するデータを取得できている")
	void DataThatMatchesTheUsernameBetweenStartDateAndEndDateCanBeObtained() throws Exception{
		DateTimeFormatter withoutZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		List<Memo> memoList = memoService.memoSearch(null, "testUser", LocalDateTime.parse("2024-01-01 00:00:00",withoutZone), LocalDateTime.parse("2024-04-01 00:00:00",withoutZone));
		assertEquals(2,memoList.size());
	}
	
	@Test
	@DisplayName("検索した文字で絞り込め、かつusernameが一致するデータを取得できている")
	void BeAbleToNarrowDownTheSearchUsingTheSearchedCharactersAndRetrieveDataThatMatchedTheUsername() throws Exception{
		DateTimeFormatter withoutZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		List<Memo> memoList = memoService.memoSearch("memo1", "testUser", null, null);
		assertEquals(1,memoList.size());
	}

}
