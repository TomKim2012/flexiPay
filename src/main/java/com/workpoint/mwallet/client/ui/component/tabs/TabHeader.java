package com.workpoint.mwallet.client.ui.component.tabs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.ActionLink;
import com.workpoint.mwallet.client.ui.component.BulletPanel;

public class TabHeader extends Composite {

	private static TabHeaderUiBinder uiBinder = GWT
			.create(TabHeaderUiBinder.class);

	@UiField
	BulletPanel liItem;

	@UiField
	ActionLink aLink;

	interface TabHeaderUiBinder extends UiBinder<Widget, TabHeader> {
	}
	/*
	 * Boolean isActive - sets the tab to be active when the TabPanel is Loaded
	 */
	public TabHeader(String text, Boolean isActive, String target) {
		initWidget(uiBinder.createAndBindUi(this));
		
		aLink.setText(text);
		setTarget(target);
		setisActive(isActive);
	}

	public void setisActive(Boolean active) {
		if (active) {
			liItem.addStyleName("active");
		} else {
			liItem.removeStyleName("active");
		}
	}
	
	public void setTarget(String target){
		if(!target.isEmpty()){
			aLink.setDataTarget("#"+target);
		}
	}

}
