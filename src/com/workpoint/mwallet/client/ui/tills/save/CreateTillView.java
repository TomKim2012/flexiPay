package com.workpoint.mwallet.client.ui.tills.save;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.IssuesPanel;
import com.workpoint.mwallet.client.ui.component.tabs.TabContent;
import com.workpoint.mwallet.client.ui.component.tabs.TabHeader;
import com.workpoint.mwallet.client.ui.component.tabs.TabPanel;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.UserDTO;

public class CreateTillView extends ViewImpl implements
		CreateTillPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, CreateTillView> {
	}

	@UiField
	IssuesPanel issues;

	@UiField
	TabPanel divTabs;

	private TillDetails tillDetails;
	private TillUserDetails userDetails;

	@Inject
	public CreateTillView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		setTabPanel();
	}

	public void setTabPanel() {
		issues.clear();
		tillDetails = new TillDetails();
		userDetails = new TillUserDetails();

		divTabs.setHeaders(Arrays.asList(new TabHeader("Till Details", true,
				"till_details"), new TabHeader("Users Details", false,
				"user_details")));

		divTabs.setContent(Arrays.asList(new TabContent(tillDetails,
				"till_details", true), new TabContent(userDetails,
				"user_details", false)));
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public boolean isValid() {
		issues.clear();
		if (!tillDetails.isValid(issues) || !userDetails.isValid(issues)) {
			issues.getElement().getStyle().setDisplay(Display.BLOCK);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void setTill(TillDTO tillSelected) {
		tillDetails.setTillInfo(tillSelected);
		userDetails.setTillInfo(tillSelected);
	}
	
	@Override
	public void setSelectedMerchant(UserDTO user){
		userDetails.setSelectedMerchant(user);
	}
	
	public TillDTO getTillDTO(){
		TillDTO tillDetail = tillDetails.getTillInfo();
		TillDTO tillCombined = userDetails.getTillUserInfo(tillDetail);
		
		return tillCombined;
	}
	
	
	public String getTillSearchCode(){
		return tillDetails.getTillCode();
	}
	
	public void setSearchMessage(String message, String styleName){
		tillDetails.setMessage(message, styleName);
	}

	@Override
	public void setUsers(List<UserDTO> allUsers) {
		userDetails.setUsers(allUsers);
	}

	@Override
	public HasClickHandlers getPickUser() {
		return tillDetails.getPickUser();
	}

	@Override
	public HasKeyDownHandlers getSearchBox() {
		return tillDetails.getSearchBox();
	}

}
