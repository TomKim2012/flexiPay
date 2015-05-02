package com.workpoint.mwallet.client.ui.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gwtbootstrap.client.ui.DropdownButton;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.base.InlineLabel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Chart.Position;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.LineSeries;
import com.sencha.gxt.chart.client.chart.series.PieSeries;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.chart.series.Series.LabelPosition;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelConfig;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelProvider;
import com.sencha.gxt.chart.client.chart.series.SeriesToolTipConfig;
import com.sencha.gxt.chart.client.draw.Gradient;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.Stop;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite.TextAnchor;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite.TextBaseline;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.workpoint.mwallet.client.ui.charts.PieChart;
import com.workpoint.mwallet.client.ui.dashboard.linegraph.data.ToolTip;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.client.ui.util.NumberUtils;
import com.workpoint.mwallet.shared.model.GradeCountDTO;
import com.workpoint.mwallet.shared.model.TrendDTO;

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

	@UiField
	HTMLPanel panelTrends;

	@UiField
	DropdownButton TrendsDropdown;

	@Inject
	public DashboardView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}
	
//	private void configureSelectedTrend(){
//		if (trend.equals(selectedTrend)) {
//			InlineLabel label = new InlineLabel();
//			label.setStyleName("icon icon-ok pull-left");
//			link.add(label);
//		}
//	}
	
	private void setTrendsDropDown(Trends selectedTrend) {
		TrendsDropdown.clear();
		for (Trends trend : Trends.values()) {
			NavLink link = new NavLink();
			link.setText(trend.getDisplayName());
//			if (trend.equals(selectedTrend)) {
//				InlineLabel label = new InlineLabel();
//				label.setStyleName("icon icon-ok pull-left");
//				link.add(label);
//			}
			TrendsDropdown.add(link);
		}
		TrendsDropdown.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String selected = TrendsDropdown.getLastSelectedNavLink()
						.getText().trim();

				Trends selectedTrend = Trends.getTrendFromName(selected);
				configureTrend(selectedTrend);
			}
		});

	}

	private static final DataPropertyAccess dataAccess = GWT
			.create(DataPropertyAccess.class);
	public static final DataModelProperties dataModelProperties = GWT
			.create(DataModelProperties.class);

	private ListStore<DataModel> listStore;
	private Chart<DataModel> pieChart;
	private Chart<Data> lineChart;

	@Override
	public Widget asWidget() {
		return widget;
	}

	List<String> colors = new ArrayList<String>();

	private PieSeries<DataModel> series;

	private ListStore<Data> store;

	private NumericAxis<Data> leftAxis;

	private NumericAxis<Data> rightAxis;

	List<LineSeries<Data>> allSeries = new ArrayList<>();
	List<LineSeries<Data>> displayedSeries = new ArrayList<>();

	@Override
	public void setGradeCount(List<GradeCountDTO> gradeCounts) {
		configurePieChart();
		int counter = 0;

		for (GradeCountDTO gradeCount : gradeCounts) {
			String name = gradeCount.getGradeDesc() + "("
					+ gradeCount.getGradeCount() + ")";
			String title = Integer.toString(gradeCount.getGradeCount())
					+ " Merchants";

			listStore.add(new DataModel(counter++, name, title, gradeCount
					.getGradeCount(), gradeCount.getColor()));

			colors.add(gradeCount.getColor());
		}

		setColors(series);
		panelChart.add(pieChart);
	}

	@Override
	public void setTrend(List<TrendDTO> trends) {
		List<Data> data = new ArrayList<Data>();
		store = new ListStore<Data>(dataAccess.nameKey());
		for (TrendDTO trend : trends) {
			String month = DateUtils.MONTHYEARFORMAT.format(trend
					.getStartDate());

			Double uniqueMerchants = trend.getUniqueMerchants() == null ? 0
					: (double) trend.getUniqueMerchants();
			Double uniqueCustomers = trend.getUniqueMerchants() == null ? 0
					: (double) trend.getUniqueCustomers();
			Double totalTrxs = trend.getTotalTrxs() == null ? 0
					: (double) trend.getTotalTrxs();

			Double totalAmount = trend.getTotalAmount() == null ? 0 : trend
					.getTotalAmount();
			Double merchantAvg = trend.getMerchantAverage() == null ? 0 : trend
					.getMerchantAverage();
			Double customerAvg = trend.getCustomerAverage() == null ? 0 : trend
					.getCustomerAverage();

			String toolTipTrxs = "Transactions:"
					+ NumberUtils.NUMBERFORMAT.format(totalTrxs);

			String toolTipSales = "Total Sales:"
					+ NumberUtils.CURRENCYFORMAT.format(totalAmount);

			String toolTipMerchants = "Merchants:"
					+ NumberUtils.NUMBERFORMAT.format(uniqueMerchants);

			String toolTipMerchantAvg = "Merchant Average:"
					+ NumberUtils.CURRENCYFORMAT.format(trend
							.getMerchantAverage());
			String toolTipCustomers = "Customers:"
					+ NumberUtils.NUMBERFORMAT.format(uniqueCustomers);

			String toolTipCustomerAvg = "Customer Average:"
					+ NumberUtils.CURRENCYFORMAT.format(trend
							.getCustomerAverage());

			ToolTip toolTips = new ToolTip(toolTipTrxs, toolTipSales,
					toolTipMerchants, toolTipMerchantAvg, toolTipCustomers,
					toolTipCustomerAvg);

			data.add(new Data(month, totalTrxs, totalAmount, uniqueMerchants,
					uniqueCustomers, merchantAvg, customerAvg, toolTips));
		}
		store.addAll(data);

		configureLineChart();

		// Configure To only Display Total Sales
		configureTrend(Trends.TOTALSALES);
		setTrendsDropDown(Trends.TOTALSALES);
	}

	Map<Integer, ValueProvider<Data, Double>> allProviders = new HashMap<Integer, ValueProvider<Data, Double>>();
	Map<Integer, ValueProvider<Data, Double>> displayedProviders = new HashMap<Integer, ValueProvider<Data, Double>>();

	public void configureLineChart() {

		allProviders.put(1, dataAccess.data1());
		allProviders.put(2, dataAccess.data2());
		allProviders.put(3, dataAccess.data3());
		allProviders.put(4, dataAccess.data4());
		allProviders.put(5, dataAccess.data5());
		allProviders.put(6, dataAccess.data6());

		TextSprite salesVolume = new TextSprite("Sales Volume");
		salesVolume.setFontSize(18);

		TextSprite merchantAverage = new TextSprite("Merchants/Customers");
		merchantAverage.setFontSize(18);

		TextSprite titleMonthYear = new TextSprite("Month of the Year");
		titleMonthYear.setFontSize(18);

		leftAxis = new NumericAxis<Data>();
		leftAxis.setPosition(Position.LEFT);
		leftAxis.setDisplayGrid(true);
		leftAxis.setTitleConfig(salesVolume);

		rightAxis = new NumericAxis<Data>();
		rightAxis.setPosition(Position.RIGHT);
		rightAxis.setTitleConfig(merchantAverage);

		// Axis that displays months at the bottom
		CategoryAxis<Data, String> catAxis = new CategoryAxis<Data, String>();
		catAxis.setPosition(Position.BOTTOM);
		catAxis.setField(dataAccess.name());
		catAxis.setTitleConfig(titleMonthYear);

		// Label for x-Axis
		TextSprite labelConfig = new TextSprite();
		catAxis.setLabelConfig(labelConfig);

		// Marker for different points
		Sprite marker = Primitives.diamond(0, 0, 6);
		/*
		 * ------------- Data1 - Transaction Numbers --------------------
		 */
		SeriesToolTipConfig<Data> toolTipTrxs = new SeriesToolTipConfig<Data>();
		toolTipTrxs.setTrackMouse(true);
		toolTipTrxs.setLabelProvider(new SeriesLabelProvider<Data>() {

			@Override
			public String getLabel(Data item,
					ValueProvider<? super Data, ? extends Number> valueProvider) {
				return item.getToolTips().getToolTipTrxs();
			}
		});
		// Marker
		marker = Primitives.diamond(0, 0, 6);
		marker.setFill(new RGB("#f89406"));

		LineSeries<Data> seriesTrxs = new LineSeries<Data>();
		seriesTrxs.setYField(dataAccess.data1());
		seriesTrxs.setStroke(new RGB("#f89406"));
		seriesTrxs.setSmooth(true);
		seriesTrxs.setFill(new RGB("#f89406"));
		seriesTrxs.setShowMarkers(true);
		seriesTrxs.setMarkerConfig(marker);
		seriesTrxs.setHighlighting(true);
		seriesTrxs.setToolTipConfig(toolTipTrxs);
		seriesTrxs.setLegendTitle("Transactions Totals");
		allSeries.add(seriesTrxs);

		/*
		 * -------------Data 2:Total Sales(Total Sales By Merchant)
		 */
		SeriesToolTipConfig<Data> toolTip2 = new SeriesToolTipConfig<Data>();
		toolTip2.setTrackMouse(true);
		toolTip2.setLabelProvider(new SeriesLabelProvider<Data>() {

			@Override
			public String getLabel(Data item,
					ValueProvider<? super Data, ? extends Number> valueProvider) {
				return item.getToolTips().getToolTipSales();
			}
		});

		LineSeries<Data> seriesSales = new LineSeries<Data>();
		seriesSales.setYField(dataAccess.data2());
		seriesSales.setStroke(new RGB(32, 68, 186));
		seriesSales.setShowMarkers(true);
		seriesSales.setSmooth(true);
		seriesSales.setFill(new RGB(32, 68, 186));
		seriesSales.setMarkerConfig(marker);
		seriesSales.setHighlighting(true);
		seriesSales.setToolTipConfig(toolTip2);
		seriesSales.setLegendTitle("Total Sales");
		allSeries.add(seriesSales);

		/*
		 * --------Data 3: Merchants(Unique Merchants)
		 */

		SeriesToolTipConfig<Data> toolTip3 = new SeriesToolTipConfig<Data>();
		toolTip3.setTrackMouse(true);
		toolTip3.setLabelProvider(new SeriesLabelProvider<Data>() {

			@Override
			public String getLabel(Data item,
					ValueProvider<? super Data, ? extends Number> valueProvider) {
				return item.getToolTips().getToolTipMerchants();
			}
		});
		// Marker
		marker = Primitives.diamond(0, 0, 6);
		marker.setFill(new RGB("#6DB4D3"));

		// Right Line Series - Merchant Average
		LineSeries<Data> seriesMerchants = new LineSeries<Data>();
		seriesMerchants.setYField(dataAccess.data3());
		seriesMerchants.setStroke(new RGB("#6DB4D3"));
		seriesMerchants.setSmooth(true);
		seriesMerchants.setFill(new RGB("#6DB4D3"));
		seriesMerchants.setShowMarkers(true);
		seriesMerchants.setMarkerConfig(marker);
		seriesMerchants.setHighlighting(true);
		seriesMerchants.setToolTipConfig(toolTip3);
		seriesMerchants.setLegendTitle("Merchants");
		allSeries.add(seriesMerchants);

		/*
		 * Data 4 --------------Customers ---------
		 */
		SeriesToolTipConfig<Data> toolTipCustomers = new SeriesToolTipConfig<Data>();
		toolTipCustomers.setTrackMouse(true);
		toolTipCustomers.setLabelProvider(new SeriesLabelProvider<Data>() {

			@Override
			public String getLabel(Data item,
					ValueProvider<? super Data, ? extends Number> valueProvider) {
				return item.getToolTips().getToolTipCustomers();
			}
		});
		// Marker
		marker = Primitives.diamond(0, 0, 6);
		marker.setFill(new RGB("#468847"));
		// Right Line Series - Merchant Average
		LineSeries<Data> seriesCustomers = new LineSeries<Data>();
		seriesCustomers.setYField(dataAccess.data4());
		seriesCustomers.setStroke(new RGB("#468847"));
		seriesCustomers.setSmooth(true);
		seriesCustomers.setFill(new RGB("#468847"));
		seriesCustomers.setShowMarkers(true);
		seriesCustomers.setMarkerConfig(marker);
		seriesCustomers.setHighlighting(true);
		seriesCustomers.setToolTipConfig(toolTipCustomers);
		seriesCustomers.setLegendTitle("Customers");
		allSeries.add(seriesCustomers);

		/*
		 * Data 5 ----------Merchant Average ---------
		 */
		SeriesToolTipConfig<Data> toolTipMerchantAvg = new SeriesToolTipConfig<Data>();
		toolTipMerchantAvg.setTrackMouse(true);
		toolTipMerchantAvg.setLabelProvider(new SeriesLabelProvider<Data>() {

			@Override
			public String getLabel(Data item,
					ValueProvider<? super Data, ? extends Number> valueProvider) {
				return item.getToolTips().getToolTipMerchantAvg();
			}
		});
		// Marker
		marker = Primitives.diamond(0, 0, 6);
		marker.setFill(new RGB("#999999"));
		// Right Line Series - Merchant Average
		LineSeries<Data> seriesMerchantAvg = new LineSeries<Data>();
		seriesMerchantAvg.setYField(dataAccess.data5());
		seriesMerchantAvg.setStroke(new RGB("#999999"));
		seriesMerchantAvg.setSmooth(true);
		seriesMerchantAvg.setFill(new RGB("#999999"));
		seriesMerchantAvg.setShowMarkers(true);
		seriesMerchantAvg.setMarkerConfig(marker);
		seriesMerchantAvg.setHighlighting(true);
		seriesMerchantAvg.setToolTipConfig(toolTipMerchantAvg);
		seriesMerchantAvg.setLegendTitle("Merchant Average");
		allSeries.add(seriesMerchantAvg);

		/*
		 * Data 6 : Customer Average
		 */
		SeriesToolTipConfig<Data> toolTipCustomerAvg = new SeriesToolTipConfig<Data>();
		toolTipCustomerAvg.setTrackMouse(true);
		toolTipCustomerAvg.setLabelProvider(new SeriesLabelProvider<Data>() {

			@Override
			public String getLabel(Data item,
					ValueProvider<? super Data, ? extends Number> valueProvider) {
				return item.getToolTips().getToolTipCustomerAvg();
			}
		});
		// Marker
		marker = Primitives.diamond(0, 0, 6);
		marker.setFill(new RGB("#b94a48"));

		// Right Line Series - Merchant Average
		LineSeries<Data> seriesCustomerAvg = new LineSeries<Data>();
		seriesCustomerAvg.setYField(dataAccess.data6());
		seriesCustomerAvg.setStroke(new RGB("#b94a48"));
		seriesCustomerAvg.setSmooth(true);
		seriesCustomerAvg.setFill(new RGB("#b94a48"));
		seriesCustomerAvg.setShowMarkers(true);
		seriesCustomerAvg.setMarkerConfig(marker);
		seriesCustomerAvg.setHighlighting(true);
		seriesCustomerAvg.setToolTipConfig(toolTipCustomerAvg);
		seriesCustomerAvg.setLegendTitle("Customer Average");
		allSeries.add(seriesCustomerAvg);

		// Legend Configuration
		Legend<Data> legend = new Legend<Data>();
		legend.setPosition(Position.RIGHT);
		legend.setItemHighlighting(true);
		legend.setItemHiding(true);
		legend.getBorderConfig().setStrokeWidth(0);

		lineChart = new Chart<Data>();
		lineChart.setStore(store);

		// Allow room for rotated labels
		lineChart.setDefaultInsets(20);
		lineChart.addAxis(leftAxis);
		lineChart.addAxis(rightAxis);
		lineChart.addAxis(catAxis);
		lineChart.setLegend(legend);
		lineChart.setAnimated(true);

		lineChart.setHeight(400);
		lineChart.setWidth(1050);
		lineChart.getElement().getStyle().setOverflow(Overflow.VISIBLE);

		panelTrends.add(lineChart);
	}

	public void removeField(NumericAxis<Data> passedAxis) {
		for (Integer key : displayedProviders.keySet()) {
			System.err.println("Removed Axis:"
					+ displayedProviders.get(key).getPath());
			passedAxis.removeField(displayedProviders.get(key));
		}
		displayedProviders.clear();
	}

	public void removeSeries() {
		if (displayedSeries.size() > 0) {
			for (LineSeries<Data> series : displayedSeries) {
				lineChart.removeSeries(series);
				System.err.println("Removed Series:"
						+ series.getLegendTitles().get(0));
			}
			displayedSeries.clear();
		}
	}

	public void configureTrend(Trends passedTrend) {
		int i = 1;
		for (Trends trend1 : Trends.values()) {
			if (trend1 == passedTrend) {
				ValueProvider<Data, Double> item = allProviders.get(i);

				//
				// if (i == 2 || i == 5 || i == 6) {
				// removeField(i, leftAxis);
				// leftAxis.addField(item);
				// } else {
				// removeField(i, rightAxis);
				// rightAxis.addField(item);
				// }

				removeField(leftAxis);
				leftAxis.addField(item);
				System.err.println("Axis Added:" + item.getPath());
				displayedProviders.put(i, item);

				removeSeries();
				lineChart.addSeries(allSeries.get(i - 1));
				allSeries.get(i - 1).setYAxisPosition(Position.LEFT);
				System.err.println("Series Added:"
						+ allSeries.get(i - 1).getLegendTitles().get(0));
				displayedSeries.add(allSeries.get(i - 1));
			}
			i++;
		}
		TrendsDropdown.setText(passedTrend.getDisplayName());
//		setTrendsDropDown(passedTrend);
		lineChart.redrawChart();
	}

	public void configurePieChart() {

		// Setup the chart list store
		listStore = new ListStore<DataModel>(dataModelProperties.id());

		// Setup the chart
		pieChart = new Chart<DataModel>();
		pieChart.setStore(listStore);
		pieChart.setAnimated(true);

		// Set the chart legend
		Legend<DataModel> legend = new Legend<DataModel>();
		legend.setPosition(Position.RIGHT);
		legend.setItemHighlighting(true);
		pieChart.setLegend(legend);

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

		pieChart.addSeries(series);

		pieChart.setHeight(400);
		pieChart.setWidth(400);
	}

	private void setColors(PieSeries<DataModel> series) {
		int i = 0;
		for (String color : colors) {
			Gradient slice = new Gradient("slice" + i, 45);
			slice.addStop(new Stop(0, new RGB(color)));
			slice.addStop(new Stop(100, new RGB(color)));
			pieChart.addGradient(slice);
			series.addColor(slice);
			++i;
		}

	}

}
