package com.workpoint.mwallet.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.workpoint.mwallet.server.dao.model.SmsModel;
import com.workpoint.mwallet.shared.model.SmsDTO;

public class SMSLogDao extends BaseDaoImpl {

	public SMSLogDao(EntityManager em) {
		super(em);
	}

	public List<SmsDTO> getSMSLog(
			String userId,boolean isSU,
			boolean isCategoryAdmin, Long categoryId) {
			
			StringBuffer jpql = new StringBuffer("SELECT "
				+ "s.id,s.cost,s.destination,s.message,s.status,s.tstamp,s.transactionId,s.messageId "
				+"FROM "
				+"mobileBanking.dbo.SmsModel s "
				+ "inner join LipaNaMpesaIPN ipn on (s.transactionid=ipn.mpesa_code) "
				+ "inner join TillModel t on (ipn.mpesa_acc=t.mpesa_acc and ipn.business_number=t.business_number) "
				+ "left join BUser u on (u.userId = t.salesPersonId) "
				+ "left join BUser u2 on (u2.userId = t.ownerId) "
				+ "where "
				+ "("
				+ "(t.categoryid=:categoryId "
				+ "and (u.userId=:userId or u2.userId=:userId or :isAdmin='Y')) "
				+ "or :isSU='Y') ");
			

			Query query = em.createNativeQuery(jpql.toString())
			.setParameter("categoryId", categoryId)
			.setParameter("userId", userId)
			.setParameter("isAdmin", isCategoryAdmin? "Y" : "N")
			.setParameter("isSU", isSU? "Y": "N");
			
			List<Object[]> rows = getResultList(query); 
			List<SmsDTO> smsList = new ArrayList<>();
			
			//byte boolTrue = 1;
			for(Object[] row: rows){
				int i=0;
				Object value=null;
				
				Long id = (value=row[i++])==null? null: new Long(value.toString());
				Double cost= (value=row[i++])==null? null: (Double)value;
				String destination=(value=row[i++])==null? null: value.toString();
				String message=(value=row[i++])==null? null:value.toString();
				String status=(value=row[i++])==null? null: value.toString();
				Date tstamp = (value=row[i++])==null? null: (Date)value;
				
				String transactionId = (value=row[i++])==null? null: value.toString();
				String messageId = (value=row[i++])==null? null: value.toString();
				
				
				SmsDTO smsDTO = new SmsDTO();
				smsDTO.setId(id);
				smsDTO.setCost(cost);
				smsDTO.setDestination(destination);
				smsDTO.setMessage(message);
				smsDTO.setTimeStamp(tstamp);
				smsDTO.settCode(transactionId);
				smsDTO.setStatus(status);
				smsList.add(smsDTO);
			}
			
			return smsList;
		}
}
