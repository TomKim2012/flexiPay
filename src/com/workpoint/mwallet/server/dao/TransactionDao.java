package com.workpoint.mwallet.server.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.workpoint.mwallet.server.dao.model.TransactionModel;

public class TransactionDao extends BaseDaoImpl {

	public TransactionDao(EntityManager em) {
		super(em);
	}

	public List<TransactionModel> getAllTrx() {
		
		return getResultList(em.createQuery("FROM TransactionModel t"));
	}
}
