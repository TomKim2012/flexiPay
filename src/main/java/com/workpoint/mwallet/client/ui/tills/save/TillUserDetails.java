package com.workpoint.mwallet.client.ui.tills.save;

import java.util.ArrayList;
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
import com.workpoint.mwallet.shared.model.UserGroup;

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
			if (tillSelected.getOwner() != null) {
				owners.add(tillSelected.getOwner());
				aOwners.setValues(owners);
				aOwners.select(Arrays.asList(tillSelected.getOwner()));
			}

			if (tillSelected.getSalesPerson() != null) {
				salesPerson.add(tillSelected.getSalesPerson());
				aOwners.setValues(owners);
				aSalesPersons.select(Arrays.asList(tillSelected
						.getSalesPerson()));
			}
		}
	}

	public TillDTO getTillUserInfo(TillDTO till) {
		tillSelected = till;

		if (tillSelected == null) {
			tillSelected = new TillDTO();
		}

		for (UserDTO owner : aOwners.getSelectedItems()) {
			tillSelected.setOwner(owner);
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
		}

		if (aOwners.getSelectedItems().size() > 1) {
			issues.addError("Till can only have one owner");
			return false;
		}
		if (aSalesPersons.getSelectedItems().size() < 1) {
			issues.addError("Please set the SalesPerson for this till");
			return false;
		}

		if (aSalesPersons.getSelectedItems().size() < 1) {
			issues.addError("Till can only have one salesPerson");
			return false;
		}
		return true;
	}

	public String geterrorMessages() {
		return errorMessage;
	}

	List<UserDTO> owners = new ArrayList<UserDTO>();
	List<UserDTO> cashiers = new ArrayList<UserDTO>();
	List<UserDTO> salesPerson = new ArrayList<UserDTO>();

	public void setUsers(List<UserDTO> allUsers) {
		breakUsers(allUsers);
		if (owners != null) {
			aOwners.setValues(owners);
		}
		if (cashiers != null) {
			aCashiers.setValues(cashiers);
		}
		if (salesPerson != null) {
			aSalesPersons.setValues(salesPerson);
		}
	}

	private void breakUsers(List<UserDTO> allUsers) {
		for (UserDTO user : allUsers) {
			sortByGroup(user.getGroups(), user);
		}
	}

	private void sortByGroup(List<UserGroup> groups, UserDTO user) {
		for (UserGroup group : groups) {
			GroupType type = GroupType.valueOf(group.getName());
			switch (type) {
			case Merchant:
				owners.add(user);
				break;
			case Cashier:
				cashiers.add(user);
				break;
			case SalesPerson:
				salesPerson.add(user);
				break;
			default:

			}
		}
	}

	public enum GroupType {
		Merchant("Merchant"), SalesPerson("SalesPerson"), Cashier("Cashier"), Administrator(
				"Administrator");

		private String groupCode;

		private GroupType(String groupCode) {
			this.groupCode = groupCode;
		}

		public String getGroupCode() {
			return groupCode;
		}

	}

}
