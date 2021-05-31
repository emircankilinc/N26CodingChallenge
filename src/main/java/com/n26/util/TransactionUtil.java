package com.n26.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.n26.constant.TransactionConstant;
import com.n26.model.Statistic;
import com.n26.model.Transaction;
import com.n26.model.TransactionStatistic;

public class TransactionUtil {

	public static Statistic getStatisticFromTransaction(Transaction transaction) throws NumberFormatException {
		Statistic statistic = new Statistic();
		BigDecimal amount = new BigDecimal(transaction.getAmount());
		statistic.setSum(amount);
		statistic.setMin(amount);
		statistic.setMax(amount);
		statistic.setCount(1L);
		return statistic;
	}

	public static Long calculateTimeInMillis(String timestamp) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(TransactionConstant.TIME_FORMAT);
		Date date = dateFormat.parse(timestamp);
		return date.getTime();
	}

	public static TransactionStatistic getTransactionStatistic(Statistic statistic) {
		TransactionStatistic tempStatistic = new TransactionStatistic();
		if (statistic != null) {
			tempStatistic.setSum(statistic.getSum() != null ? statistic.getSum().toString()
					: TransactionConstant.DEFAULT_DECIMAL_VALUE);
			tempStatistic.setMax(statistic.getMax() != null ? statistic.getMax().toString()
					: TransactionConstant.DEFAULT_DECIMAL_VALUE);
			tempStatistic.setMin(statistic.getMin() != null ? statistic.getMin().toString()
					: TransactionConstant.DEFAULT_DECIMAL_VALUE);
			tempStatistic.setAvg(statistic.getAvg() != null ? statistic.getAvg().toString()
					: TransactionConstant.DEFAULT_DECIMAL_VALUE);
			tempStatistic.setCount(statistic.getCount());
		}
		return tempStatistic;
	}

	public static boolean isTransactionAmountValid(Transaction transaction) {
		try {
			new BigDecimal(transaction.getAmount());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
