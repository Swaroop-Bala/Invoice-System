package com.invoice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invoice.entity.InvoiceEntity;

public interface InvoiceDAO extends JpaRepository<InvoiceEntity, Long> {

	List<InvoiceEntity> findByStatus(String aStatus);

}
