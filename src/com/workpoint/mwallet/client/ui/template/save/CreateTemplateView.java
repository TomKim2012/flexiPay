package com.workpoint.mwallet.client.ui.template.save;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.DropDownList;
import com.workpoint.mwallet.client.ui.component.IssuesPanel;
import com.workpoint.mwallet.client.ui.component.autocomplete.AutoCompleteField;
import com.workpoint.mwallet.client.ui.component.tabs.TabPanel;
import com.workpoint.mwallet.shared.model.CustomerDTO;
import com.workpoint.mwallet.shared.model.Listable;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.model.TillDTO;

public class CreateTemplateView extends ViewImpl implements
		CreateTemplatePresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, CreateTemplateView> {
	}

	private TemplateDetails templateDetails;

	@UiField
	IssuesPanel issues;

	@UiField
	DropDownList<Packages> dropDownPackages;

	@UiField
	AutoCompleteField<CustomerDTO> aCustomers;

	@UiField
	TextArea txtComposeArea;

	@UiField
	DropDownList<Packages> dropDownType;

	@UiField
	TextBox txtName;

	@UiField
	CheckBox chkDefault;

	@UiField
	AutoCompleteField<TillDTO> aTills;

	@UiField
	HTMLPanel tillPanel;

	@UiField
	HTMLPanel customerPanel;

	@UiField
	HTMLPanel defaultPanel;

	@UiField
	TabPanel divTabs;

	@Inject
	public CreateTemplateView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		setTabPanel();

		tillPanel.setVisible(false);
		defaultPanel.setVisible(false);

		List<Packages> lstPackage = new ArrayList<>();
		lstPackage.add(new Packages("#firstName", "1"));
		lstPackage.add(new Packages("#lastName", "2"));
		lstPackage.add(new Packages("#surName", "3"));
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

		List<Packages> typePackage = new ArrayList<>();
		typePackage.add(new Packages("Custom: To communicate with customer",
				"1"));
		typePackage.add(new Packages("Transaction: After customer transaction",
				"2"));
		dropDownType.setItems(typePackage);
		dropDownType.setValue(typePackage.get(0));
		dropDownType.addValueChangeHandler(new ValueChangeHandler<Packages>() {
			@Override
			public void onValueChange(ValueChangeEvent<Packages> event) {
				Packages packages = event.getValue();
				if (packages.getName() == "2") {
					List<Packages> lstPackage = new ArrayList<>();
					lstPackage.add(new Packages("#firstName", "1"));
					lstPackage.add(new Packages("#lastName", "2"));
					lstPackage.add(new Packages("#surName", "3"));
					lstPackage.add(new Packages("#amount", "4"));
					lstPackage.add(new Packages("#time", "5"));
					lstPackage.add(new Packages("#date", "6"));
					lstPackage.add(new Packages("#Business", "7"));
					dropDownPackages.setItems(lstPackage);

					defaultPanel.setVisible(true);
					tillPanel.setVisible(true);
					customerPanel.setVisible(false);

				} else if (packages.getName() == "1") {
					List<Packages> lstPackage = new ArrayList<>();
					lstPackage.add(new Packages("#firstName", "1"));
					lstPackage.add(new Packages("#lastName", "2"));
					lstPackage.add(new Packages("#surName", "3"));
					lstPackage.add(new Packages("#Business", "7"));
					dropDownPackages.setItems(lstPackage);

					tillPanel.setVisible(false);
					customerPanel.setVisible(true);
					defaultPanel.setVisible(false);
				}
			}
		});
	}

	@Override
	public void setCustomers(List<CustomerDTO> customers) {
		if (customers != null) {
			aCustomers.setValues(customers);
		}
	}

	@Override
	public void setTills(List<TillDTO> tills) {
		if (tills != null) {
			aTills.setValues(tills);
		}
	}

	public void setTabPanel() {
		issues.clear();
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setTemplate(TemplateDTO templateSelected) {
		templateDetails.setTemplateInfo(templateSelected);
	}

	public TemplateDTO getTemplateDTO() {
		TemplateDTO templateDetail = templateDetails.getTemplateInfo();
		return templateDetail;
	}

	@Override
	public boolean isValid() {
		issues.clear();
		if (!templateDetails.isValid(issues)) {
			issues.getElement().getStyle().setDisplay(Display.BLOCK);
			return false;
		} else {
			return true;
		}
	}

	public TextArea getTxtComposeArea() {
		return txtComposeArea;
	}

	public void setTxtComposeArea(TextArea txtComposeArea) {
		this.txtComposeArea = txtComposeArea;
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

	@Override
	public String getTemplateText() {
		if (!txtComposeArea.getText().isEmpty()) {
			return txtComposeArea.getText();
		} else {
			return null;
		}
	}

	@Override
	public String getTemplateType() {
		if (!dropDownType.getValue().getName().isEmpty()) {
			return dropDownType.getValue().getDisplayName();
		} else {
			return null;
		}
	}

	@Override
	public String getTemplateName() {
		if (!txtName.getText().isEmpty()) {
			return txtName.getText();
		} else {
			return null;
		}
	}

	@Override
	public int getTemplateDefault() {
		if (chkDefault.isChecked()) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public List<TillDTO> getTemplateTill() {
		if (!aTills.getSelectedItems().isEmpty()) {
			return aTills.getSelectedItems();
		} else {
			return null;
		}
	}

	@Override
	public List<CustomerDTO> getCustomers() {
		if (aCustomers.getSelectedItems().size() > 0) {
			return aCustomers.getSelectedItems();
		} else {
			return null;
		}
	}
}
