package com.invoice.service;

import org.springframework.http.ResponseEntity;

import com.invoice.model.InvoiceModel;
import com.invoice.model.OverDueModel;

public abstract interface InvoiceService {

	public ResponseEntity<?> addInvoice(InvoiceModel aInvoiceModel) throws Exception;

	public ResponseEntity<?> getInvoice() throws Exception;;

	public ResponseEntity<?> processPayments(InvoiceModel aInvoice) throws Exception;;

	public ResponseEntity<?> processOverdue(OverDueModel aOverDueModel) throws Exception;;
}
