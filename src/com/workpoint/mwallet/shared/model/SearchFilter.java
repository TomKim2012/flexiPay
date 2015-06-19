package com.workpoint.mwallet.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SearchFilter implements Serializable {

	private static final long serialVersionUID = -8494519116994121416L;

	private String phrase;
	private TillDTO till;
	private TemplateDTO template;
	private String verificationCode;
	private List<TillDTO> tills;
	private List<TemplateDTO> templates;
	private UserDTO owner;
	private UserDTO salesPerson;
	private UserDTO cashier;
	private Date startDate;
	private String formatedStartDate;
	private String formatedendDate;
	private String viewBy;
	private Date endDate;
	private boolean isSu;

	public void setTemplate(TemplateDTO template) {
		this.template = template;
	}

	public SearchFilter() {
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPhrase() {
		return phrase;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public TillDTO getTill() {
		return till;
	}

	public void setTill(TillDTO till) {
		this.till = till;
	}
	
	public TemplateDTO getTemplate() {
		return template;
	}

	public boolean isEmpty() {
		if (phrase != null)
			return false;
		if (startDate != null)
			return false;
		if (endDate != null)
			return false;
		return true;

	}

	public UserDTO getOwner() {
		return owner;
	}

	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	public UserDTO getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(UserDTO salesPerson) {
		this.salesPerson = salesPerson;
	}

	public UserDTO getCashier() {
		return cashier;
	}

	public void setCashier(UserDTO cashier) {
		this.cashier = cashier;
	}

	public List<TillDTO> getTills() {
		return tills;
	}

	public void setTills(List<TillDTO> tills) {
		this.tills = tills;
	}

	public List<TemplateDTO> getTemplates() {
		return templates;
	}

	public void setTemplates(List<TemplateDTO> templates) {
		this.templates = templates;
	}

	public boolean isSu() {
		return isSu;
	}

	public void setSu(boolean isSu) {
		this.isSu = isSu;
	}

	public String getFormatedEndDate() {
		return formatedendDate;
	}

	public void setFormatedEndDate(String formatedendDate) {
		this.formatedendDate = formatedendDate;
	}

	public String getFormatedStartDate() {
		return formatedStartDate;
	}

	public void setFormatedStartDate(String formatedStartDate) {
		this.formatedStartDate = formatedStartDate;
	}
	
	public void setViewBy(String viewBy) {
		this.viewBy = viewBy;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getViewBy() {
		return viewBy;
	}


}
