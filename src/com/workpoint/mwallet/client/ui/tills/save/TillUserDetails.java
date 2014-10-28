package com.workpoint.mwallet.client.ui.tills.save;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TillUserDetails extends Composite {

	private static TillUserDetailsUiBinder uiBinder = GWT
			.create(TillUserDetailsUiBinder.class);

	interface TillUserDetailsUiBinder extends UiBinder<Widget, TillUserDetails> {
	}

	public TillUserDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
