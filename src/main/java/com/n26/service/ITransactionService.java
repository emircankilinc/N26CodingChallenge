package com.n26.service;

import org.springframework.http.HttpStatus;

import com.n26.model.Transaction;
import com.n26.model.TransactionStatistic;

public interface ITransactionService {

	HttpStatus insertTransaction(Transaction transaction) throws Exception;

	TransactionStatistic getStatisticsForLastMinute();

	void deleteAllTransactions();

}
