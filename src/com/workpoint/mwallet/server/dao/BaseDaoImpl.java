package com.workpoint.mwallet.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.workpoint.mwallet.server.dao.model.PO;

public class BaseDaoImpl {

	protected EntityManager em;
	protected Logger log = Logger.getLogger(BaseDaoImpl.class); 
	
	public BaseDaoImpl(EntityManager em){
		this.em = em;
	}
		
	public void save(PO po){
		em.persist(po);
	}
	
	public void delete(PO po){
		em.remove(po);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getSingleResultOrNull(Query query){
		T value = null;
		try{
			value = (T)query.getSingleResult();
		}catch(Exception e){
			//e.printStackTrace();
		}
		
		return value;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(Query query){
		List<T> values = null;
		values = query.getResultList();
		return values;
	}
	
	public <T> T getById(Class<T> clazz, long id){
		
		return em.find(clazz, id);
	}
	
}
