package com.workpoint.mwallet.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.workpoint.mwallet.server.dao.model.SettingModel;

public class SettingsDao extends BaseDaoImpl {

	public SettingsDao(EntityManager em) {
		super(em);
	}

	public List<SettingModel> getSettings() {
		String jpql = "FROM SettingModel t order by SettingKey Desc";

		Query query = em.createQuery(jpql);
		return getResultList(query);
	}
	
	public void saveSetting(SettingModel setting) {
		save(setting);
	}
}
