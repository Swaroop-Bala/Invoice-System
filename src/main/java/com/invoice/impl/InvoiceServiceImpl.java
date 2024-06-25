package com.invoice.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.invoice.dao.InvoiceDAO;
import com.invoice.entity.InvoiceEntity;
import com.invoice.helper.EntityModelMappers;
import com.invoice.model.InvoiceModel;
import com.invoice.model.OverDueModel;
import com.invoice.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	InvoiceDAO myInvoiceDAO;

	protected static final Logger logger = LogManager.getLogger();

	@Override
	public ResponseEntity<?> addInvoice(InvoiceModel aInvoiceModel) throws Exception {
		try {
			InvoiceEntity myInvoiceEntity = EntityModelMappers.mapAddInvoiceEntity(aInvoiceModel);
			myInvoiceDAO.saveAndFlush(myInvoiceEntity);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(EntityModelMappers.mapAddInvoiceModel(myInvoiceEntity));
		} catch (Exception myException) {
			logger.error(myException.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("System Error : Error while Adding Invoice");
		}

	}

	@Override
	public ResponseEntity<?> getInvoice() {
		List<InvoiceEntity> myInvoiceEntity = new ArrayList<InvoiceEntity>();
		try {
			myInvoiceEntity = myInvoiceDAO.findAll();
			if(myInvoiceEntity.isEmpty())
				return ResponseEntity.status(HttpStatus.OK)
						.body("No Invoices Available");
				
			return ResponseEntity.status(HttpStatus.OK)
					.body(EntityModelMappers.mapGetInvoiceModelList(myInvoiceEntity));
		} catch (Exception myException) {
			logger.error(myException.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("System Error : Error while Fetching Invoice");

		}

	}

	@Override
	public ResponseEntity<?> processPayments(InvoiceModel aInvoice) throws Exception {

		InvoiceEntity myInvoiceEntity = new InvoiceEntity();
		try {

			long myInvoiceID = Long.parseLong(aInvoice.getId());
			myInvoiceEntity = myInvoiceDAO.findById(myInvoiceID).orElseThrow(() -> new Exception());

			double myPaymentAmount = aInvoice.getAmount();
			if (!(StringUtils.equalsIgnoreCase(myInvoiceEntity.getStatus(), "paid")
					|| (myPaymentAmount > myInvoiceEntity.getDueAmount()))) {

				double myDueAmt = myInvoiceEntity.getDueAmount();
				myDueAmt -= myPaymentAmount;
				myInvoiceEntity.setPaidAmount(myPaymentAmount);
				myInvoiceEntity.setStatus("paid");
				myInvoiceDAO.saveAndFlush(myInvoiceEntity);
				if (myDueAmt != 0) {
					InvoiceEntity myNewInvoiceEntity = new InvoiceEntity();
					myNewInvoiceEntity.setDueAmount(myDueAmt);
					myNewInvoiceEntity.setDueDate(myInvoiceEntity.getDueDate());
					myNewInvoiceEntity.setPaidAmount(0.0);
					myNewInvoiceEntity.setStatus("pending");
					myInvoiceDAO.saveAndFlush(myNewInvoiceEntity);
				}
				return ResponseEntity.status(HttpStatus.OK).body("Payment Successful");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("System Error : Either OverPayment or Invoice has been Already Paid!");
			}

		} catch (Exception myException) {
			logger.error(myException.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("System Error : Error while processing Payment!");

		}
	}

	@Override
	public ResponseEntity<?> processOverdue(OverDueModel aOverDueModel) {

		try {
			handlePendingPayments(aOverDueModel);
		} catch (Exception myException) {
			logger.error(myException.getMessage());
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("System Error : Error while Applying Late Fee");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Late Fees Applied SuccessFully");
	}

	private void handlePendingPayments(OverDueModel aOverDueModel) throws Exception {

		List<InvoiceEntity> myInvoiceEntityList = myInvoiceDAO.findByStatus("pending").stream()
				.filter(p -> p.getDueDate().isBefore(LocalDate.now().minusDays(aOverDueModel.getOverdue_days())))
				.filter(p -> p.getPaidAmount() == 0).collect(Collectors.toList());

		if (null != myInvoiceEntityList && !myInvoiceEntityList.isEmpty()) {

			List<InvoiceEntity> myInvoiceEntityNewList = new ArrayList<InvoiceEntity>();

			for (InvoiceEntity myInvoiceEntity : myInvoiceEntityList) {

				InvoiceEntity myNewInvoiceEntity = new InvoiceEntity();
				myNewInvoiceEntity.setDueAmount(myInvoiceEntity.getDueAmount());
				myNewInvoiceEntity.setDueDate(myInvoiceEntity.getDueDate());
				myNewInvoiceEntity.setPaidAmount(myInvoiceEntity.getPaidAmount());
				myNewInvoiceEntity.setStatus("pending");

				myInvoiceEntityNewList.add(myNewInvoiceEntity);
			}

			myInvoiceEntityList.forEach(s -> s.setStatus("void"));

			myInvoiceDAO.saveAllAndFlush(myInvoiceEntityList);

			myInvoiceEntityNewList.forEach(d -> {
				d.setDueAmount(d.getDueAmount() + aOverDueModel.getLate_fee());
				d.setDueDate(LocalDate.now());

			});
			myInvoiceDAO.saveAllAndFlush(myInvoiceEntityNewList);

		}
	}

}
