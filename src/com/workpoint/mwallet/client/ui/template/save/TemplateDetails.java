package com.workpoint.mwallet.client.ui.template.save;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.DropDownList;
import com.workpoint.mwallet.client.ui.component.IssuesPanel;
import com.workpoint.mwallet.shared.model.Listable;
import com.workpoint.mwallet.shared.model.TemplateDTO;

public class TemplateDetails extends Composite {

	private static TemplateDetailsUiBinder uiBinder = GWT
			.create(TemplateDetailsUiBinder.class);

	@UiField
	DropDownList<Packages> dropDownPackages;	
	@UiField
	TextArea txtComposeArea;
	@UiField
	TextBox txtType;
	@UiField
	TextBox txtName;
	@UiField
	CheckBox chckisDefault;
	@UiField
	TextBox txttillModel_Id;


	private TemplateDTO templateSelected;

	private String errorMessage;

	interface TemplateDetailsUiBinder extends UiBinder<Widget, TemplateDetails> {
	}

	public TemplateDetails() {
		initWidget(uiBinder.createAndBindUi(this));
		
		List<Packages> lstPackage = new ArrayList<>();
		lstPackage.add(new Packages("#firstName", "1"));
		lstPackage.add(new Packages("#lastName", "2"));
		lstPackage.add(new Packages("#surName", "3"));
		lstPackage.add(new Packages("#amount", "4"));
		lstPackage.add(new Packages("#time", "5"));
		lstPackage.add(new Packages("#date", "6"));
		lstPackage.add(new Packages("#Business", "7"));

		dropDownPackages.setItems(lstPackage);

		dropDownPackages
				.addValueChangeHandler(new ValueChangeHandler<Packages>() {
					@Override
					public void onValueChange(ValueChangeEvent<Packages> event) {
						Packages package1 = event.getValue();
						// Integer convertedPackage = Integer.parseInt(package1
						// .getName());
						txtComposeArea.setText(txtComposeArea.getText() + " "
								+ package1.getDisplayName());

					}
				});
		
	}

	public void setTemplateInfo(TemplateDTO templateSelected) {
		this.templateSelected = templateSelected;
		if (templateSelected != null) {
			txtComposeArea.setValue(templateSelected.getMessage());
			txtType.setValue(templateSelected.getType());
			txtName.setValue(templateSelected.getName());			
			chckisDefault.setValue(templateSelected.getIsDefault()== 1 ? true : false);
			txttillModel_Id.setValue(templateSelected.getTillModel_Id());
//			txttillModel_Id.getElement().setAttribute("readonly", "true");
//			chckEnable.setValue(templateSelected.isActive() == 1 ? true : false);
//			lstCategoryType.setValue(category);
		}
	}

	public TemplateDTO getTemplateInfo() {
		if (templateSelected == null) {
			templateSelected = new TemplateDTO();
		}
		templateSelected.setMessage(txtComposeArea.getValue());
		templateSelected.setType(txtType.getValue());
		templateSelected.setName(txtName.getValue());
		templateSelected.setTillModel_Id(txttillModel_Id.getValue());
		templateSelected.setIsDefault(chckisDefault.getValue() ? 1 : 0);
		
		return templateSelected;
	}

	public boolean isValid(IssuesPanel issues) {
		
		
		return true;
	}
	
	public class Packages implements Listable {
		private String name;
		private String value;

		public Packages(String name, String value) {
			this.name = name;
			this.value = value;
		}

		@Override
		public String getName() {
			return value;
		}

		@Override
		public String getDisplayName() {
			return name;
		}
	}

	public String geterrorMessages() {
		return errorMessage;
	}


	boolean isNullOrEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}


}
