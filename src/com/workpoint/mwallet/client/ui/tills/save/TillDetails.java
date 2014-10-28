package com.workpoint.mwallet.client.ui.tills.save;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TillDetails extends Composite {

	private static TillDetailsUiBinder uiBinder = GWT
			.create(TillDetailsUiBinder.class);

	interface TillDetailsUiBinder extends UiBinder<Widget, TillDetails> {
	}

	public TillDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
