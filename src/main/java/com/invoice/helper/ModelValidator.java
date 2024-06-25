package com.invoice.helper;

import com.invoice.model.InvoiceModel;


public class ModelValidator {

	/**
	 * Below method validates the request sent for addInvoice service
	 * @param InvoiceModel
	 * @return boolean
	 */
	public static boolean isValidAddInvoiceRequest(InvoiceModel myInvoiceModel) {
		
		if (null == myInvoiceModel.getDue_date()) {
			return false;
		}
		return true;
	}
}
