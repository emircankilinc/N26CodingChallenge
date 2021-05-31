package com.n26.codingchallenge.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.model.Transaction;
import com.n26.model.TransactionStatistic;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void test_invalidTimeStampFormat_UNPROCESSABLE_ENTITY_() {
		Transaction transaction = new Transaction("434.15", "2021-05-31");
		ResponseEntity<Void> response = testRestTemplate.postForEntity("/transactions", transaction, Void.class);
		Assert.assertEquals(" Response HttpStatus is UNPROCESSABLE_ENTITY/422", HttpStatus.UNPROCESSABLE_ENTITY,
				response.getStatusCode());
	}

	@Test
	public void test_InvalidTransactionAmountFormat_UNPROCESSABLE_ENTITY() {
		Transaction transaction = new Transaction("Hello World", "2021-05-31T04:45:08.514349");
		ResponseEntity<Void> response = testRestTemplate.postForEntity("/transactions", transaction, Void.class);
		Assert.assertEquals(" Response HttpStatus is UNPROCESSABLE_ENTITY/422", HttpStatus.UNPROCESSABLE_ENTITY,
				response.getStatusCode());
	}

	@Test
	public void test_ValidTransactionParameterFormat_CREATED() {
		Transaction transaction = new Transaction("434.15", "2021-05-31T04:44:08.514349");
		ResponseEntity<Void> response = testRestTemplate.postForEntity("/transactions", transaction, Void.class);
		Assert.assertEquals(" Response HttpStatus is CREATED/201", HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void test_NullTransactionParameters_CREATED() {
		Transaction transaction = new Transaction(null, null);
		ResponseEntity<Void> response = testRestTemplate.postForEntity("/transactions", transaction, Void.class);
		Assert.assertEquals(" Response HttpStatus is NO_CONTENT/204", HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	public void test_InvalidBodyType_BAD_REQUEST() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		ResponseEntity<Void> response = testRestTemplate.exchange("/transactions", HttpMethod.POST,
				new HttpEntity<>("", headers), Void.class);
		Assert.assertEquals(" Response HttpStatus is BAD_REQUEST/400 ", HttpStatus.BAD_REQUEST,
				response.getStatusCode());
	}

	@Test
	public void test_DeleteStatistics_OK() {
		ResponseEntity<Void> response = testRestTemplate.exchange("/transactions", HttpMethod.DELETE, null, Void.class);
		Assert.assertEquals(" Response HttpStatus is NO_CONTENT/204", HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	public void test_GetStatistics_OK() {
		insertTemporaryTransactions();
		ResponseEntity<TransactionStatistic> response = testRestTemplate.getForEntity("/statistics",
				TransactionStatistic.class);
		Assert.assertEquals(" Response HttpStatus is OK/200", HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(" Response Count : 0", true, response.getBody().getCount() == 0L);

	}

	private void insertTemporaryTransactions() {
		testRestTemplate.postForEntity("/transactions",
				new Transaction("50", Calendar.getInstance().getTime().toString()), Void.class);
	}

}
