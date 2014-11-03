package com.workpoint.mwallet.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.workpoint.mwallet.server.dao.model.TillModel;

public class TillDao extends BaseDaoImpl {

	public TillDao(EntityManager em) {
		super(em);
	}

	public List<TillModel> getAllTills() {
		return getResultList(em.createQuery("FROM TillModel t order by tillNo DESC"));
	}
	
	public List<TillModel> getTillsByName(String name){
		String jpql = "FROM TillModel t where t.businessName like :bizName " +
				" order by tillNo Desc";
		
		Query query = em.createQuery(jpql)
				.setParameter("bizName","%"+name+"%");
		
		return getResultList(query);
	}
	
	public void saveTill(TillModel till){
		save(till);
	}
}
