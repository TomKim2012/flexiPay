package com.workpoint.mwallet.client.ui.component;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.shared.model.Listable;

public class Dropdown<T extends Listable> extends Composite implements HasValue<T>{

	private static DropdownUiBinder uiBinder = GWT
			.create(DropdownUiBinder.class);

	interface DropdownUiBinder extends UiBinder<Widget, Dropdown> {
	}
	
	@UiField BulletListPanel ulMenu;
	private T selected=null;
	private List<T> items=null;
	
	public Dropdown() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setValues(List<T> values){
		this.items = values;
		
		ulMenu.clear();
		if(values!=null)
		for(T value: values){
			DropdownItem item = new DropdownItem(value);
			ulMenu.add(item);
		}
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<T> handler) {
		
		return this.addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public T getValue() {
		return selected;
	}

	@Override
	public void setValue(T value) {
		setValue(value, false);
	}

	@Override
	public void setValue(T value, boolean fireEvents) {
		if(fireEvents){
			ValueChangeEvent.fire(Dropdown.this, value);
		}
		this.selected=value;
	}
	
	public List<T> getSelectionValues(){
		return items;
	}	

	
	/**
	 * <li>
	 * @author duggan
	 *
	 */
	class DropdownItem extends BulletPanel{
		T item;
		final ActionLink link;
		public DropdownItem(T item) {
			super();
			this.item = item;
			link = new ActionLink();
			link.setText(item.getDisplayName());
			add(link);
		}
		
		@Override
		protected void onLoad() {
			super.onLoad();
			link.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					setValue(item,true);
				}
			});
		}
	}
}
