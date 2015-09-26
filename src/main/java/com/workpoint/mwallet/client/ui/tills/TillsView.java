package com.workpoint.mwallet.client.ui.tills;

import static com.workpoint.mwallet.client.ui.tills.TillsPresenter.FILTER_SLOT;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.ActionLink;
import com.workpoint.mwallet.client.ui.component.MyHTMLPanel;
import com.workpoint.mwallet.client.ui.component.TableHeader;
import com.workpoint.mwallet.client.ui.component.TextField;
import com.workpoint.mwallet.client.ui.tills.table.TillsTable;
import com.workpoint.mwallet.client.ui.tills.table.TillsTableRow;
import com.workpoint.mwallet.client.ui.util.DateRange;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.UserDTO;

public class TillsView extends ViewImpl implements
		TillsPresenter.IActivitiesView {

	private final Widget widget;

	@UiField
	HTMLPanel divContentTop;

	@UiField
	MyHTMLPanel divContentTable;

	@UiField
	ActionLink aCreate;

	@UiField
	ActionLink aEdit;
	
	@UiField
	ActionLink aConfigure;

	@UiField
	ActionLink aDelete;

	@UiField
	HTMLPanel divMainContainer;

	@UiField
	TillsTable tblView;

	@UiField
	HTMLPanel divFilterBox;

	@UiField
	Anchor iFilterdropdown;

	@UiField
	TillsHeader headerContainer;

	@UiField
	ActionLink btnSearch;

	@UiField
	TextField txtSearchBox;

	public interface Binder extends UiBinder<Widget, TillsView> {
	}

	protected boolean isNotDisplayed;

	@Inject
	public TillsView(final Binder binder) {
		widget = binder.createAndBindUi(this);

		iFilterdropdown.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showFilterView();
			}
		});
		
		initControlButtons();

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

	public void initControlButtons() {
		show(aCreate, false);
		show(aEdit, false);
		show(aDelete, false);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public HasClickHandlers getAddButton() {
		return aCreate;
	}

	@Override
	public HasClickHandlers getEditButton() {
		return aEdit;
	}

	@Override
	public HasClickHandlers getDeleteButton() {
		return aDelete;
	}

	@Override
	public void presentData(TillDTO till) {
		tblView.createRow(new TillsTableRow(till));
	}
	
	@Override
	public void setHeaders(List<TableHeader> tableHeaders) {
		tblView.createHeader(tableHeaders);
	}
	
	@Override
	public void presentSummary(String totalTills) {
		headerContainer.setSummary(totalTills);
	}

	@Override
	public void clear() {
		tblView.clear();
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == FILTER_SLOT) {
			divFilterBox.clear();
			if (content != null) {
				divFilterBox.add(content);
			}
		} else {
			super.setInSlot(slot, content);
		}

	}

	@Override
	public void setSelection(boolean show) {
		if (show) {
			show(aCreate, false);
			show(aEdit, true);
			show(aDelete, true);
		} else {
			show(aCreate, true);
			show(aEdit, false);
			show(aDelete, false);
		}
	}

	private void show(Anchor aAnchor, boolean show) {
		if (show) {
			aAnchor.getElement().getParentElement().removeClassName("hide");
		} else {
			aAnchor.getElement().getParentElement().addClassName("hide");
		}
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
	
	public HasClickHandlers getFilterActionLink(){
		return iFilterdropdown;
	}
	
	public HasClickHandlers getConfigureButton(){
		return aConfigure;
	}
	
	@Override
	public void setAllowedButtons(UserDTO userGroup,boolean selection){
		CategoryDTO category = userGroup.getCategory();
		boolean isSuperUser = category.getCategoryName().equals("*") && userGroup.isAdmin();

		if (isSuperUser) {
			setSelection(selection);
		}
	}

	@Override
	public void setDates(DateRange passedDate) {
		//Set the String in the View
	}

}