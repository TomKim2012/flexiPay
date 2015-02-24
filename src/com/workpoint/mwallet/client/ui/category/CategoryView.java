package com.workpoint.mwallet.client.ui.category;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.category.table.CategoryTable;
import com.workpoint.mwallet.client.ui.category.table.CategoryTableRow;
import com.workpoint.mwallet.client.ui.component.ActionLink;
import com.workpoint.mwallet.client.ui.component.MyHTMLPanel;
import com.workpoint.mwallet.shared.model.CategoryDTO;

public class CategoryView extends ViewImpl implements
		CategoryPresenter.ICategoryView {

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
	ActionLink aDelete;

	@UiField
	HTMLPanel divMainContainer;

	@UiField
	CategoryTable tblView;


	public interface Binder extends UiBinder<Widget, CategoryView> {
	}

	protected boolean isNotDisplayed;

	@Inject
	public CategoryView(final Binder binder) {
		widget = binder.createAndBindUi(this);


		initControlButtons();

	}


	public void initControlButtons() {
		show(aCreate, true);
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
	public void presentData(CategoryDTO category) {
		tblView.createRow(new CategoryTableRow(category));
	}


	@Override
	public void clear() {
		tblView.clear();
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

}