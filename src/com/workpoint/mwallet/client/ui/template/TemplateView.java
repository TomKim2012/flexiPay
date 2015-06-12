package com.workpoint.mwallet.client.ui.template;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.DropDownList;
import com.workpoint.mwallet.shared.model.Listable;

public class TemplateView extends ViewImpl implements TemplatePresenter.MyView {

	private final Widget widget;

	@UiField
	DropDownList<Packages> dropDownPackages;

	@UiField
	TextArea txtComposeArea;

	@UiField
	Button saveButton;

	private List<LIElement> liElements = new ArrayList<LIElement>();
	private List<DivElement> divElements = new ArrayList<DivElement>();

	int counter = 0;

	public interface Binder extends UiBinder<Widget, TemplateView> {
	}

	@Inject
	public TemplateView(final Binder binder) {
		widget = binder.createAndBindUi(this);

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
				.addValueChangeHandler(new ValueChangeHandler<TemplateView.Packages>() {
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

	@Override
	public Widget asWidget() {
		return widget;
	}

	public TextArea getComposeTextArea() {
		return txtComposeArea;
	}

	public void setComposeTextArea(TextArea composeTextArea) {
		this.txtComposeArea = composeTextArea;
	}

	public Button getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(Button saveButton) {
		this.saveButton = saveButton;
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

}
