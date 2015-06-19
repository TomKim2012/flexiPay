package com.workpoint.mwallet.client.ui.template;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.ActionLink;
import com.workpoint.mwallet.client.ui.component.DropDownList;
import com.workpoint.mwallet.client.ui.component.MyHTMLPanel;
import com.workpoint.mwallet.client.ui.component.TableHeader;
import com.workpoint.mwallet.client.ui.component.TableView;
import com.workpoint.mwallet.client.ui.template.table.TemplateTable;
import com.workpoint.mwallet.client.ui.template.table.TemplateTableRow;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.Listable;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.model.UserDTO;

public class TemplateView extends ViewImpl implements TemplatePresenter.MyView {

	private final Widget widget;

	@UiField
	HTMLPanel divContentTop;

	@UiField
	HTMLPanel divMiddleContent;

	@UiField
	HTMLPanel divMainContainer;

	@UiField
	TemplateTable tblView;

	@UiField
	ActionLink aCreate;

	@UiField
	ActionLink aEdit;

	@UiField
	ActionLink aDelete;
	
	@UiField
	ActionLink aSend;

	private List<LIElement> liElements = new ArrayList<LIElement>();
	private List<DivElement> divElements = new ArrayList<DivElement>();

	int counter = 0;

	public interface Binder extends UiBinder<Widget, TemplateView> {
	}

	@Inject
	public TemplateView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		
		initControlButtons();
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
	public HasClickHandlers getSendButton() {
		return aSend;
	}

	@Override
	public void presentData(TemplateDTO template) {
		tblView.createRow(new TemplateTableRow(template));
	}

	@Override
	public void setHeaders(List<TableHeader> tableHeaders) {
		tblView.createHeader(tableHeaders);
	}

	@Override
	public void setSelection(boolean show) {
		if (show) {
			show(aCreate, false);
			show(aEdit, true);
			show(aDelete, true);
			show(aSend, true);
		} else {
			show(aCreate, true);
			show(aEdit, false);
			show(aDelete, false);
			show(aSend, false);
		}
	}

	private void show(Anchor aAnchor, boolean show) {
		if (show) {
			aAnchor.getElement().getParentElement().removeClassName("hide");
		} else {
			aAnchor.getElement().getParentElement().addClassName("hide");
		}
	}

	@Override
	public void setAllowedButtons(UserDTO userGroup, boolean selection) {
		CategoryDTO category = userGroup.getCategory();
		boolean isSuperUser = category.getCategoryName().equals("*")
				&& userGroup.isAdmin();

		if (isSuperUser) {
			setSelection(selection);
		}
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		tblView.clear();

	}

	public void setMiddleHeight() {
		int totalHeight = divMainContainer.getElement().getOffsetHeight();
		int topHeight = divContentTop.getElement().getOffsetHeight();
		int middleHeight = totalHeight - topHeight - 10;

		if (middleHeight > 0) {
			divMiddleContent.setHeight(middleHeight + "px");
		}
	}

	@Override
	public void initControlButtons() {
		// TODO Auto-generated method stub
		
	}

}
