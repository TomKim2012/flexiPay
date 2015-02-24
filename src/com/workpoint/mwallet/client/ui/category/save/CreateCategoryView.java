package com.workpoint.mwallet.client.ui.category.save;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.DropDownList;
import com.workpoint.mwallet.client.ui.component.IssuesPanel;
import com.workpoint.mwallet.client.ui.util.DateRange;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.Listable;

public class CreateCategoryView extends ViewImpl implements
		CreateCategoryPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, CreateCategoryView> {
	}

	@UiField
	IssuesPanel issues;

	@UiField
	DropDownList<CategoryTypes> lstCategoryType;

	@UiField
	TextBox txtCategoryName;

	private CategoryDTO categorySelected;

	public enum CategoryTypes implements Listable {
		MERCHANT_AGENCY("Merchant Agency", "Merchant_Agency"), BANKING_PARTNER(
				"Banking Partner", "Banking_Partner");

		private String displayName;
		private String value;

		private CategoryTypes(String displayName, String value) {
			this.displayName = displayName;
			this.value = value;
		}

		@Override
		public String getName() {
			return value;
		}

		@Override
		public String getDisplayName() {
			return displayName;
		}
		
		public static CategoryTypes getCategory(String value){
			for(CategoryTypes type: CategoryTypes.values()){
				if(type.value.equals(value)){
					return type;
				}
			}
			return null;
		}

	}

	@Inject
	public CreateCategoryView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		issues.clear();

	}

	public boolean isValid() {
		if (txtCategoryName.getValue().isEmpty()) {
			issues.addError("Category Name cannot be Empty");
			return false;
		} else if (lstCategoryType.getValue() == null) {
			issues.addError("Business Category cannot be Empty");
			return false;
		}
		return true;
	}

	public void setCategoryInfo(CategoryDTO categorySelected) {
		this.categorySelected = categorySelected;

		if (categorySelected != null) {
			txtCategoryName.setValue(categorySelected.getCategoryName());
			lstCategoryType.setValue(CategoryTypes.getCategory(categorySelected.getCategoryType()));
		}
	}

	public CategoryDTO getCategoryInfo() {
		if (categorySelected == null) {
			categorySelected = new CategoryDTO();
		}

		categorySelected.setCategoryName(txtCategoryName.getValue());
		categorySelected.setCategoryType(lstCategoryType.getValue().getName());

		return categorySelected;
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

}
