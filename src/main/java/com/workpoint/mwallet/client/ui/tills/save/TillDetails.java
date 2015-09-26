package com.workpoint.mwallet.client.ui.tills.save;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.ActionLink;
import com.workpoint.mwallet.client.ui.component.DropDownList;
import com.workpoint.mwallet.client.ui.component.IssuesPanel;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.TillDTO;

public class TillDetails extends Composite {

	private static TillDetailsUiBinder uiBinder = GWT
			.create(TillDetailsUiBinder.class);

	@UiField
	TextBox txtBusinessName;
	@UiField
	TextBox txtTillCode;
	@UiField
	TextBox txtPhone;
	@UiField
	CheckBox chckEnable;
	@UiField
	SpanElement spnMessage;

	@UiField
	ActionLink aPickTill;
	@UiField
	SpanElement spnSpinner;

	@UiField
	DropDownList<CategoryDTO> lstCategoryType;

	private TillDTO tillSelected;

	private String errorMessage;

	private CategoryDTO category;

	interface TillDetailsUiBinder extends UiBinder<Widget, TillDetails> {
	}

	public TillDetails() {
		initWidget(uiBinder.createAndBindUi(this));
		
	lstCategoryType.addValueChangeHandler(new ValueChangeHandler<CategoryDTO>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<CategoryDTO> event) {
				category = lstCategoryType.getValue();
			}
		});
	}

	public void setTillInfo(TillDTO tillSelected) {
		this.tillSelected = tillSelected;
		if (tillSelected != null) {
			txtBusinessName.setValue(tillSelected.getBusinessName());
			txtTillCode.setValue(tillSelected.getTillNo());
			aPickTill.setVisible(false);
			txtTillCode.getElement().setAttribute("readonly", "true");
			txtPhone.setValue(tillSelected.getPhoneNo());
			chckEnable.setValue(tillSelected.isActive() == 1 ? true : false);
			category = tillSelected.getCategory();
			lstCategoryType.setValue(category);
		}
	}

	public TillDTO getTillInfo() {
		if (tillSelected == null) {
			tillSelected = new TillDTO();
		}
		tillSelected.setBusinessName(txtBusinessName.getValue());
		tillSelected.setTillNo(txtTillCode.getValue());
		tillSelected.setPhoneNo(txtPhone.getValue());
		tillSelected.setActive(chckEnable.getValue() ? 1 : 0);
		
		tillSelected.setCategory(category);
		return tillSelected;
	}

	public boolean isValid(IssuesPanel issues) {
		if (txtBusinessName.getValue().isEmpty()) {
			issues.addError("Business Name cannot be Empty");
			return false;
		} else if (txtTillCode.getValue().isEmpty()) {
			issues.addError("Till Number cannot be Empty");
			return false;
		} else if (txtPhone.getValue().isEmpty()
				|| txtPhone.getValue().length() < 10) {
			issues.addError("Enter a correct Phone Number");
			return false;
		} else if (lstCategoryType.getValue()==null) {
			issues.addError("Till must belong to a Category. Select category");
			return false;
		}else {
			return true;
		}
	}

	public String geterrorMessages() {
		return errorMessage;
	}

	public String getTillCode() {
		if (isNullOrEmpty(txtTillCode.getText())) {
			setMessage("Please Enter a valid Till Code", "text-error");
			return null;
		} else {
			return txtTillCode.getText();
		}

	}

	boolean isNullOrEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}

	public void setMessage(String message, String styleName) {
		spnMessage.setInnerText(message);
		spnMessage.setClassName(styleName);
	}

	public HasClickHandlers getPickUser() {
		return aPickTill;
	}

	public void showSpinner(boolean show) {
		if (show) {
			spnSpinner.removeClassName("hide");
		} else {
			spnSpinner.addClassName("hide");
		}
	}

	public HasKeyDownHandlers getSearchBox() {
		return txtTillCode;
	}

	public void setCategories(List<CategoryDTO> categories) {
		lstCategoryType.setItems(categories);
	}

}
