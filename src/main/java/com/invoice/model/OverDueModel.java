package com.invoice.model;

public class OverDueModel {

	private Double late_fee;
	private long overdue_days;

	public Double getLate_fee() {
		return late_fee;
	}

	public void setLate_fee(Double late_fee) {
		this.late_fee = late_fee;
	}

	public long getOverdue_days() {
		return overdue_days;
	}

	public void setOverdue_days(int overdue_days) {
		this.overdue_days = overdue_days;
	}

}
