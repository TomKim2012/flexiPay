package com.workpoint.mwallet.client.ui.programs;

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

		HasClickHandlers getNewOutcome();

		HasClickHandlers getNewActivityLink();

		HasClickHandlers getNewObjectiveLink();

		HasClickHandlers getNewTaskLink();

		HasClickHandlers getEditLink();

		HasClickHandlers getDeleteButton();

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
	/*@Inject
	CreateOutcomePresenter createOutcome;
	@Inject
	CreateActivityPresenter createActivity;
	@Inject
	CreateObjectivePresenter objectivePresenter;
	@Inject
	CreateActivityPresenter createTask;
	@Inject
	AssignActivityPresenter assignActivity;

	@Inject
	ActivityDetailPresenter activityDetail;*/

	@Inject
	PlaceManager placeManager;

	Long programId;
	String programCode;

	Long programDetailId; // Drill Down
	String programDetailCode; // Drill Down

	/*ProgramDetailType programType = ProgramDetailType.PROGRAM; // last selected

	PeriodDTO period;

	IsProgramDetail selected;

	IsProgramDetail detail;*/

	@Inject
	public ProgramsPresenter(final EventBus eventBus, final IActivitiesView view) {
		super(eventBus, view);
	}

	
	@Override
	protected void onBind() {
		super.onBind();
		
		/*
		addRegisteredHandler(ActivitySelectionChangedEvent.TYPE, this);
		addRegisteredHandler(ProgramsReloadEvent.TYPE, this);
		addRegisteredHandler(AppResizeEvent.TYPE, this);

		getView().getPeriodDropDown().addValueChangeHandler(
				new ValueChangeHandler<PeriodDTO>() {

					@Override
					public void onValueChange(ValueChangeEvent<PeriodDTO> event) {
						PeriodDTO period = event.getValue();
						// period changed - reload all
						ProgramsPresenter.this.period = period;
						periodChanged();
					}
				});

		getView().getDetailButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				placeManager.revealPlace(new PlaceRequest(NameTokens.home)
						.with("page", "detailed").with("activityid",
								selected.getId() + ""));
			}
		});

		getView().getaAssign().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (selected != null) {
					assignActivity.load(selected.getId(), selected.getType());
				}
				AppManager.showPopUp("Assign Activity",
						assignActivity.getWidget(), new OptionControl() {
							@Override
							public void onSelect(String name) {

								if (name.equals("Cancel")) {
									hide();
									return;
								}
								fireEvent(new ProcessingEvent());
								assignActivity.addItems(); // Add all users
								TaskInfo taskInfo = assignActivity
										.getTaskInfo();

								if (selected != null) {
									taskInfo.setDescription(selected
											.getDescription());
									taskInfo.setActivityId(selected.getId());
									String taskName = "Program-"
											+ selected.getId();
									String approvalTaskName = taskName
											+ "-Approval";
									taskInfo.setTaskName(taskName);
									taskInfo.setApprovalTaskName(approvalTaskName);

									taskInfo.setDescription(selected
											.getDescription());
									assignTask(taskInfo);
								}
								hide();

							}
						}, "Done", "Cancel");

			}
		});
		
		
		getView().getaMove().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				requestHelper.execute(new GetProgramsTreeRequest(programId,null),
						new TaskServiceCallback<GetProgramsTreeResponse>() {
				
					@Override
					public void processResult(
							GetProgramsTreeResponse aResponse) {
						List<ProgramTreeModel> rootModels= aResponse.getModels();
						final TreeWidget tree = new TreeWidget(selected.getType(),rootModels);
						if(!rootModels.isEmpty())
						AppManager.showPopUp("Move '"+selected.getName()+"'",tree,new OptionControl(){
							@Override
							public void onSelect(String name) {
								if (name.equals("Done")) {
									moveProgramDetail(selected.getId(), tree.getSelectedTargetModel());
								}
								hide();
							}

						},"Done","Cancel");
					}
				});
			}
		});
		
		//Add Button
		getView().getAddButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				fireEvent(new CreateProgramEvent(null));
			}

		});

		getView().getDeleteButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final IsProgramDetail program = (selected != null) ? selected
						: detail;
				AppManager.showPopUp("Delete "
						+ program.getType().getDisplayName(),
						"Do you want to delete "
								+ program.getType().getDisplayName() + " '"
								+ program.getName() + "'",
						new OnOptionSelected() {

							@Override
							public void onSelect(String name) {
								if (name.equals("Yes")) {
									delete(program.getId());
								}
							}
						}, "Cancel", "Yes");
			}
		});

		getView().getNewOutcome().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showEditPopup(ProgramDetailType.OUTCOME);
			}
		});

		getView().getNewActivityLink().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				showEditPopup(ProgramDetailType.ACTIVITY);
			}
		});

		getView().getNewObjectiveLink().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				showEditPopup(ProgramDetailType.OBJECTIVE);
			}
		});

		getView().getNewTaskLink().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				showEditPopup(ProgramDetailType.TASK);
			}
		});

		getView().getEditLink().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				showEditPopup(
						selected != null ? selected.getType() : detail
								.getType(), true);
			}
		});
	 */
	}

	
	/*
	protected void showEditPopup(ProgramDetailType type) {
		showEditPopup(type, false);
	}

	protected void showEditPopup(ProgramDetailType type, boolean edit) {
		IsProgramDetail activity = (selected != null) ? selected : detail;

		showEditPopup(type, activity, edit);
	}

	
	}

	private void save(final IsProgramDetail activity) {
		// program.setParentId(programId);
		MultiRequestAction requests = new MultiRequestAction();
		requests.addRequest(new CreateProgramRequest(activity));

		requestHelper.execute(requests,
				new TaskServiceCallback<MultiRequestActionResult>() {
					@Override
					public void processResult(MultiRequestActionResult aResponse) {
						CreateProgramResponse createProgramsResponse = (CreateProgramResponse) aResponse
								.get(0);

						getView().setLastUpdatedId(
								createProgramsResponse.getProgram().getId());

						if (activity.getType() == ProgramDetailType.PROGRAM) {
							loadData(programId);
						} else if (activity.getType() == ProgramDetailType.OBJECTIVE) {
							loadData(null, null, null,
									ProgramDetailType.OBJECTIVE);
						} else if (activity.getType() == ProgramDetailType.ACTIVITY) {
							assert activity.getActivityOutcomeId() != null;
							afterSave(createProgramsResponse.getProgram()
									.getId(), activity.getActivityOutcomeId(),
									activity.getId() == null);
						} else {
							afterSave(createProgramsResponse.getProgram()
									.getId(), activity.getParentId(), activity
									.getId() == null);
						}

						fireEvent(new ProcessingCompletedEvent());
						fireEvent(new ActivitySavedEvent(activity.getType()
								.name().toLowerCase()
								+ " successfully saved"));

					}
				});

	}

	public void delete(Long programId) {
		requestHelper.execute(new DeleteProgramRequest(programId),
				new TaskServiceCallback<DeleteProgramResponse>() {
					@Override
					public void processResult(DeleteProgramResponse aResponse) {
						// refresh
						IsProgramDetail program = (selected != null) ? selected
								: detail;
						afterDelete(program.getId(), program.getParentId());

						if (program.getType() == ProgramDetailType.PROGRAM) {
							// remove Program Tab
							getView().removeTab(program.getId());
						}

						if (selected != null) {
							ProgramsPresenter.this.selected = null;
							getView().setSelection(
									detail == null ? null : detail.getType());
						} else if (detail != null) {
							ProgramsPresenter.this.detail = null;
							getView().setSelection(null);
						}

					}
				});
	}

	protected void afterDelete(Long id, final Long parentId) {

		// reload parent
		if (parentId != null) {
			MultiRequestAction requests = new MultiRequestAction();
			requests.addRequest(new GetProgramsRequest(parentId, false));
			requestHelper.execute(requests,
					new TaskServiceCallback<MultiRequestActionResult>() {
						@Override
						public void processResult(
								MultiRequestActionResult aResponse) {
							IsProgramDetail parent = ((GetProgramsResponse) (aResponse
									.get(0))).getSingleResult();
							fireEvent(new ProgramDetailSavedEvent(parent,
									false, false));
						}
					});
		}

		fireEvent(new ProgramDeletedEvent(id));

	}

	protected void afterSave(Long saveActivityId, final Long parentId,
			final boolean isNew) {
		MultiRequestAction requests = new MultiRequestAction();
		requests.addRequest(new GetProgramsRequest(saveActivityId, false));
		// reload parent
		if (parentId != null) {
			requests.addRequest(new GetProgramsRequest(parentId, false));
		}

		requestHelper.execute(requests,
				new TaskServiceCallback<MultiRequestActionResult>() {
					@Override
					public void processResult(MultiRequestActionResult aResponse) {

						IsProgramDetail activity = ((GetProgramsResponse) (aResponse
								.get(0))).getSingleResult();
						fireEvent(new ProgramDetailSavedEvent(activity, isNew,
								true));

						if (parentId != null) {
							IsProgramDetail parent = ((GetProgramsResponse) (aResponse
									.get(1))).getSingleResult();
							fireEvent(new ProgramDetailSavedEvent(parent,
									false, false));
						}
					}
				});
	}

	public void loadData(final Long activityId) {
		loadData(activityId, programDetailId);
	}

	public void loadData(final Long programId, Long detailId) {
		loadData(programId, detailId, null);
	}

	public void loadObjectives() {
		loadData(null, null, null, ProgramDetailType.OBJECTIVE);
	}

	public void loadData(Long programId, Long detailId, Long periodId) {
		loadData(programId, detailId, periodId, ProgramDetailType.PROGRAM);
	}

	public void loadActivitiesByOutcome(Long programId, Long outcomeId) {
		loadData(programId, outcomeId, null, true, ProgramDetailType.PROGRAM);
	}
	*/
	/**
	 * If PeriodId is null; current period is selected
	 * 
	 * @param programId
	 * @param detailId
	 * @param periodId
	 */
	
	/*
	public void loadData(Long programId, Long detailId, Long periodId,
			final ProgramDetailType typeToLoad) {
		loadData(programId, detailId, periodId, false, typeToLoad);
	}

	public void loadData(Long parentProgramId, Long detailId, Long periodId,
			boolean searchByOutcome, final ProgramDetailType typeToLoad) {
		fireEvent(new ProcessingEvent());

		this.programId = (parentProgramId == null || parentProgramId == 0L) ? null
				: parentProgramId;
		programDetailId = detailId == null ? null : detailId == 0 ? null
				: detailId;

		getView().setDownloadUrl(programId, searchByOutcome?programDetailId:null, searchByOutcome?null:programDetailId, typeToLoad.name());
		
		MultiRequestAction action = new MultiRequestAction();

		if (typeToLoad.equals(ProgramDetailType.OBJECTIVE)) {
			getView().setProgramId(this.programId, true);
			GetProgramsRequest request = new GetProgramsRequest(
					ProgramDetailType.OBJECTIVE, true);
			action.addRequest(request);
		} else {
			getView().setProgramId(this.programId);
		}

		// List of Programs for tabs
		{
			// Within a given period
			GetProgramsRequest request = new GetProgramsRequest(
					ProgramDetailType.PROGRAM, false);
			if (periodId != null) {
				request.setPeriodId(periodId);
			}
			action.addRequest(request);
		}

		// Get Periods
		action.addRequest(new GetPeriodsRequest());

		// Get Funding Sources
		action.addRequest(new GetFundsRequest());

		if (this.programId != null) {
			// Details of selected program
			// this.programId = programId;

			if (periodId != null) {
				// Period is not current period
				action.addRequest(new GetProgramsRequest(programCode, periodId,
						programDetailId == null));
			} else {
				action.addRequest(new GetProgramsRequest(this.programId,
						programDetailId == null));
			}

		}

		if (programDetailId != null) {
			if (searchByOutcome) {
				if (periodId != null) {
					assert programDetailCode != null;
					action.addRequest(new GetProgramsRequest(programDetailCode,
							periodId, this.programId != null));
				} else {
					action.addRequest(new GetProgramsRequest(this.programId,
							programDetailId, this.programId != null));
				}
			} else {
				if (periodId != null) {
					assert programDetailCode != null;
					action.addRequest(new GetProgramsRequest(programDetailCode,
							periodId, this.programId != null));
				} else {
					action.addRequest(new GetProgramsRequest(programDetailId,
							this.programId != null));
				}
			}

		}

		requestHelper.execute(action,
				new TaskServiceCallback<MultiRequestActionResult>() {
					@Override
					public void processResult(MultiRequestActionResult aResponse) {
						int i = 0;
						if (typeToLoad.equals(ProgramDetailType.OBJECTIVE)) {
							GetProgramsResponse response = (GetProgramsResponse) aResponse
									.get(i++);
							getView().setData(response.getPrograms());
						}

						// Programs (Presented as tabs below)
						GetProgramsResponse response = (GetProgramsResponse) aResponse
								.get(i++);
						// System.err.println("Tabs >> "+response.getPrograms().size());
						getView().createProgramTabs(response.getPrograms());

						// Periods
						GetPeriodsResponse getPeriod = (GetPeriodsResponse) aResponse
								.get(i++);
						getView().setPeriods(getPeriod.getPeriods());

						// Funds
						GetFundsResponse getFundsReq = (GetFundsResponse) aResponse
								.get(i++);
						getView().setFunds(getFundsReq.getFunds());

						// activities under a program
						// && programDetailId==null
						if (ProgramsPresenter.this.programId != null) {
							GetProgramsResponse response2 = (GetProgramsResponse) aResponse
									.get(i++);
							if (response2.getSingleResult() != null) {
								// The program detail might not be available for
								// the specified period
								programCode = response2.getSingleResult()
										.getCode();
								ProgramsPresenter.this.programId = response2
										.getSingleResult().getId();
								setActivity(response2.getSingleResult());
							}

						} else {

							if (programDetailId == null
									&& typeToLoad == ProgramDetailType.PROGRAM) {
								// This is a summary table with no program
								getView().setData(response.getPrograms());
							}

							getView()
									.selectTab(
											typeToLoad == ProgramDetailType.OBJECTIVE ? "#home;page=objectives"
													: "#home;page=activities;activity=" + 0);
							getView()
									.setSelection(
											typeToLoad == ProgramDetailType.OBJECTIVE ? ProgramDetailType.OBJECTIVE
													: null, false);
						}

						if (programDetailId != null) {
							GetProgramsResponse response2 = (GetProgramsResponse) aResponse
									.get(i++);

							if (response2.getSingleResult() != null) {
								// The program detail might not be available for
								// the specified period
								programDetailCode = response2.getSingleResult()
										.getCode();
								programDetailId = response2.getSingleResult()
										.getId();
								setActivity(response2.getSingleResult());
							}

						}

						getView().setActivePeriod(period);

						fireEvent(new ProcessingCompletedEvent());
					}
				});
	}

	protected void setActivity(IsProgramDetail activity) {
		getView().setActivity(activity);

		// if (activity.getType() != ProgramDetailType.PROGRAM
		// || programId == null) {
		// // this is a detail activity
		// this.detail = activity;
		// }
		this.detail = activity;
		programType = activity.getType();
	}

	protected void loadProgram(Long id) {
		this.programId = id;
		GetProgramsRequest request = new GetProgramsRequest(id, true);

		requestHelper.execute(request,
				new TaskServiceCallback<GetProgramsResponse>() {
					@Override
					public void processResult(GetProgramsResponse aResponse) {
						GetProgramsResponse response = (GetProgramsResponse) aResponse;
						setActivity(response.getSingleResult());
					}
				});

	}

	@Override
	public void onActivitySelectionChanged(ActivitySelectionChangedEvent event) {
		if (event.isSelected()) {
			this.selected = event.getProgramActivity();
			getView().setSelection(event.getProgramActivity().getType(), true);
		} else {
			this.selected = null;
			if (programId == null || programId == 0) {
				// summary view
				getView().setSelection(null);
			} else {
				getView().setSelection(programType, false);
			}

		}
	}

	@Override
	public void onProgramsReload(ProgramsReloadEvent event) {
		loadData(programId, programDetailId);
	}

	private void assignTask(final TaskInfo taskInfo) {
		requestHelper.execute(new AssignTaskRequest(taskInfo),
				new TaskServiceCallback<AssignTaskResponse>() {
					@Override
					public void processResult(AssignTaskResponse aResponse) {
						fireEvent(new ProcessingCompletedEvent());
						fireEvent(new LoadAlertsEvent());

						List<OrgEntity> assigned = taskInfo
								.getParticipants(ParticipantType.ASSIGNEE);
						String allocatedPeople = "";

						for (OrgEntity entity : assigned) {
							allocatedPeople = allocatedPeople
									+ entity.getDisplayName() + ",";
						}

						afterSave(selected.getId(), selected.getParentId(),
								false);
						fireEvent(new ActivitySavedEvent(
								"You successfully assigned '"
										+ taskInfo.getDescription() + "' "
										+ allocatedPeople));

					}
				});
	}
	*/
	
}
