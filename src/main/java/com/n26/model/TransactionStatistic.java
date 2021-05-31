package com.n26.model;

import com.n26.constant.TransactionConstant;

public class TransactionStatistic {

	private String sum = TransactionConstant.DEFAULT_DECIMAL_VALUE;
	private String avg = TransactionConstant.DEFAULT_DECIMAL_VALUE;
	private String max = TransactionConstant.DEFAULT_DECIMAL_VALUE;
	private String min = TransactionConstant.DEFAULT_DECIMAL_VALUE;
	private long count;

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
