package com.workpoint.mwallet.client.ui.tills.save;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.IssuesPanel;
import com.workpoint.mwallet.client.ui.component.autocomplete.AutoCompleteField;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.UserDTO;

public class TillUserDetails extends Composite {

	private static TillUserDetailsUiBinder uiBinder = GWT
			.create(TillUserDetailsUiBinder.class);

	@UiField
	AutoCompleteField<UserDTO> aOwners;

	// @UiField SpanElement spnOwner;
	// @UiField Anchor aChange;

	@UiField
	AutoCompleteField<UserDTO> aCashiers;
	@UiField
	AutoCompleteField<UserDTO> aSalesPersons;

	private String errorMessage;

	private TillDTO tillSelected;

	interface TillUserDetailsUiBinder extends UiBinder<Widget, TillUserDetails> {
	}

	public TillUserDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setTillInfo(TillDTO tillSelected) {
		this.tillSelected = tillSelected;
		if (tillSelected != null) {
			aOwners.select(Arrays.asList(tillSelected.getOwner()));
			aCashiers.select(tillSelected.getCashiers());
			aSalesPersons.select(Arrays.asList(tillSelected.getSalesPerson()));
		}
	}

	public TillDTO getTillUserInfo(TillDTO till) {
		tillSelected = till;
		if (tillSelected == null) {
			tillSelected = new TillDTO();
		}
		System.err.println("Owner>>>"+aOwners.getSelectedItems().size());
		for (UserDTO owner : aOwners.getSelectedItems()) {
			tillSelected.setOwner(owner);
			System.err.println("Owner>>>"+owner.getFirstName());
		}
		for (UserDTO salesPerson : aSalesPersons.getSelectedItems()) {
			tillSelected.setSalesPerson(salesPerson);
		}
		tillSelected.setCashiers(aCashiers.getSelectedItems());
		
		return tillSelected;
	}

	public boolean isValid(IssuesPanel issues) {
		if (aOwners.getSelectedItems().size() < 1) {
			issues.addError("Please set an Owner for this till");
			return false;
		} else if (aCashiers.getSelectedItems().size() < 1) {
			issues.addError("Please set at least 1 Cashier for this till");
			return false;
		} else if (aSalesPersons.getSelectedItems().size() < 1) {
			issues.addError("Please set the SalesPerson for this till");
			return false;
		}
		return true;
	}

	public String geterrorMessages() {
		return errorMessage;
	}

	public void setUsers(List<UserDTO> allUsers) {
		aOwners.setValues(allUsers);
		aCashiers.setValues(allUsers);
		aSalesPersons.setValues(allUsers);
	}

}
