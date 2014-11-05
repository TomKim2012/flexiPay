package com.workpoint.mwallet.client.ui.tills.save;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.UserDTO;

public class CreateTillPresenter extends
		PresenterWidget<CreateTillPresenter.MyView> {

	public interface MyView extends View {
		boolean isValid();

		void setTill(TillDTO tillSelected);
		
		void setUsers(List<UserDTO> allUsers);

		TillDTO getTillDTO();
	}

	@Inject DispatchAsync requestHelper;
	private TillDTO selected;
		
	@Inject
	public CreateTillPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}
	
	@Override
	protected void onBind() {
		super.onBind();
	}

	public void setTillDetails(TillDTO selected) {
		this.selected = selected;
		getView().setTill(selected);
	}

	public void setUsers(List<UserDTO> users) {
		getView().setUsers(users);
	}

}
