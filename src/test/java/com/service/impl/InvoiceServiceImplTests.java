package com.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.invoice.dao.InvoiceDAO;
import com.invoice.entity.InvoiceEntity;
import com.invoice.impl.InvoiceServiceImpl;
import com.invoice.model.InvoiceModel;
import com.invoice.model.OverDueModel;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceImplTests {
	

	private static final InvoiceServiceImpl aInvoiceServiceImpl = new InvoiceServiceImpl();

	@Mock
	InvoiceDAO aInvoiceDAO;
	
	@Mock
	InvoiceEntity aInvoiceEntity;
	
	@InjectMocks
	InvoiceServiceImpl myInvoiceServiceImpl;
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAddInvoiceWithEmptyParameters() throws Exception {
		Mockito.when(aInvoiceDAO.saveAndFlush(Mockito.any())).thenReturn(aInvoiceEntity);
		InvoiceModel myInvoiceModel = new InvoiceModel();
		ResponseEntity<?> myResponseEntity = myInvoiceServiceImpl.addInvoice(myInvoiceModel);
		assertEquals(myResponseEntity.getStatusCode(),HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	@Test
	public void testAddInvoiceWithPartialParamters() throws Exception {
		Mockito.when(aInvoiceDAO.saveAndFlush(Mockito.any())).thenReturn(aInvoiceEntity);
		InvoiceModel myInvoiceModel = new InvoiceModel();
		myInvoiceModel.setAmount(100.00);
		myInvoiceModel.setDue_date("2023-11-22");
		ResponseEntity<?> myResponseEntity = myInvoiceServiceImpl.addInvoice(myInvoiceModel);
		assertEquals(myResponseEntity.getStatusCode(),HttpStatus.CREATED);

	}

	
	
	@Test
	public void testAddInvoiceNullObject() throws Exception {
		InvoiceModel myInvoiceModel = new InvoiceModel();
		myInvoiceModel.setAmount(100.00);
		myInvoiceModel.setDue_date(null);
		Mockito.when(aInvoiceDAO.saveAndFlush(Mockito.any())).thenReturn(aInvoiceEntity);
		ResponseEntity<?> myResponseEntity = myInvoiceServiceImpl.addInvoice(myInvoiceModel);
		assertEquals(myResponseEntity.getStatusCode(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	
	@Test
	public void testAddInvoiceWithInvalidParameters() throws Exception {
		InvoiceModel myInvoiceModel = new InvoiceModel();
		myInvoiceModel.setAmount(100.00);
		myInvoiceModel.setDue_date("34324");
		Mockito.when(aInvoiceDAO.saveAndFlush(Mockito.any())).thenReturn(aInvoiceEntity);
		ResponseEntity<?> myResponseEntity = myInvoiceServiceImpl.addInvoice(myInvoiceModel);
		assertEquals(myResponseEntity.getStatusCode(),HttpStatus.INTERNAL_SERVER_ERROR);

	}


	@Test
	public void testGetInvoice() throws Exception {
		List<InvoiceEntity> myInvoiceEntity = new ArrayList <InvoiceEntity>();
		Mockito.when(aInvoiceDAO.findAll()).thenReturn(myInvoiceEntity);
		ResponseEntity<?> myResponseEntity = myInvoiceServiceImpl.getInvoice();
		assertEquals(myResponseEntity.getStatusCode(),HttpStatus.OK);

	}
	
	@Test
	public void testProcessPayments() throws Exception {
		Mockito.when(aInvoiceDAO.saveAndFlush(Mockito.any())).thenReturn(aInvoiceEntity);
		InvoiceModel myInvoiceModel = new InvoiceModel();
		ResponseEntity<?> myResponseEntity = myInvoiceServiceImpl.processPayments(myInvoiceModel);
		assertEquals(myResponseEntity.getStatusCode(),HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	@Test
	public void testProcessOverdue() throws Exception {
		Mockito.when(aInvoiceDAO.saveAndFlush(Mockito.any())).thenReturn(aInvoiceEntity);
		OverDueModel myOverDueModel = new OverDueModel();
		ResponseEntity<?> myResponseEntity = myInvoiceServiceImpl.processOverdue(myOverDueModel);
		assertEquals(myResponseEntity.getStatusCode(),HttpStatus.OK);

	}
	

}
