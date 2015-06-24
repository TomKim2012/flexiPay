package com.workpoint.mwallet.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.workpoint.mwallet.server.dao.model.ClientDocModel;
import com.workpoint.mwallet.server.dao.model.ClientModel;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.model.TillDTO;

public class sendTemplateDao extends BaseDaoImpl {

	public sendTemplateDao(EntityManager em) {
		super(em);
	}

	
	private String getTemplateString(List<TemplateDTO> allTemplates) {
		String templateList = "";
		int counter = 1;
		for (TemplateDTO template : allTemplates) {
			templateList = templateList + template.getMessage();
			if (allTemplates.size() != counter) {
				templateList += ",";
			}
			counter++;
		}
		return templateList;
	}

}
