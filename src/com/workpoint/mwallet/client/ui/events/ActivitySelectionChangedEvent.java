package com.workpoint.mwallet.client.ui.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.CustomerDTO;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.model.TillDTO;

public class ActivitySelectionChangedEvent extends
		GwtEvent<ActivitySelectionChangedEvent.ActivitySelectionChangedHandler> {

	public static Type<ActivitySelectionChangedHandler> TYPE = new Type<ActivitySelectionChangedHandler>();
	private TillDTO till;
	private CategoryDTO category;
	private boolean isSelected;
	private TemplateDTO template;
	private CustomerDTO customer;

	public interface ActivitySelectionChangedHandler extends EventHandler {
		void onActivitySelectionChanged(ActivitySelectionChangedEvent event);
	}

	public ActivitySelectionChangedEvent(TillDTO tillDetail, Boolean isSelected) {
		this.till = tillDetail;
		this.isSelected = isSelected;
	}
	
	public ActivitySelectionChangedEvent(CustomerDTO customerDetail, Boolean isSelected) {
		this.customer = customerDetail;
		this.isSelected = isSelected;
	}

	public ActivitySelectionChangedEvent(CategoryDTO categoryDetail,
			Boolean isSelected) {
		this.category = categoryDetail;
		this.isSelected = isSelected;
	}

	public ActivitySelectionChangedEvent(TemplateDTO templateDetail,
			Boolean isSelected) {
		this.template = templateDetail;
		//this.setTemplate(templateDetail);
		this.isSelected = isSelected();

	}

	public TillDTO gettillDetail() {
		return till;
	}

	public TemplateDTO getTemplate() {
		return template;
	}
	
	public TemplateDTO getTemplateDetail() {
		return template;
	}
	
	public CustomerDTO getCustomer() {
		return customer;
	}
	
	public CustomerDTO getCustomerDetails() {
		return customer;
	}



	public void setTemplate(TemplateDTO template) {
		this.template = template;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public boolean isSelected() {
		return isSelected;
	}

	@Override
	protected void dispatch(ActivitySelectionChangedHandler handler) {
		handler.onActivitySelectionChanged(this);
	}

	@Override
	public Type<ActivitySelectionChangedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<ActivitySelectionChangedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, TillDTO tillDetail,
			Boolean isSelected) {
		source.fireEvent(new ActivitySelectionChangedEvent(tillDetail,
				isSelected));
	}

	public static void fire(HasHandlers source, TemplateDTO templateDetail,
			Boolean isSelected) {
		source.fireEvent(new ActivitySelectionChangedEvent(templateDetail,
				isSelected));
	}
	
	public static void fire(HasHandlers source, CustomerDTO customerDetail,
			Boolean isSelected) {
		source.fireEvent(new ActivitySelectionChangedEvent(customerDetail,
				isSelected));
	}
}
