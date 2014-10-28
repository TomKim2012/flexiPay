package com.workpoint.mwallet.client.ui.transactions;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.ActionLink;
import com.workpoint.mwallet.client.ui.component.BulletListPanel;
import com.workpoint.mwallet.client.ui.component.BulletPanel;
import com.workpoint.mwallet.client.ui.component.MyHTMLPanel;
import com.workpoint.mwallet.client.ui.transactions.table.ProgramsTable;

public class ProgramsView extends ViewImpl implements
		ProgramsPresenter.IActivitiesView {

	private final Widget widget;

	@UiField
	ActionLink aProgram;

	@UiField
	HTMLPanel divMainContainer;

	@UiField
	HTMLPanel divMiddleContent;

	@UiField
	HTMLPanel divContentBottom;

	@UiField
	HTMLPanel divContentTop;

	@UiField
	HTMLPanel divNoContent;
	@UiField
	ProgramsTable tblView;

	@UiField
	HTMLPanel divFilterBox;

	@UiField
	Anchor iFilterdropdown;
	@UiField
	MyHTMLPanel divProgramsTable;
	@UiField
	ActionLink aBack;

	@UiField
	ProgramHeader headerContainer;

	@UiField
	BulletListPanel listPanel;

	@UiField
	ActionLink aLeft;
	@UiField
	ActionLink aRight;
	Long lastUpdatedId;


	public interface Binder extends UiBinder<Widget, ProgramsView> {
	}


	protected boolean isNotDisplayed;

	@Inject
	public ProgramsView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		listPanel.setId("mytab");
		
		
		aBack.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				History.back();
			}
		});
		
		iFilterdropdown.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (isNotDisplayed) {
					divFilterBox.removeStyleName("hide");
					isNotDisplayed = false;
				} else {
					divFilterBox.addStyleName("hide");
					isNotDisplayed = true;
				}
			}
		});
	}

	public void setMiddleHeight() {
		int totalHeight = divMainContainer.getElement().getOffsetHeight();
		int topHeight = divContentTop.getElement().getOffsetHeight();
		int middleHeight = totalHeight - topHeight - 43;

		if (middleHeight > 0) {
			divProgramsTable.setHeight(middleHeight + "px");
		}
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	private void showContent(boolean status) {
		if (status) {
			divMiddleContent.removeStyleName("hidden");
			divNoContent.addStyleName("hidden");
			headerContainer.setTitle("Programs Management");
		} else {
			divMiddleContent.addStyleName("hidden");
			divNoContent.removeStyleName("hidden");
		}
	}

	public void createDefaultTabs() {
		listPanel.clear();
		createTab("Goals", "#home;page=objectives", true);
		createTab("Summary", 0, true);
	}

	public void createTab(String text, long id, boolean active) {
		createTab(text, "#home;page=activities;activity=" + id, active);
	}

	public void createTab(String text, String url, boolean active) {
		BulletPanel li = new BulletPanel();
		ActionLink a = new ActionLink(text);

		a.setHref(url);
		li.add(a);

		if (active) {
			li.addStyleName("active");
		} else {
			li.removeStyleName("active");
		}
		listPanel.add(li);
	}


	@Override
	public void setLastUpdatedId(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProgramId(Long programId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HasClickHandlers getAddButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasClickHandlers getaAssign() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasClickHandlers getDetailButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void selectTab(Long l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectTab(String url) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTab(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProgramId(Long programId, boolean isGoalsTable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDownloadUrl(Long programid, Long outcomeid, Long activityId,
			String programType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HasClickHandlers getaMove() {
		// TODO Auto-generated method stub
		return null;
	}

	

}