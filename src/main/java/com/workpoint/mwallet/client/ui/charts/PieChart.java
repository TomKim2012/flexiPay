package com.workpoint.mwallet.client.ui.charts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Chart.Position;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.series.PieSeries;
import com.sencha.gxt.chart.client.chart.series.Series.LabelPosition;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelConfig;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelProvider;
import com.sencha.gxt.chart.client.chart.series.SeriesToolTipConfig;
import com.sencha.gxt.chart.client.draw.Gradient;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.Stop;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite.TextAnchor;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite.TextBaseline;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.workpoint.mwallet.shared.model.dashboard.Data;

public class PieChart extends Composite{

	private static PieChartUiBinder uiBinder = GWT
			.create(PieChartUiBinder.class);

	interface PieChartUiBinder extends UiBinder<Widget, PieChart> {
	}

	public interface DataPropertyAccess extends PropertyAccess<Data> {

		ValueProvider<Data, Number> data1();

		ValueProvider<Data, String> name();
		
		@Path("name")
		ModelKeyProvider<Data> nameKey();
	}


	@UiField Chart<Data> chart;
	private static final DataPropertyAccess dataAccess = GWT.create(DataPropertyAccess.class);;
	ListStore<Data> store=null;
	
	public PieChart() {
		initWidget(uiBinder.createAndBindUi(this));
		initUI();
	}

	public void initUI(){
		store = new ListStore<Data>(dataAccess.nameKey());

		//chart.setDefaultInsets(50);
		chart.setStore(store);
		//chart.setShadowChart(true);
		chart.setAnimated(true);


		PieSeries<Data> series = new PieSeries<Data>();
		series.setAngleField(dataAccess.data1());
		setColors(series);

		TextSprite textConfig = new TextSprite();
		textConfig.setFont("Arial");
		textConfig.setTextBaseline(TextBaseline.MIDDLE);
		textConfig.setFontSize(12);
		textConfig.setTextAnchor(TextAnchor.MIDDLE);
		textConfig.setZIndex(15);
		
		SeriesLabelConfig<Data> labelConfig = new SeriesLabelConfig<Data>();
		labelConfig.setSpriteConfig(textConfig);
		labelConfig.setLabelPosition(LabelPosition.START);
		labelConfig.setValueProvider(dataAccess.name(),
				new StringLabelProvider<String>(){
			@Override
			public String getLabel(String item) {
				if(item.length()>12){
					item = item.substring(0,11)+"..";
				}
				return item;
			}
		});
		series.setLabelConfig(labelConfig);
		series.setHighlighting(true);
		series.setLegendValueProvider(dataAccess.name(),
				new LabelProvider<String>() {

					@Override
					public String getLabel(String item) {
						//return item.substring(0, 3);						
						return item;
					}
				});
		chart.addSeries(series);
		series.setDonut(54);
		
		//Set series tooltip
		SeriesToolTipConfig<Data> toolTip = new SeriesToolTipConfig<Data>();
	    toolTip.setTrackMouse(true);
	    toolTip.setHideDelay(200);
	    toolTip.setLabelProvider(new SeriesLabelProvider<Data>() {
	 
	      @Override
	      public String getLabel(Data item, ValueProvider<? super Data, ? extends Number> valueProvider) {
	        return "(" + item.getTitle()+ ")";
	      }
	    });
	    series.setToolTipConfig(toolTip);
	    
		final Legend<Data> legend = new Legend<Data>();
		legend.setPosition(Position.RIGHT);
		legend.setItemHighlighting(true);
		legend.setItemHiding(true);
		chart.setLegend(legend);
		
		//panelTurnAroundTime.add(chart);
//		final Resizable resize = new Resizable(chart, Dir.E, Dir.SE, Dir.S);
//		resize.setMinHeight(400);
//		resize.setMinWidth(400);

	}
	
	List<String> colors = Arrays.asList("#468847","#3a87ad","#f0ad4e"); 
	private void setColors(PieSeries<Data> series) {
		
		int i=0;
		for(String color: colors){
			Gradient slice = new Gradient("slice"+i, 45);
			
			slice.addStop(new Stop(0, new RGB(color)));
			slice.addStop(new Stop(100, new RGB(color)));
			chart.addGradient(slice);
			series.addColor(slice);
			++i;
		}		

	}

	public void setData(List<Data> data){
		store.clear();
		store.addAll(data);
		chart.redrawChart();
	}
	
	public void setData(List<Data> data, List<String> colors){
		setData(data);
		this.colors = colors;
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public void clear() {
		setData(new ArrayList<Data>());
	}

}
