package com.invoice.helper;

import com.invoice.model.InvoiceModel;

public class ModelValidator {

	
	public static boolean isValidAddInvoiceRequest(InvoiceModel myInvoiceModel) {
		
		if (null == myInvoiceModel.getDue_date()) {
			return false;
		}
		return true;
	}
}
