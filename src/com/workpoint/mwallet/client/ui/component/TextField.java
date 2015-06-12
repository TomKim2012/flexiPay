package com.workpoint.mwallet.client.ui.component;

import com.google.gwt.user.client.ui.TextBox;

public class TextField extends TextBox{

	public void setPlaceholder(String placeHolderValue){
		getElement().setAttribute("placeHolder", placeHolderValue);
	}
	
	public void setClass(String className){
		setStyleName(className);
	}
	
	public void setType(String type){
		getElement().setAttribute("type", type);
	}
	
	public void setRequired(boolean isRequired){
		if(isRequired){
			getElement().setAttribute("required", "required");
		}
	}
	
	public void setDisabled(Boolean isDisabled){
		if(isDisabled){
			getElement().setAttribute("disabled", "disabled");
		}
	}
	
	public void setAutoComplete(boolean autocomplete){
		if(autocomplete){
			getElement().setAttribute("autocomplete", "on");
		}else{
			getElement().setAttribute("autocomplete", "off");
		}
	}
	
}
