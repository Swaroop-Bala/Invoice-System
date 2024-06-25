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

	
	/**
	 * Below Method helps in converting the Model Invoice to Model Entity Object
	 * This covertion is required, prior to utilizing the repositories
	 * @param InvoiceModel
	 * @return InvoiceEntity
	 * 
	 */
	public static InvoiceEntity mapAddInvoiceEntity(InvoiceModel aInvoice) throws ParseException {

		InvoiceEntity myInvoiceEntity = new InvoiceEntity();
		myInvoiceEntity.setDueAmount((null == aInvoice.getAmount()) ? 0.0 : aInvoice.getAmount());
		myInvoiceEntity.setDueDate(StringUtils.isBlank(aInvoice.getDue_date()) ? LocalDate.now()
				: LocalDate.parse(aInvoice.getDue_date(), myDateTimeFormatter));
		myInvoiceEntity.setPaidAmount((null == aInvoice.getPaid_amount()) ? 0.0 : aInvoice.getPaid_amount());
		myInvoiceEntity.setStatus("pending");

		return myInvoiceEntity;

	}
	/**
	 * Below Method helps in converting the Invoice ID from Entity back to Model so that in service 
	 * response only invoice id would be listed
	 * @param InvoiceEntity
	 * @return InvoiceModel
	 */
	public static InvoiceModel mapAddInvoiceModel(InvoiceEntity aInvoiceEntity) {

		InvoiceModel myInvoice = new InvoiceModel();
		String myInvoiceId = String.valueOf(aInvoiceEntity.getInvoiceId());
		myInvoice.setId(myInvoiceId);
		return myInvoice;
	}

	/**
	 * Below Method helps in converting the List of Invoice Entity back to List of InvoiceModel so that in GET service 
	 * response List of Invoice would be visible
	 * @param List<InvoiceEntity>
	 * @return List<InvoiceModel>
	 */
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


}
