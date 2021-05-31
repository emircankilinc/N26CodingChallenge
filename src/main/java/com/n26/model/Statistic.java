package com.n26.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Statistic {

	private BigDecimal sum;
	private BigDecimal avg;
	private BigDecimal max;
	private BigDecimal min;
	private long count;

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public BigDecimal getAvg() {
		return avg;
	}

	public void setAvg(BigDecimal avg) {
		this.avg = avg;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	private boolean isEmpty() {
		return this.sum == null && this.avg == null && this.max == null && this.min == null;
	}

	public void assignStatisticValue(Statistic statistic) {
		if (!statistic.isEmpty()) {
			if (this.isEmpty()) {
				this.sum = statistic.getSum().setScale(2, RoundingMode.HALF_UP);
				this.max = statistic.getMax().setScale(2, RoundingMode.HALF_UP);
				this.min = statistic.getMin().setScale(2, RoundingMode.HALF_UP);
				this.count = statistic.getCount();
			} else {
				this.sum = this.sum.add(statistic.getSum()).setScale(2, RoundingMode.HALF_UP);
				this.max = this.max.max(statistic.getMax()).setScale(2, RoundingMode.HALF_UP);
				this.min = this.min.min(statistic.getMin()).setScale(2, RoundingMode.HALF_UP);
				this.count = this.count + statistic.getCount();
			}
			this.avg = this.sum.divide(BigDecimal.valueOf(this.count), 2, RoundingMode.HALF_UP);
		}
	}

}
