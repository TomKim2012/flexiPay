package com.workpoint.mwallet.client.ui.settings;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Category extends Composite {

	private static CategoryUiBinder uiBinder = GWT
			.create(CategoryUiBinder.class);

	interface CategoryUiBinder extends UiBinder<Widget, Category> {
	}

	public Category() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
