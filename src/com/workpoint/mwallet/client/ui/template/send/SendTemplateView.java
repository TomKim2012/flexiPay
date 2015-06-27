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
	TextArea txtContactArea;
	@UiField
	TextArea txtMessageArea;
	@UiField
	DropDownList<TemplateDTO> lstTemplates;

	private SendTemplateDetails sendTemplateDetails;

	@Inject
	public SendTemplateView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		setTabPanel();

		lstTemplates
				.addValueChangeHandler(new ValueChangeHandler<TemplateDTO>() {

					@Override
					public void onValueChange(
							ValueChangeEvent<TemplateDTO> event) {
						txtMessageArea.setValue(event.getValue().getMessage());

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
		sendTemplateDetails.setTemplateInfo(templateSelected);
	}

	public TemplateDTO getTemplateDTO() {
		TemplateDTO templateDetail = sendTemplateDetails.getTemplateInfo();

		return templateDetail;
	}

	@Override
	public boolean isValid() {
		issues.clear();

		if (!sendTemplateDetails.isValid(issues)) {
			issues.getElement().getStyle().setDisplay(Display.BLOCK);
			return false;
		} else {
			return true;
		}
	}

	public TextArea getTxtComposeArea() {
		return txtContactArea;
	}

	public void setTxtComposeArea(TextArea txtComposeArea) {
		this.txtContactArea = txtComposeArea;
	}

	@Override
	public String getTemplateText() {
		if (!txtContactArea.getText().isEmpty()) {
			return txtContactArea.getText();
		} else {
			return null;
		}
	}

	@Override
	public void setTemplates(List<TemplateDTO> templates) {
		lstTemplates.setItems(templates);
	}

}
