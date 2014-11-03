package com.workpoint.mwallet.client.ui.tills.save;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.IssuesPanel;
import com.workpoint.mwallet.shared.model.TillDTO;

public class TillDetails extends Composite {

	private static TillDetailsUiBinder uiBinder = GWT
			.create(TillDetailsUiBinder.class);
	
	@UiField TextBox txtBusinessName;
	@UiField TextBox txtTillCode;
	@UiField TextBox txtPhone;
	@UiField CheckBox chckEnable;

	private TillDTO tillSelected;

	private String errorMessage;

	interface TillDetailsUiBinder extends UiBinder<Widget, TillDetails> {
	}

	public TillDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setTillInfo(TillDTO tillSelected) {
		this.tillSelected = tillSelected;
		if(tillSelected!=null){
			txtBusinessName.setValue(tillSelected.getBusinessName());
			txtTillCode.setValue(tillSelected.getTillNo());
			txtPhone.setValue(tillSelected.getPhoneNo());
			chckEnable.setValue(tillSelected.isActive()==1?true:false);
		}
	}
	
	
	public TillDTO getTillInfo(){
			if(tillSelected==null){
				tillSelected =new TillDTO();
			}
			tillSelected.setBusinessName(txtBusinessName.getValue());
			tillSelected.setTillNo(txtTillCode.getValue());
			tillSelected.setPhoneNo(txtPhone.getValue());
			tillSelected.setActive(chckEnable.getValue()?1:0);
			return tillSelected;
	}

	public boolean isValid(IssuesPanel issues) {
		if(txtBusinessName.getValue().isEmpty()){
			issues.addError("Business Name cannot be Empty");
			return false;
		}else if(txtTillCode.getValue().isEmpty()){
			issues.addError("Till Number cannot be Empty");
			return false;
		}else if(txtPhone.getValue().isEmpty() || txtPhone.getValue().length()<10){
			issues.addError("Enter a correct Phone Number");
			return false;
		}else{
			return true;
		}
	}

	public String geterrorMessages() {
		return errorMessage;
	}

}
