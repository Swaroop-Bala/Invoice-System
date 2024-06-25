package com.invoice.helper;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.invoice.entity.InvoiceEntity;
import com.invoice.model.InvoiceModel;

public class EntityModelMappers {

	final static DateTimeFormatter myDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static InvoiceEntity mapAddInvoiceEntity(InvoiceModel aInvoice) throws ParseException {

		InvoiceEntity myInvoiceEntity = new InvoiceEntity();
		myInvoiceEntity.setDueAmount((null == aInvoice.getAmount()) ? 0.0 : aInvoice.getAmount());
		myInvoiceEntity.setDueDate(StringUtils.isBlank(aInvoice.getDue_date()) ? LocalDate.now()
				: LocalDate.parse(aInvoice.getDue_date(), myDateTimeFormatter));
		myInvoiceEntity.setPaidAmount((null == aInvoice.getPaid_amount()) ? 0.0 : aInvoice.getPaid_amount());
		myInvoiceEntity.setStatus("pending");

		return myInvoiceEntity;

	}

	public static InvoiceModel mapAddInvoiceModel(InvoiceEntity aInvoiceEntity) {

		InvoiceModel myInvoice = new InvoiceModel();
		String myInvoiceId = String.valueOf(aInvoiceEntity.getInvoiceId());
		myInvoice.setId(myInvoiceId);
		return myInvoice;
	}

	public static List<InvoiceModel> mapGetInvoiceModelList(List<InvoiceEntity> myInvoiceEntity) {

		List<InvoiceModel> myInvoiceList = new ArrayList<InvoiceModel>();

		for (InvoiceEntity aInvoiceEntity : myInvoiceEntity) {
			InvoiceModel myInvoice = new InvoiceModel();
			myInvoice.setId(String.valueOf(aInvoiceEntity.getInvoiceId()));
			myInvoice.setAmount(aInvoiceEntity.getDueAmount());
			myInvoice.setDue_date(aInvoiceEntity.getDueDate().toString().formatted(myDateTimeFormatter));
			myInvoice.setPaid_amount(aInvoiceEntity.getPaidAmount());
			myInvoice.setStatus(aInvoiceEntity.getStatus());
			myInvoiceList.add(myInvoice);
		}
		return myInvoiceList;
	}

	public static InvoiceEntity mapPaymentEntity(InvoiceModel aInvoiceModel) {

		InvoiceEntity myInvoiceEntity = new InvoiceEntity();
		myInvoiceEntity.setDueAmount(aInvoiceModel.getAmount());
		return myInvoiceEntity;
	}

}
