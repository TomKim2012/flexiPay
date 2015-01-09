package com.workpoint.mwallet.client.ui.sms;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.ActionLink;
import com.workpoint.mwallet.client.ui.component.MyHTMLPanel;
import com.workpoint.mwallet.client.ui.component.TextField;
import com.workpoint.mwallet.client.ui.sms.table.SmsTable;
import com.workpoint.mwallet.client.ui.sms.table.SmsTableRow;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.SmsDTO;

public class SmsView extends ViewImpl implements
		SmsPresenter.IActivitiesView {

	private final Widget widget;

	@UiField
	HTMLPanel divContentTop;

	@UiField
	MyHTMLPanel divContentTable;

	@UiField
	HTMLPanel divMainContainer;

	@UiField
	SmsTable tblView;

	@UiField
	HTMLPanel divFilterBox;

	@UiField
	Anchor iFilterdropdown;

	@UiField
	SmsHeader headerContainer;

	@UiField
	ActionLink btnSearch;

	@UiField
	TextField txtSearchBox;

	public interface Binder extends UiBinder<Widget, SmsView> {
	}

	protected boolean isNotDisplayed;

	@Inject
	public SmsView(final Binder binder) {
		widget = binder.createAndBindUi(this);

		iFilterdropdown.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showFilterView();
			}
		});


	}

	public void showFilterView() {
		if (isNotDisplayed) {
			divFilterBox.removeStyleName("hide");
			isNotDisplayed = false;
		} else {
			divFilterBox.addStyleName("hide");
			isNotDisplayed = true;
		}
	}

	@Override
	public Widget asWidget() {
		return widget;
	}


	@Override
	public void presentData(SmsDTO log) {
		tblView.createRow(new SmsTableRow(log));
	}

	@Override
	public void presentSummary(String totalTills, String totalCost, String averageCost) {
		headerContainer.setSummary(totalTills,totalCost, averageCost);
	}

	@Override
	public void clear() {
		tblView.clear();
	}

	public void setMiddleHeight() {
		int totalHeight = divMainContainer.getElement().getOffsetHeight();
		int topHeight = divContentTop.getElement().getOffsetHeight();
		int middleHeight = totalHeight - topHeight - 10;

		if (middleHeight > 0) {
			divContentTable.setHeight(middleHeight + "px");
		}
	}

	public SearchFilter getFilter() {
		SearchFilter filter = new SearchFilter();
		if (!txtSearchBox.getText().isEmpty()) {
			filter.setPhrase(txtSearchBox.getText());
			return filter;
		} else {
			return null;
		}
	}

	@Override
	public HasClickHandlers getSearchButton() {
		return btnSearch;
	}
	
	@Override
	public HasKeyDownHandlers getSearchBox() {
		return txtSearchBox;
	}
}