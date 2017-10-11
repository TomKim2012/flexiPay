package com.workpoint.mwallet.server.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PFSADB.dbo.clientdoc")
public class ClientDocModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Recid")
	private Long id;

	@Column(name = "clientcode")
	private String clientcode;

	@Column(name = "docnum")
	private String docnum;

	public Long getId() {
		return id;
	}

	public String getClientcode() {
		return clientcode;
	}

	public String getDocnum() {
		return docnum;
	}
}
