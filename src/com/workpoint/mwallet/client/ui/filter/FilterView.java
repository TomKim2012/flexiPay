package com.workpoint.mwallet.client.ui.filter;

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
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TillDTO;

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
	DropDownList<TillDTO> lstDates;
	
	@UiField
	Button btnSearch;
	@UiField
	Anchor aClose;

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
		filter.setTill(lstTills.getValue());
		filter.setStartDate(dtRange.getStartDate());
		filter.setEndDate(dtRange.getEndDate());
		return filter;
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

}
