package com.workpoint.mwallet.server.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.workpoint.mwallet.server.dao.model.TillModel;
import com.workpoint.mwallet.shared.model.SearchFilter;

public class TillDao extends BaseDaoImpl {

	public TillDao(EntityManager em) {
		super(em);
	}

	public List<TillModel> getAllTills(SearchFilter filter) {
		if (filter == null)
			return getResultList(em
					.createQuery("FROM TillModel t order by tillNo DESC"));

		StringBuffer jpql = new StringBuffer("FROM TillModel t ");
		Map<String, Object> params = new HashMap<>();

		boolean isFirst = true;
		
		if(filter.getTill()!=null){
			jpql.append(isFirst? " Where" : " And");
			jpql.append(" t.tillNo = :tillNumber");
			params.put("tillNumber", filter.getTill().getTillNo());
			isFirst = false;
		}
		
		Query query = em.createQuery(jpql.toString());
		for(String key: params.keySet()){
			query.setParameter(key, params.get(key));
		}
		
		return getResultList(query);
	}

	public List<TillModel> getTillsByName(String name) {
		String jpql = "FROM TillModel t where t.businessName like :bizName "
				+ " order by tillNo Desc";

		Query query = em.createQuery(jpql).setParameter("bizName",
				"%" + name + "%");

		return getResultList(query);
	}

	public void saveTill(TillModel till) {
		save(till);
	}
}
