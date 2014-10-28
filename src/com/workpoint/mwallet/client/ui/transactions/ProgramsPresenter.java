package com.workpoint.mwallet.client.ui.transactions;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class ProgramsPresenter extends
		PresenterWidget<ProgramsPresenter.IActivitiesView> 
//		implements ActivitySelectionChangedHandler, ProgramsReloadHandler, ResizeHandler 
		
		{

	@ContentSlot
	public static final Type<RevealContentHandler<?>> FILTER_SLOT = new Type<RevealContentHandler<?>>();

//	@Inject
//	FilterPresenter filterPresenter;

	public interface IActivitiesView extends View {


//		void setData(List<IsProgramDetail> programs);
//
//		void createProgramTabs(List<IsProgramDetail> programs);
//
//		void setActivity(IsProgramDetail singleResult);
//
//		void setSelection(ProgramDetailType type);
//
//		void setPeriods(List<PeriodDTO> periods);
//
//		// void setSummaryView(boolean hasProgramId);
//
//		void setFunds(List<FundDTO> funds);

		void setLastUpdatedId(Long id);

		void setProgramId(Long programId);

//		void setSelection(ProgramDetailType programType, boolean isRowData);

		HasClickHandlers getAddButton();

		HasClickHandlers getaAssign();

		HasClickHandlers getDetailButton();

//		Dropdown<PeriodDTO> getPeriodDropDown();

//		void setActivePeriod(PeriodDTO period);

		void selectTab(Long l);

		void selectTab(String url);

		void setMiddleHeight();

		void removeTab(Long id);

		void setProgramId(Long programId, boolean isGoalsTable);

		void createDefaultTabs();

		void setDownloadUrl(Long programid, Long outcomeid, Long activityId,
				String programType);

		HasClickHandlers getaMove();
	}

	@Inject
	DispatchAsync requestHelper;

	@Inject
	PlaceManager placeManager;

	Long programId;
	String programCode;



	@Inject
	public ProgramsPresenter(final EventBus eventBus, final IActivitiesView view) {
		super(eventBus, view);
	}

	
	@Override
	protected void onBind() {
		super.onBind();
	}
	
}
