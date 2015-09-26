package com.workpoint.mwallet.client.ui.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class StarRating extends Composite {

	private static StarRatingUiBinder uiBinder = GWT
			.create(StarRatingUiBinder.class);

	interface StarRatingUiBinder extends UiBinder<Widget, StarRating> {
	}

	@UiField FlowPanel container;
	String id="rating-input-";
	private int groupId = Random.nextInt(10000);
	private int max=-1;
	
	public StarRating() {
		initWidget(uiBinder.createAndBindUi(this));
		container.setStyleName("rating");
		id = id.concat(groupId+"-");
		setMaxValue(5);
		
	}
	
	public StarRating(int maxValue) {
		this();
		setMaxValue(maxValue);
	}
	
	public void setMaxValue(int maxValue){
		this.max= maxValue;
		createElements(maxValue);
	}
	
	int rating= 0;
	public void setValue(int rating){
		this.rating = rating;
		if(this.isAttached()){
			DOM.getElementById(id+rating).setAttribute("checked", "checked");
		}
	}

	public int getValue(){
		
		for(int i=max; i>0; i--){
			String strId = id+i;
			Element el = DOM.getElementById(strId);//elements.getItem(i);
			
			String val = el.getAttribute("checked");
			if(val!=null && !val.isEmpty()){
				String v= el.getAttribute("value");
				return new Integer(v);
			}
		}
		return 0;
	}
	
	private void createElements(int maxRatingValue){
		
		StringBuffer elementBuffer = new StringBuffer("");
		
		for(int i=maxRatingValue; i>0; i--){		
			elementBuffer.append("<input type=\"radio\" class=\"rating-input\" "+
                "id=\"rating-input-"+groupId+"-"+i+"\" name=\"rating-input-"+groupId+"\"" +
                		" value=\""+i+"\"/> "+
                "<label for=\"rating-input-"+groupId+"-"+i+"\" class=\"rating-star\" " +
                		"id=\"rating-label-"+groupId+"-"+i+"\"></label>");
		}
		
		getElement().setInnerHTML(elementBuffer.toString());
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		
		if(rating>0)
			DOM.getElementById(id+rating).setAttribute("checked", "checked");
	}

	private void load() {
//		for(int i=max; i>0; i--){
//			String labelId = "rating-label-"+groupId+"-"+i;
//			Element el = DOM.getElementById(labelId);//elements.getItem(i);		
//			if(el!=null){
//				DOM.sinkEvents((com.google.gwt.user.client.Element)el, Event.ONCLICK | Event.ONMOUSEOUT | Event.ONMOUSEOVER);
//				DOM.setEventListener((com.google.gwt.user.client.Element)el, new EventHandler());
//			}
//		}
	}
	
	@Override
	protected void onAttach() {
		// TODO Auto-generated method stub
		super.onAttach();

		load();
	}
	
	class EventHandler implements EventListener{
		
		private Element divElement;
		
		@Override
		public void onBrowserEvent(Event event) {
			divElement = Element.as(((NativeEvent) event).getEventTarget());
			divElement.setAttribute("background-color", "");
			if (event.equals(Event.ONCLICK)) {
				Window.alert("ONCLICK");
			}
//			if(event.equals(Event.ONMOUSEOVER)) {				
//				Window.alert("MOUSEOVER");
//		    }else 
//			if (event.equals(Event.ONCHANGE)) {
//				Window.alert("ONCHANGE");
//		    }
		}
	}

}
