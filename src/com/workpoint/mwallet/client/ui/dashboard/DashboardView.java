package com.workpoint.mwallet.client.ui.dashboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.dashboard.Performance.PerformanceType;

public class DashboardView extends ViewImpl implements
		DashboardPresenter.MyView {

	public interface Binder extends UiBinder<Widget, DashboardView> {
	}

	private final Widget widget;

	@UiField SpanElement spnTotalFunding;
	@UiField SpanElement spnActual;
	@UiField SpanElement spnRemaining;
//	@UiField SpanElement spnTimelinesTitle;
//	@UiField SpanElement spnTargetsTitle;
	
	/*@UiField
	TableView tableAnalysis;

	@UiField
	TableView tblBudgetAnalysis;

	@UiField
	PieChart pieChartBudget;
	// @UiField
	// PieChart pieChartConsumption;
	@UiField
	PieChart pieChartTimelines;
	@UiField
	PieChart pieChartTargets;*/

	@Inject
	public DashboardView(final Binder binder) {
		widget = binder.createAndBindUi(this);

		/*tblBudgetAnalysis.setHeaders(Arrays.asList("Program Names", "Plan(Ksh)",
				"Actual(Ksh)", "Available(Ksh)"));

		tableAnalysis.setHeaders(Arrays.asList("PROGRAM NAME", "BUDGET",
				"MEETING TARGETS", "MEETING TIMELINES", "THROUGH PUT"));*/
	}

	InlineLabel getInlineLabel(String amount, String color) {
		InlineLabel deficit = new InlineLabel(amount);
		if (!color.isEmpty())
			deficit.addStyleName(color);
		deficit.addStyleName("bold");
		return deficit;
	}

	InlineLabel getInlineLabel(String amount) {
		InlineLabel label = getInlineLabel(amount, "");
		return label;
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	/*@Override
	public void generate(List<ProgramAnalysis> list) {
		
		List<Data> pieChartData = new ArrayList<Data>();
		double totalFunding = 0.0;
		double totalActual = 0.0;
		double totalLeft = 0.0;
		
		for(ProgramAnalysis analysis: list){
			String href = "#home;page=activities;activity="+analysis.getId();
			
			ActionLink link = new ActionLink(analysis.getName());
			link.addStyleName("reports-program-link");
			link.setHref(href);
			link.setTitle(analysis.getDescription());
			
			tblBudgetAnalysis.addRow(
					Arrays.asList("", "background-purple text-right", "text-right", "text-right"),
					link, 
					new InlineLabel(formatShort(analysis.getBudgetAmount())),
					new InlineLabel(formatShort(analysis.getActual())),
					getInlineLabel(formatShort(analysis.getDiff()), analysis.isWithinBudget()? "text-success":"text-error"));
			
			totalFunding = totalFunding+analysis.getBudgetAmount();
			totalActual = totalActual+analysis.getActual();
			totalLeft = totalLeft+analysis.getDiff();
		}
		spnTotalFunding.setInnerText(format(totalFunding));
		spnActual.setInnerText(format(totalActual));
		spnRemaining.setInnerText(format(totalLeft));
		
		
		for(ProgramAnalysis analysis: list){
			if(analysis.getBudgetAmount()!=0){
				pieChartData.add(new Data(analysis.getName(), analysis.getBudgetAmount()/totalFunding,
						formatPerc(analysis.getBudgetAmount()*100/totalFunding)));
			}
		}
		pieChartBudget.setData(pieChartData);
	}*/

	private String formatPerc(Double d) {
		
		return d.intValue()+"%";
	}
/*
	private String format(Double budgetAmount) {
		
		return NumberUtils.CURRENCYFORMAT.format(budgetAmount);
	}
	
	private String formatShort(Double budgetAmount) {
		
		return NumberUtils.NUMBERFORMAT.format(budgetAmount);
	}

	@Override
	public void setAnalysis(List<PerformanceModel> budgetsPerfomance,
			List<PerformanceModel> targetsPerfomance,
			List<PerformanceModel> timelinesPerfomance,
			List<PerformanceModel> throughputPerfomance) {
		String budgetMeasureTip = "Measure of activities accomplished within budgets";
		String budgetNoData = "Percentage of Activities without actual expenditure information";
		String targetMeasure = "Measure of activities that met their Targets";
		String targetNoData = "Percentage of Activities without actual outcome information";
		String timelinesMeasure = "Measure of ability to meet planned timelines.";
		String throughPut = "Average amount of documentation available compared to other programs";
		
		double totalTimelinesSuccessCount = 0;
		double totalTimelinesFailCount = 0;
		double totalTimelinesNoDataCount = 0;
		
		double totalKPISuccessCount = 0;
		double totalKPIFailCount = 0;
		double totalKPINoDataCount = 0;
		
		int i=0;
		for(PerformanceModel model: budgetsPerfomance){
			PerformanceModel targetModel = targetsPerfomance.get(i);
			PerformanceModel timelineModel = timelinesPerfomance.get(i);
			if(model.isEmpty() && targetModel.isEmpty() && timelineModel.isEmpty()){
				//do not show empty rows
				++i;
				continue;
			}
			
			totalTimelinesSuccessCount+=timelineModel.getCountSuccess();
			totalTimelinesFailCount+=timelineModel.getCountFail();
			totalTimelinesNoDataCount+=timelineModel.getCountNoData();
			
			totalKPISuccessCount += targetModel.getCountSuccess();;
			totalKPIFailCount += targetModel.getCountFail();
			totalKPINoDataCount += targetModel.getCountNoData();
			
			ActionLink aName = new ActionLink(model.getName()+" ("+(model.getTotalCount())+")");
			aName.setHref("#home;page=activities;activity="+model.getProgramId());
			tableAnalysis.addRow(
					aName,
					new ColorWidget(model, budgetMeasureTip, budgetNoData),
					new ColorWidget(targetModel, targetMeasure, targetNoData),
					new ColorWidget(timelineModel, timelinesMeasure, ""),							
					new ColorWidget(Arrays.asList(new Performance(throughPut, 0,
							PerformanceType.NODATA))));
			
			++i;
		}
		
		
		double timelineTotal = totalTimelinesSuccessCount+totalTimelinesFailCount +totalTimelinesNoDataCount;
		if(timelineTotal!=0){
			int t = ((Double)timelineTotal).intValue();
			spnTimelinesTitle.setInnerText(""+t+" Activities And Tasks Analysed");
			List<Data> pieData = new ArrayList<Data>();
			if(totalTimelinesSuccessCount>0){
				pieData.add(new Data("Within deadlines",
						totalTimelinesSuccessCount, (int)(totalTimelinesSuccessCount*100/timelineTotal)+"%"));
			}
			
			if(totalTimelinesFailCount!=0){
				pieData.add(new Data("Failed Deadlines",totalTimelinesFailCount , 
						(int)(totalTimelinesFailCount*100/timelineTotal)+"%"));
			}
			pieChartTimelines.setData(pieData);
		}
		
		double kpiTotal = totalKPISuccessCount +totalKPIFailCount +totalKPINoDataCount;
		if(kpiTotal!=0){
			int t = ((Double)kpiTotal).intValue();
			spnTargetsTitle.setInnerText(t+" Key Performance Indicators Analysed");
			List<Data> pieData = new ArrayList<Data>();
			if(totalKPISuccessCount!=0){
				pieData.add(new Data("Within Targets", totalKPISuccessCount,
						(int)(totalKPISuccessCount*100/kpiTotal)+"%"));
			}
			
			if(totalKPIFailCount!=0){
				pieData.add(new Data("Failed Targets", totalKPIFailCount, 
						(int)(totalKPIFailCount*100/kpiTotal)+"%"));
			}
			
			if(totalKPINoDataCount!=0){
				pieData.add(new Data("No Data", totalKPINoDataCount, 
						(int)(totalKPINoDataCount*100/kpiTotal)+"%"));
			}
			
			pieChartTargets.setData(pieData);
		}
		
	}*/
}
