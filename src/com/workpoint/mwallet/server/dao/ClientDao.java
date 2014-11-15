package com.workpoint.mwallet.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.workpoint.mwallet.server.dao.model.ClientDocModel;
import com.workpoint.mwallet.server.dao.model.ClientModel;

public class ClientDao extends BaseDaoImpl {

	public ClientDao(EntityManager em) {
		super(em);
	}

	public ClientModel getClientByCode(String clCode) {
		String jpql = "FROM ClientModel t where t.clCode = :clCode ";

		Query query = em.createQuery(jpql).setParameter("clCode", clCode);

		return getSingleResultOrNull(query);
	}

	public ClientDocModel getClientByTillCode(String tillCode) {
		String jpql = "FROM ClientDocModel t where t.docnum = :docNum ";

		Query query = em.createQuery(jpql).setParameter("docNum", tillCode);

		return getSingleResultOrNull(query);
	}

}
