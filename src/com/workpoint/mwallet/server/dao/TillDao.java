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
		
		if(filter.getPhrase()!=null){
			jpql.append(isFirst? " Where" : " And");
			jpql.append(" (t.tillNo like :phrase or t.businessName like :phrase or " +
					"t.owner like :phrase or t.salesPerson like :phrase)");
			params.put("phrase", "%"+filter.getPhrase()+"%");
			isFirst = false;
		}
		
		if(filter.getOwner()!=null){
			jpql.append(isFirst? " Where" : " And");
			jpql.append("(t.owner.userId = :owner)");
			params.put("owner", filter.getOwner().getUserId());
			isFirst = false;
		}
		
		if(filter.getSalesPerson()!=null){
			jpql.append(isFirst? " Where" : " And");
			jpql.append(" (t.salesPerson.userId = :salesPerson)");
			params.put("salesPerson", filter.getSalesPerson().getUserId());
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
	
	public TillModel getTillByTillNo(String tillNo){
		String jpql = "FROM TillModel t where t.tillNo like :tillNo ";
		
		Query query = em.createQuery(jpql).setParameter("tillNo", tillNo);

		return getSingleResultOrNull(query);
	}

	public void saveTill(TillModel till) {
		save(till);
	}
}
