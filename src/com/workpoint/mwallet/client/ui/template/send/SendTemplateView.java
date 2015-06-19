package com.workpoint.mwallet.client.ui.template.send;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.DropDownList;
import com.workpoint.mwallet.client.ui.component.IssuesPanel;
import com.workpoint.mwallet.client.ui.component.tabs.TabContent;
import com.workpoint.mwallet.client.ui.component.tabs.TabHeader;
import com.workpoint.mwallet.client.ui.component.tabs.TabPanel;
import com.workpoint.mwallet.shared.model.Listable;
import com.workpoint.mwallet.shared.model.TemplateDTO;

public class SendTemplateView extends ViewImpl implements
		SendTemplatePresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, SendTemplateView> {
	}

	@UiField
	IssuesPanel issues;
	@UiField
	DropDownList<Packages> dropDownPackages;
	
	private TemplateDetails templateDetails;


	@UiField
	TextArea txtComposeArea;
	
	@UiField
	TextBox txtType;
	
	@UiField
	TextBox txtName;
	
	@UiField
	CheckBox txtDefault;	
	
	@UiField
	TextBox txtTill;
	/*	
	@UiField
	TabPanel divTabs;
*/

	@Inject
	public SendTemplateView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		setTabPanel();

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

	public void setTabPanel() {
		issues.clear();
		//templateDetails = new TemplateDetails();
		
		//divTabs.setHeaders(Arrays.asList(new TabHeader("Till Details", true,
		//		"till_details")));
		
		//divTabs.setContent(Arrays.asList(new TabContent(templateDetails,
		//		"template details", true)));
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setTemplate(TemplateDTO templateSelected) {
		templateDetails.setTemplateInfo(templateSelected);
	}
	
	
	public TemplateDTO getTemplateDTO(){
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
		if (!txtType.getText().isEmpty()) {
			return txtType.getText();
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
		if (txtDefault.isChecked()) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	@Override
	public String getTemplateTill() {
			return txtTill.getValue();
	}


}
