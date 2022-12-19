package com.newordle.newordle;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import com.newordle.newordle.dao.WordsDao;
import com.newordle.newordle.services.NewordleService;

@EnableScheduling
@Service
class NewordleApplicationTests {
	// @Mock
	// WordsDao wordsDao;

	// @Test
	// public void testValidateEnteredWord() {
	// Set<String> wordSet = Set.of("hello", "green", "world");
	// NewordleService newordleService = new NewordleService(wordSet, "hello",
	// wordsDao);

	// newordleService.validateEnteredWord("green");
	// }

	// @Test
	// public void testInsertOneWord() {
	// Set<String> wordSet = Set.of("hello", "green", "world");
	// NewordleService newordleService = new NewordleService(wordSet, "hello",
	// wordsDao);
	// System.out.println(wordSet);
	// Assertions.assertEquals(newordleService.insertOneWord(null),"WordLength not
	// 5");
	// Mockito.when(wordsDao.getAllWords()).thenReturn(wordSet);
	// Assertions.assertEquals(newordleService.insertOneWord("abc"), "Word Length
	// not 5");

	// Mockito.when(wordsDao.getAllWords()).thenReturn(wordSet);
	// Assertions.assertEquals(newordleService.insertOneWord("hello"), "Word already
	// in the DB");

	// Mockito.when(wordsDao.insertOneWord("steal")).thenReturn("steal");
	// Assertions.assertEquals(newordleService.insertOneWord("steal"), "Successfully
	// Inserted word - steal");
	// wordSet = newordleService.getWordSet....
	// }
}