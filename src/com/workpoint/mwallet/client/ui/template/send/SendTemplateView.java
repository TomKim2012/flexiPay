package com.workpoint.mwallet.client.ui.template.send;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.DropDownList;
import com.workpoint.mwallet.client.ui.component.IssuesPanel;
import com.workpoint.mwallet.shared.model.TemplateDTO;

public class SendTemplateView extends ViewImpl implements
		SendTemplatePresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, SendTemplateView> {
	}

	@UiField
	IssuesPanel issues;

	@UiField
	TextArea txtComposeArea;
	@UiField
	DropDownList<TemplateDTO> lstTemplates;

	private TemplateDetails templateDetails;

	@Inject
	public SendTemplateView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		setTabPanel();
		
		lstTemplates.addValueChangeHandler(new ValueChangeHandler<TemplateDTO>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<TemplateDTO> event) {
	
				Window.alert(event.getValue().toString());
				Window.alert(lstTemplates.getValue().toString());
				

				
				//txtComposeArea.setValue(event.getValue().toString());
				
			}
		});
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

	@Override
	public String getTemplateText() {
		if (!txtComposeArea.getText().isEmpty()) {
			return txtComposeArea.getText();
		} else {
			return null;
		}
	}

	@Override
	public void setTemplates(List<TemplateDTO> templates) {
		lstTemplates.setItems(templates);
	}

}
