package com.workpoint.mwallet.server.dao;

import java.util.List;

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

	public ClientModel getClientByClientCodeNative(String clCode) {

		System.err.println(">>>" + clCode);
		StringBuffer sqlBuffer = new StringBuffer(
				"select clname,middlename,clsurname,phone,clcode from client where clcode like :clientCode");

		String[] splitClientCode = clCode.split("/");

		String searchString = splitClientCode[0] + "%" + splitClientCode[1];

		System.err.println("Search>>>>>" + searchString.trim());

		Query query = em.createNativeQuery(sqlBuffer.toString()).setParameter("clientCode", searchString.trim());

		List<Object[]> result = getResultList(query);

		ClientModel model = new ClientModel();

		System.err.println(">>>>Result Size>>>>>" + result.size());
		if (result != null) {
			// model.setFirstName(result[0] == null ? "" :
			// result[0].toString());
			// model.setMiddleName(result[1] == null ? "" :
			// result[0].toString());
			// model.setSirName(result[2] == null ? "" : result[0].toString());
			// model.setPhoneNo(result[3] == null ? "" : result[0].toString());
			// model.setClCode(result[4] == null ? "" : result[0].toString());

		}
		return model;

	}

	public ClientDocModel getClientByTillCode(String tillCode) {
		String jpql = "FROM ClientDocModel t where t.docnum = :docNum ";

		Query query = em.createQuery(jpql).setParameter("docNum", tillCode);

		return getSingleResultOrNull(query);
	}

}
