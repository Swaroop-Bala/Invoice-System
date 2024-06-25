package com.invoice.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Invoices")
public class InvoiceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long invoiceId;

	@Column(name = "DueAmount")
	private double DueAmount;

	@Column(name = "dueDate")
	private LocalDate dueDate;

	@Column(name = "paidAmount")
	private Double paidAmount;

	@Column(name = "status")
	private String status;

	public InvoiceEntity() {
	}

	public InvoiceEntity(double dueAmount, LocalDate dueDate, Double paidAmount, String status) {
		this.DueAmount = dueAmount;
		this.dueDate = dueDate;
		this.paidAmount = paidAmount;
		this.status = status;
	}

	public long getInvoiceId() {
		return invoiceId;
	}

	public double getDueAmount() {
		return DueAmount;
	}

	public void setDueAmount(double dueAmount) {
		DueAmount = dueAmount;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
