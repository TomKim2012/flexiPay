package com.workpoint.mwallet.client.ui.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class BreadCrumbItem extends Composite {

	private static BreadCrumbItemUiBinder uiBinder = GWT
			.create(BreadCrumbItemUiBinder.class);
	
	@UiField ActionLink aLink;
	@UiField BulletPanel liItem;
	@UiField SpanElement spnDivider;
	
	interface BreadCrumbItemUiBinder extends UiBinder<Widget, BreadCrumbItem> {
	}

	public BreadCrumbItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	/*
	 * set the Text for the BreadCrumb
	 */
	public void setLinkText(String text) {
		aLink.setText(text);
	}
	
	public void setHome(Boolean status){
		if(status){
			aLink.addStyleName("icon-home helper-font-16");
		}
	}
	
	/*
	 * The last Item in the breadCrumb;
	 * if true - Link not clickable
	 * false - Link clickable
	 */
	public void setActive(Boolean status){
		if(status){
			liItem.addStyleName("active");
			spnDivider.addClassName("hidden");
		}else{
			liItem.removeStyleName("active");
			spnDivider.removeClassName("hidden");
		}
	}
	
	public void setHref(String href){
		aLink.setHref(href);
	}
	
	@Override
	public void setTitle(String title) {
		aLink.setTitle(title);
	}

}
