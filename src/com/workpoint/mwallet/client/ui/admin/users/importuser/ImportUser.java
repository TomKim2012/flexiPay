package com.workpoint.mwallet.client.ui.admin.users.importuser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.ActionLink;
import com.workpoint.mwallet.client.ui.component.TableView;
import com.workpoint.mwallet.client.ui.component.TextField;
import com.workpoint.mwallet.shared.model.UserDTO;

public class ImportUser extends Composite {
	
	@UiField TextField txtSearchBox;
	@UiField ActionLink btnSearch;
	@UiField TableView tblView;
	
	private static ImportUserUiBinder uiBinder = GWT
			.create(ImportUserUiBinder.class);

	interface ImportUserUiBinder extends UiBinder<Widget, ImportUser> {
	}

	public ImportUser() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public HasClickHandlers getSearchLink(){
		return btnSearch;
	}
	
	public String getSearchTerm(){
		return txtSearchBox.getText();
	}
	
	public UserDTO getUser() {
		return null;
	}
}
