package com.workpoint.mwallet.client.ui.template.table;

import java.util.ArrayList;
import java.util.List;

import com.github.gwtbootstrap.client.ui.CheckBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.TableHeader;

public class ConfigureTablePopUp extends Composite {

	private static ConfigureTablePopUpUiBinder uiBinder = GWT
			.create(ConfigureTablePopUpUiBinder.class);

	@UiField
	HTMLPanel panelListing;

	private List<TableHeader> localHeaders = new ArrayList<TableHeader>();

	interface ConfigureTablePopUpUiBinder extends
			UiBinder<Widget, ConfigureTablePopUp> {
	}

	public ConfigureTablePopUp() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setHeaders(List<TableHeader> headers) {
		localHeaders = headers;

		int counter = 0;
		for (TableHeader header : headers) {
			CheckBox checkbox = new CheckBox(header.getTitleName());
			checkbox.setValue(header.getisDisplayed());
			checkbox.setName(Integer.toString(counter));
			counter++;

			checkbox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					String id = ((CheckBox) event.getSource()).getName();
					int i = Integer.parseInt(id);
					localHeaders.get(i).setisDisplayed(event.getValue());

					// System.err.println(i+">>"+localHeaders.get(i).getisDisplayed());
				}
			});

			panelListing.add(checkbox);
		}
	}

	public List<TableHeader> getConfiguredHeaders() {
		return localHeaders;
	}

}
