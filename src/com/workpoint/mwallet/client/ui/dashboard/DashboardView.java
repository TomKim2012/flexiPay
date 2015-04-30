package com.workpoint.mwallet.client.ui.dashboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
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
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.workpoint.mwallet.client.ui.charts.PieChart;
import com.workpoint.mwallet.shared.model.GradeCountDTO;

public class DashboardView extends ViewImpl implements
		DashboardPresenter.MyView {

	public interface Binder extends UiBinder<Widget, DashboardView> {
	}

	private final Widget widget;

	@UiField
	SpanElement spnTotalFunding;
	@UiField
	SpanElement spnActual;
	@UiField
	SpanElement spnRemaining;

	//
	@UiField
	PieChart pieTillAnalysis;

	@UiField
	HTMLPanel panelChart;

	@Inject
	public DashboardView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	// Chart data model property access definitions
	public interface DataModelProperties extends PropertyAccess<DataModel> {
		ModelKeyProvider<DataModel> id();

		ValueProvider<DataModel, String> name();

		ValueProvider<DataModel, Double> data();

		ValueProvider<DataModel, String> title();
		
		ValueProvider<DataModel, String> color();
	}

	// Chart data model
	public class DataModel {
		private int id;
		private String name;
		private double data;
		private String title;
		private String color;

		public DataModel(int id, String name, String title, double data, String color) {
			this.id = id;
			this.name = name;
			this.data = data;
			this.title = title;
			this.color = color;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getData() {
			return data;
		}

		public void setData(double data) {
			this.data = data;
		}

		public String getTitle() {
			return title;
		}
		
		public String getColor() {
			return color;
		}
	}

	public static final DataModelProperties dataModelProperties = GWT
			.create(DataModelProperties.class);

	private ListStore<DataModel> listStore;
	private Chart<DataModel> chart;

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	List<String> colors= new ArrayList<String>();

	private PieSeries<DataModel> series;
	
	@Override
	public void setGradeCount(List<GradeCountDTO> gradeCounts) {
		configurePieChart();
		int counter = 0;
		for (GradeCountDTO gradeCount : gradeCounts) {
			String minValue = NumberFormat.getCurrencyFormat("KES").format(
					gradeCount.getMinValue());
			String maxValue = "Max";
			if (gradeCount.getMaxValue() != null) {
				maxValue = NumberFormat.getCurrencyFormat("KES").format(
						gradeCount.getMaxValue());
			}
			String name = gradeCount.getGradeDesc()+"("+gradeCount.getGradeCount()+")";
			String title = Integer.toString(gradeCount.getGradeCount())
					+ " Merchants";
			
			listStore.add(new DataModel(counter++, name, title, gradeCount
					.getGradeCount(), gradeCount.getColor()));
			
			colors.add(gradeCount.getColor());
		}
		
		setColors(series);
		panelChart.add(chart);

	}

	public void configurePieChart() {

		// Setup the chart list store
		listStore = new ListStore<DataModel>(dataModelProperties.id());

		// Setup the chart
		chart = new Chart<DataModel>();
		chart.setStore(listStore);
		chart.setAnimated(true);

		// Set the chart legend
		Legend<DataModel> legend = new Legend<DataModel>();
		legend.setPosition(Position.RIGHT);
		legend.setItemHighlighting(true);
		chart.setLegend(legend);

		series = new PieSeries<DataModel>();
		series.setAngleField(dataModelProperties.data());
		series.setHighlighting(true);
		series.setDonut(50);
		

		// Sprite config
		TextSprite textConfig = new TextSprite();
		textConfig.setFont("Arial");
		textConfig.setTextBaseline(TextBaseline.MIDDLE);
		textConfig.setFontSize(12);
		textConfig.setTextAnchor(TextAnchor.MIDDLE);
		textConfig.setZIndex(15);

		// Setup the label config
		SeriesLabelConfig<DataModel> labelConfig = new SeriesLabelConfig<DataModel>();
		labelConfig.setSpriteConfig(textConfig);
		labelConfig.setLabelPosition(LabelPosition.START);
		labelConfig.setValueProvider(dataModelProperties.name(),
				new StringLabelProvider<String>() {
					@Override
					public String getLabel(String item) {
						return item.substring(0, 5);
					}
				});
		series.setLabelConfig(labelConfig);

		// Setup the legend label config
		series.setLegendValueProvider(dataModelProperties.name(),
				new StringLabelProvider<String>());
		
		

		// Set series tooltip
		SeriesToolTipConfig<DataModel> toolTip = new SeriesToolTipConfig<DataModel>();
		toolTip.setTrackMouse(true);
		toolTip.setHideDelay(200);
		toolTip.setLabelProvider(new SeriesLabelProvider<DataModel>() {

			@Override
			public String getLabel(
					DataModel item,
					ValueProvider<? super DataModel, ? extends Number> valueProvider) {
				return "(" + item.getTitle() + ")";
			}
		});
		series.setToolTipConfig(toolTip);

		chart.addSeries(series);

		chart.setHeight(400);
		chart.setWidth(400);
	}


	private void setColors(PieSeries<DataModel> series) {
		
		System.err.println("Set Color Called!");
		
		int i = 0;
		for (String color : colors) {
			Gradient slice = new Gradient("slice" + i, 45);
			slice.addStop(new Stop(0, new RGB(color)));
			slice.addStop(new Stop(100, new RGB(color)));
			chart.addGradient(slice);
			series.addColor(slice);
			++i;
			
			System.err.println("Color"+color);
		}

	}

}
