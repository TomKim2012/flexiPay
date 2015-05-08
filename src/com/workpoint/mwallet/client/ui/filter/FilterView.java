package com.workpoint.mwallet.client.ui.filter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.DateRangeWidget;
import com.workpoint.mwallet.client.ui.component.DropDownList;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter.SearchType;
import com.workpoint.mwallet.client.ui.tills.save.TillUserDetails.GroupType;
import com.workpoint.mwallet.client.ui.util.DateRange;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.model.UserGroup;

public class FilterView extends ViewImpl implements FilterPresenter.MyView {

	private final Widget widget;
	@UiField
	FocusPanel filterDialog;
	@UiField
	HTMLPanel divFilter;

	@UiField
	DateRangeWidget dtRange;

	@UiField
	DropDownList<TillDTO> lstTills;

	@UiField
	DropDownList<UserDTO> lstSalesPerson;
	@UiField
	DropDownList<UserDTO> lstMerchant;
	@UiField
	DropDownList<UserDTO> lstCashiers;

	@UiField
	DropDownList<DateRange> lstDates;

	@UiField
	HTMLPanel divTill;
	@UiField
	HTMLPanel divDateRange;
	@UiField
	HTMLPanel divSalesPerson;
	@UiField
	HTMLPanel divMerchant;
	@UiField
	HTMLPanel divCashier;
	@UiField
	HTMLPanel divDateRangeWidget;

	@UiField
	Button btnSearch;
	@UiField
	Anchor aClose;
	private SearchType searchType;

	public interface Binder extends UiBinder<Widget, FilterView> {
	}

	@Inject
	public FilterView(final Binder binder) {
		widget = binder.createAndBindUi(this);

	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public HasClickHandlers getSearchButton() {
		return btnSearch;
	}

	@Override
	public SearchFilter getSearchFilter() {
		SearchFilter filter = new SearchFilter();
		switch (searchType) {
		case Till:
			filter.setOwner(lstMerchant.getValue());
			filter.setCashier(lstCashiers.getValue());
			filter.setSalesPerson(lstSalesPerson.getValue());
			break;

		case Transaction:
			filter.setTill(lstTills.getValue());
			filter.setStartDate(dtRange.getStartDate());
			filter.setEndDate(dtRange.getEndDate());
			break;

		default:
			break;
		}
		return filter;
	}

	@Override
	public void setUsers(List<UserDTO> users) {
		breakUsers(users);
		lstCashiers.setItems(cashiers);
		lstMerchant.setItems(owners);
		lstSalesPerson.setItems(salesPerson);
	}

	private void clear() {
		cashiers.clear();
		owners.clear();
		salesPerson.clear();
	}

	@Override
	public HasClickHandlers getCloseButton() {
		return aClose;
	}

	@Override
	public HasBlurHandlers getFilterDialog() {
		return filterDialog;
	}

	@Override
	public void setTills(List<TillDTO> tills) {
		lstTills.setItems(tills);
	}

	public void show(HTMLPanel panel, Boolean status) {
		if (status) {
			panel.removeStyleName("hide");
		} else {
			panel.addStyleName("hide");
		}
	}

	@Override
	public void showFilter(SearchType searchType) {
		this.searchType = searchType;
		show(divTill, false);
		show(divDateRange, false);
		show(divDateRangeWidget, false);
		show(divSalesPerson, false);
		show(divMerchant, false);
		show(divCashier, false);

		switch (searchType) {
		case Till:
			show(divSalesPerson, true);
			show(divMerchant, true);
			show(divCashier, true);
			break;

		case Transaction:
			show(divTill, true);
			show(divDateRange, false);
			show(divDateRangeWidget, true);
			break;

		default:
			break;
		}
	}

	List<UserDTO> owners = new ArrayList<UserDTO>();
	List<UserDTO> cashiers = new ArrayList<UserDTO>();
	List<UserDTO> salesPerson = new ArrayList<UserDTO>();

	private void breakUsers(List<UserDTO> allUsers) {
		clear();
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

}
