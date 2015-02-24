package com.workpoint.mwallet.client.ui.category.save;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.workpoint.mwallet.client.ui.MainPagePresenter;
import com.workpoint.mwallet.client.ui.component.IssuesPanel;
import com.workpoint.mwallet.client.ui.users.save.UserSavePresenter;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.TillDTO;

public class CreateCategoryPresenter extends
		PresenterWidget<CreateCategoryPresenter.MyView> {

	public interface MyView extends View {

		void setCategoryInfo(CategoryDTO categorySelected);

		boolean isValid();

		CategoryDTO getCategoryInfo();

	}

	@Inject
	DispatchAsync requestHelper;

	@Inject
	MainPagePresenter mainPagePresenter;

	private CategoryDTO selected;

	@Inject
	public CreateCategoryPresenter(final EventBus eventBus, final MyView view,
			Provider<UserSavePresenter> addUserProvider) {
		super(eventBus, view);

	}

	@Override
	protected void onBind() {
		super.onBind();

	}
	
	public void setCategoryDetails(CategoryDTO selected) {
		this.selected = selected;
		getView().setCategoryInfo(selected);
	}

}
