package com.newordle.newordle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.newordle.newordle.dao.WordsDao;
import com.newordle.newordle.services.NewordleService;

// @ExtendWith(SpringExtension.class)
// @RunWith(SpringRunner.class)
@Service
// @SpringBootTest
class NewordleApplicationTests {
	// @Mock
	WordsDao wordsDao = Mockito.mock(WordsDao.class);

	@Test
	public void testValidateEnteredWord() {
		Set<String> wordSet = Set.of("hello", "green", "world");
		NewordleService newordleService = new NewordleService(wordSet, "hello",
				wordsDao);

		newordleService.validateEnteredWord("green");
	}

	@Test
	public void testInsertOneWord() {
		Set<String> wordSet = new HashSet<>(Arrays.asList("hello", "green", "world"));
		NewordleService newordleService = new NewordleService(wordSet, "hello",
				wordsDao);
		System.out.println(wordSet);
		// Add null check
		// Assertions.assertEquals(newordleService.insertOneWord(null), "WordLength not
		// 5");

		Mockito.when(wordsDao.getAllWords()).thenReturn(wordSet);
		Assertions.assertEquals(newordleService.insertOneWord("abc"), "Word Length not 5");

		Mockito.when(wordsDao.getAllWords()).thenReturn(wordSet);
		Assertions.assertEquals(newordleService.insertOneWord("hello"), "Word already in the DB");

		Mockito.when(wordsDao.insertOneWord("steal")).thenReturn("steal");
		Assertions.assertEquals(newordleService.insertOneWord("steal"), "Successfully Inserted word - steal");
		// wordSet = newordleService.getWordSet....
	}
}