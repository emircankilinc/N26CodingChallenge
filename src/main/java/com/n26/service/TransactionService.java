package com.n26.service;

import java.text.ParseException;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;

import com.n26.model.Statistic;
import com.n26.model.Transaction;
import com.n26.model.TransactionStatistic;
import com.n26.repository.TransactionRepository;
import com.n26.util.TransactionUtil;

@Component
public class TransactionService implements ITransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public HttpStatus insertTransaction(Transaction transaction) {
		try {
			Long transactionTimeInMillis = TransactionUtil.calculateTimeInMillis(transaction.getTimestamp());
			Long currentTimeInMillis = Instant.now().toEpochMilli();
			
			if (currentTimeInMillis - transactionTimeInMillis < 0
					|| !TransactionUtil.isTransactionAmountValid(transaction)) {
				return HttpStatus.UNPROCESSABLE_ENTITY;
			}

			Statistic statistic = TransactionUtil.getStatisticFromTransaction(transaction);

			ConcurrentHashMap<Long, Statistic> transactionStatisticsMap = transactionRepository
					.getTransactionStatisticsMap();

			synchronized (transactionStatisticsMap) {
				Statistic transactionStatistic = transactionStatisticsMap.get(transactionTimeInMillis);
				if (transactionStatistic == null) {
					transactionStatisticsMap.put(transactionTimeInMillis, statistic);
				} else {
					transactionStatistic.assignStatisticValue(statistic);
				}
			}
			return HttpStatus.CREATED;
		} catch (HttpMessageNotReadableException e) {
			return HttpStatus.BAD_REQUEST;
		} catch (NumberFormatException e) {
			return HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (ParseException e) {
			return HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (NullPointerException e) {
			return HttpStatus.NO_CONTENT;
		}
	}

	@Override
	public TransactionStatistic getStatisticsForLastMinute() {
		Long currentTimeInSecondFromEpoch = Instant.now().toEpochMilli();
		ConcurrentHashMap<Long, Statistic> transactionStatisticsMap = transactionRepository
				.getTransactionStatisticsMap();

		Statistic statistic = new Statistic();

		synchronized (transactionStatisticsMap) {
			for (Long counter = 0L; counter < 60000L; counter++) {
				Statistic statisticFromMap = transactionStatisticsMap.get(currentTimeInSecondFromEpoch - counter);
				if (statisticFromMap != null) {
					statistic.assignStatisticValue(statisticFromMap);
				}
			}
		}

		return TransactionUtil.getTransactionStatistic(statistic);
	}

	@Override
	public void deleteAllTransactions() {
		transactionRepository.deleteAllTransactions();
	}

}
