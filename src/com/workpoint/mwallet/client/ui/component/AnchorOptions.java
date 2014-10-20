package com.workpoint.mwallet.client.ui.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AnchorOptions extends Composite {

	private static AnchorOptionsUiBinder uiBinder = GWT
			.create(AnchorOptionsUiBinder.class);
	
	@UiField BulletListPanel ulMenu;

	interface AnchorOptionsUiBinder extends UiBinder<Widget, AnchorOptions> {
	}

	public AnchorOptions() {
		initWidget(uiBinder.createAndBindUi(this));
		ulMenu.getElement().setAttribute("role", "menu"); 
	}

	public void createMenu(String text) {
		BulletPanel li =new BulletPanel();
		ActionLink a = new ActionLink();
		a.setText(text);
		li.add(a);
		ulMenu.add(li);
	}

}
