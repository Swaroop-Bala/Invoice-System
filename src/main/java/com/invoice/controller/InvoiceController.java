package com.invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invoice.model.InvoiceModel;
import com.invoice.model.OverDueModel;
import com.invoice.service.InvoiceService;

@RestController
public class InvoiceController {

	@Autowired
	InvoiceService myInvoiceService;

	@PostMapping("/invoices")
	public ResponseEntity<?> addInvoice(@RequestBody InvoiceModel aInvoiceModel) {

		try {
			return this.myInvoiceService.addInvoice(aInvoiceModel);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("System Error: Error Occured While Adding Invoice");

		}
	}

	@GetMapping("/invoices")
	public ResponseEntity<?> addInvoice() {

		try {
			return this.myInvoiceService.getInvoice();
		} catch (Exception ex) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(" System Error: Unable to fetch Invoice details");

		}
	}

	@PostMapping("/invoices/{aInvoiceId}/payments")
	public ResponseEntity<?> processPayments(@PathVariable String aInvoiceId, @RequestBody InvoiceModel aInvoice) {

		try {
			aInvoice.setId(aInvoiceId);
			return this.myInvoiceService.processPayments(aInvoice);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("System Error: Error Occured During Payment Process");

		}
	}

	@PostMapping("/invoices/process-overdue")
	public ResponseEntity<?> processOverdue(@RequestBody OverDueModel aOverDueModel) {

		try {
			return this.myInvoiceService.processOverdue(aOverDueModel);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("System Error: Error Occured During Payment Process");

		}
	}
}
