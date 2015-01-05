package com.workpoint.mwallet.server.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.workpoint.mwallet.server.dao.model.SmsModel;

public class SMSLogDao extends BaseDaoImpl {

	public SMSLogDao(EntityManager em) {
		super(em);
	}

	public List<SmsModel> getSMSLog() {
			return getResultList(em
					.createQuery("FROM SmsModel"));
	}
}
