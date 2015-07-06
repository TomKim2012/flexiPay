package com.workpoint.mwallet.client.ui.template.send;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.DropDownList;
import com.workpoint.mwallet.client.ui.component.IssuesPanel;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.TemplateDTO;

public class SendTemplateDetails extends Composite {

	private static TemplateDetailsUiBinder uiBinder = GWT
			.create(TemplateDetailsUiBinder.class);

	@UiField
	TextBox txtmessage;
	@UiField
	TextBox txtType;
	@UiField
	TextBox txtName;
	@UiField
	CheckBox txtisDefault;
	@UiField
	TextBox txttillModel_Id;
	@UiField
	CheckBox chckEnable;


	@UiField
	DropDownList<CategoryDTO> lstCategoryType;

	private TemplateDTO templateSelected;

	private String errorMessage;

	private CategoryDTO category;

	interface TemplateDetailsUiBinder extends UiBinder<Widget, SendTemplateDetails> {
	}

	public SendTemplateDetails() {
		initWidget(uiBinder.createAndBindUi(this));
		
	}

	public void setTemplateInfo(TemplateDTO templateSelected) {
		this.templateSelected = templateSelected;
		if (templateSelected != null) {
			txtmessage.setValue(templateSelected.getMessage());
			txtType.setValue(templateSelected.getType());
			txtName.setValue(templateSelected.getName());			
			txtisDefault.setValue(templateSelected.getIsDefault()== 1 ? true : false);
			//txttillModel_Id.setValue(templateSelected.getTillModel_Id());
//			txttillModel_Id.getElement().setAttribute("readonly", "true");
//			chckEnable.setValue(templateSelected.isActive() == 1 ? true : false);
//			lstCategoryType.setValue(category);
		}
	}

	public TemplateDTO getTemplateInfo() {
		if (templateSelected == null) {
			templateSelected = new TemplateDTO();
		}
		templateSelected.setMessage(txtmessage.getValue());
		templateSelected.setType(txtType.getValue());
		templateSelected.setName(txtName.getValue());		
		//templateSelected.setTillModel_Id(Integer.parseInt(txttillModel_Id.getValue()));
		
		return templateSelected;
	}

	public boolean isValid(IssuesPanel issues) {
		
		
		return true;
	}

	public String geterrorMessages() {
		return errorMessage;
	}


	boolean isNullOrEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}


}
