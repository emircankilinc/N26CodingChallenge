package com.n26.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.n26.model.Transaction;
import com.n26.model.TransactionStatistic;
import com.n26.service.TransactionService;

@Component
@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping(value = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> insertTransaction(@RequestBody Transaction transaction) {
		HttpStatus insertTransaction = transactionService.insertTransaction(transaction);
		return new ResponseEntity<>(insertTransaction);
	}

	@DeleteMapping(value = "/transactions")
	public ResponseEntity<Void> deleteAllTransactions() {
		transactionService.deleteAllTransactions();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TransactionStatistic> getStatisticForLastMinute() {
		TransactionStatistic statisticsForLastMinute = transactionService.getStatisticsForLastMinute();
		return new ResponseEntity<>(statisticsForLastMinute, HttpStatus.OK);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final ResponseEntity<Void> httpMessageNotReadableException(HttpMessageNotReadableException ex,
			WebRequest request) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
