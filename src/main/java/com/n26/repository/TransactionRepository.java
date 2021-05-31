package com.n26.repository;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.n26.model.Statistic;

@Component
public class TransactionRepository {

	private ConcurrentHashMap<Long, Statistic> transactionStatisticsMap;

	@PostConstruct
	void init() {
		this.transactionStatisticsMap = new ConcurrentHashMap<Long, Statistic>();
	}

	public void insertTransaction(Long timeStamp, Statistic statistic) {
		transactionStatisticsMap.put(timeStamp, statistic);
	}

	public void deleteAllTransactions() {
		transactionStatisticsMap.clear();
	}

	public ConcurrentHashMap<Long, Statistic> getTransactionStatisticsMap() {
		return transactionStatisticsMap;
	}

	public void setTransactionStatisticsMap(ConcurrentHashMap<Long, Statistic> transactionStatisticsMap) {
		this.transactionStatisticsMap = transactionStatisticsMap;
	}

}
